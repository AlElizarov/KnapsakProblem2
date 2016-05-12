package view.components.tablemanagers;

import javax.swing.table.TableCellEditor;

import view.components.tablemanagers.editors.StringCellEditor;
import view.components.tablemanagers.editors.UnitsCellEditor;
import viewmodel.TaskManager;

class NotSolutionEconomManager extends TableType {

	public NotSolutionEconomManager(TaskManager manager) {
		super(manager);
	}

	@Override
	protected boolean isEnythingElseEditable(int row, int col) {
		if ((col >= 0 && col < manager.getVariableCount())
				|| (row >= 0 && col <= 1)) {
			return true;
		}
		return false;
	}

	@Override
	protected void setColumnMargin() {
		columnMargin = 2;
	}

	@Override
	protected void setRowMargin() {
		rowMargin = 1;
	}

	@Override
	protected Object gerEnythingElse(int row, int col) {
		if (row == -1 && col >= 0 && col < manager.getVariableCount()) {
			return manager.getVarNames().get(col);
		}
		if (col == -2 && row > -1) {
			return getNames(row);
		}

		if (col == -1 && row > -1) {
			return getUnits(row);
		}
		return null;
	}

	private Object getUnits(int row) {
		if (row < manager.getCriterionCount()) {
			return manager.getCritUnits().get(row);
		}
		if (row >= manager.getCriterionCount()) {
			return manager.getLimitUnits().get(
					row - manager.getCriterionCount());
		}
		return null;
	}

	private Object getNames(int row) {
		if (row < manager.getCriterionCount()) {
			return manager.getCritNames().get(row);
		}
		if (row >= manager.getCriterionCount()) {
			return manager.getLimitNames().get(
					row - manager.getCriterionCount());
		}
		return null;
	}

	@Override
	protected void setEnythingElse(Object value, int row, int col) {
		if (row == -1) {
			manager.getVarNames().set(col, value.toString());
		}
		if (col == -2) {
			setNames(value, row);
		}

		if (col == -1) {
			setUnits(value, row);
		}
	}

	private void setUnits(Object value, int row) {
		if (row < manager.getCriterionCount()) {
			manager.getCritUnits().set(row, value.toString());
		}
		if (row >= manager.getCriterionCount()) {
			manager.getLimitUnits().set(row - manager.getCriterionCount(),
					value.toString());
		}
	}

	private void setNames(Object value, int row) {
		if (row < manager.getCriterionCount()) {
			manager.getCritNames().set(row, value.toString());
		}
		if (row >= manager.getCriterionCount()) {
			manager.getLimitNames().set(row - manager.getCriterionCount(),
					value.toString());
		}
	}

	@Override
	protected TableCellEditor getEnythingElseEditor(int row, int column) {
		if (column == -2 || row == -1) {
			return new StringCellEditor();
		} else {
			return new UnitsCellEditor();
		}
	}

	@Override
	protected int getFirstRowHeight() {
		return 40;
	}

	@Override
	protected boolean isEnythingElseFull() {
		return isCritNamesFull()
				&& isLimitationNamesFull()
				&& !manager.getVarNames().contains(null);
	}

	private boolean isLimitationNamesFull() {
		return !manager.getLimitNames().contains(null);
	}

	private boolean isCritNamesFull() {
		return !manager.getCritNames().contains(null);
	}

}
