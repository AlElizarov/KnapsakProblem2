package view.areas;

import javax.swing.JSplitPane;

import viewmodel.TaskManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public abstract class CenterViewMode extends JSplitPane implements BindableArea{
	
	protected TaskManager manager;

	public CenterViewMode(TaskManager manager) {
		super(JSplitPane.VERTICAL_SPLIT);
		this.manager = manager;
	}

}
