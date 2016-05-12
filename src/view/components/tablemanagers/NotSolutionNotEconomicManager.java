package view.components.tablemanagers;

import javax.swing.table.TableCellEditor;

import viewmodel.TaskManager;

class NotSolutionNotEconomicManager extends TableType {

	public NotSolutionNotEconomicManager(TaskManager manager) {
		super(manager);
	}

	@Override
	protected boolean isEnythingElseEditable(int row, int col) {
		return false;
	}

	@Override
	protected void setColumnMargin() {
		columnMargin = 0;
	}

	@Override
	protected void setRowMargin() {
		rowMargin = 0;
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
	protected boolean isEnythingElseFull() {
		return true;
	}

}
