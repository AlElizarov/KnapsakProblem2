package view.components.tablemanagers;

import javax.swing.table.TableCellEditor;

import viewmodel.TaskManager;

class NotSolutionNotEconomicManager extends TableType {

	public NotSolutionNotEconomicManager(TaskManager manager) {
		super(manager);
		columnMargin = 0;
		rowMargin = 0;
	}

	@Override
	protected boolean isEnythingElseEditable(int row, int col) {
		return false;
	}

	@Override
	protected Object gerEnythingElse(int row, int col) {
		return null;
	}

	@Override
	protected void setEnythingElse(Object value, int row, int col) {
	}

	@Override
	protected TableCellEditor getEnythingElseEditor(int row, int column) {
		return null;
	}

	@Override
	protected int getFirstRowHeight() {
		return 20;
	}

	@Override
	protected int getLastRowHeight() {
		return 20;
	}

}
