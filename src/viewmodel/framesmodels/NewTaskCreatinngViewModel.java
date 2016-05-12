package viewmodel.framesmodels;

import handle.AbstractHandler;

import java.awt.Color;

import viewmodel.TaskManager;
import viewmodel.framesmodels.utils.HandlerDigitalParameters;
import viewmodel.framesmodels.utils.HandlerStringParameters;

public class NewTaskCreatinngViewModel {

	private boolean hasEconomMeaning = false;
	private boolean isMaxButtonSelected = true;
	private boolean isMinButtonSelected = false;

	private AbstractHandler varCountHandler = new HandlerDigitalParameters();
	private AbstractHandler limitCountHandler = new HandlerDigitalParameters();
	private AbstractHandler criterionCountHandler = new HandlerDigitalParameters();
	private AbstractHandler taskNameHandler = new HandlerStringParameters();
	private AbstractHandler economTextHandler = new HandlerStringParameters();

	private TaskManager manager;

	public NewTaskCreatinngViewModel(TaskManager sharedViewModel) {
		this.manager = sharedViewModel;
	}

	public boolean isEconomFieldEnabled() {
		return hasEconomMeaning;
	}

	public void setEconomCheckBoxSelected(boolean isSelected) {
		hasEconomMeaning = isSelected;
		if (!isSelected) {
			manager.setEconomText("");
			economTextHandler.setText(null);
		}
	}

	public boolean isEconomCheckBoxSelected() {
		return hasEconomMeaning;
	}

	public boolean isMaxSelected() {
		return isMaxButtonSelected;
	}

	public boolean isMinSelected() {
		return isMinButtonSelected;
	}

	public void setMinSelected() {
		isMinButtonSelected = true;
		isMaxButtonSelected = false;
	}

	public void setMaxSelected() {
		isMinButtonSelected = false;
		isMaxButtonSelected = true;
	}

	private boolean isAllTextFieldsGood() {
		return taskNameHandler.isGood()
				&& varCountHandler.isGood()
				&& limitCountHandler.isGood()
				&& criterionCountHandler.isGood()
				&& (hasEconomMeaning && economTextHandler.isGood() || !hasEconomMeaning);
	}

	public Color getTaskNameFieldColor() {
		return taskNameHandler.getFieldColor();
	}

	public String getTaskNameErrorMsg() {
		return taskNameHandler.getErrorMsg();
	}

	public boolean isOkButtonEnabled() {
		return isAllTextFieldsGood();
	}

	public AbstractHandler getVarCountHandler() {
		return varCountHandler;
	}

	public AbstractHandler getLimitCountHandler() {
		return limitCountHandler;
	}

	public AbstractHandler getCriterionCountHandler() {
		return criterionCountHandler;
	}

	public AbstractHandler getTaskNameHandler() {
		return taskNameHandler;
	}

	public AbstractHandler getEconomTextHandler() {
		return economTextHandler;
	}

	public void setTaskName(String taskName) {
		manager.setTaskName(taskName);
	}

	public void setVarCount(String varCount) {
		manager.setVarCount(varCount);
	}

	public void setLimitationCount(String limitationCount) {
		manager.setLimitationCount(limitationCount);
	}

	public void setCriterionCount(String criterionCount) {
		manager.setCriterionCount(criterionCount);
	}

	public void setType(boolean type) {
		manager.setMax(type);
	}

	public void createTask() {
		manager.createTask();
	}

	public void setEconomText(String text) {
		manager.setEconomText(text);
	}

	public String getEconomText() {
		return manager.getEconomText();
	}
}
