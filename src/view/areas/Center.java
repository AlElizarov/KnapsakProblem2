package view.areas;

import viewmodel.TaskManager;

public class Center implements BindableArea{
	
	private CenterViewMode mode;
	private TaskManager manager;

	public Center(TaskManager manager) {
		this.manager = manager;
		setSimpleMode();
	}

	public void setSimpleMode() {
		mode = new SimpleMode(manager);
	}

	public void setStudyMode() {
		mode = new StudyMode(manager);
	}

	public CenterViewMode getCenterView() {
		return mode;
	}

	@Override
	public void bind() {
		mode.bind();
	}

}
