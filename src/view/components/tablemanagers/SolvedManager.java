package view.components.tablemanagers;

import javax.swing.table.TableCellEditor;

import viewmodel.TaskManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableType;

class SolvedManager extends TableType {

	private ITableType tableType;

	public SolvedManager(TaskManager taskManager, ITableType tableType) {
		super(taskManager);
		this.tableType = tableType;
		columnMargin = tableType.getColumnMargin();
		rowMargin = tableType.getRowMargin();
		rowMarginBottom = 1;
	}

	@Override
	protected Object gerEnythingElse(int row, int col) {
		if (row < tableType.getRowCount()) {
			return tableType.getValueAt(row, col);
		} else {
			return 1;
		}
	}

	@Override
	protected void setEnythingElse(Object value, int row, int col) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isEnythingElseEditable(int row, int col) {
		if (row < tableType.getRowCount()-2) {
			return tableType.isCellEditable(row, col);
		} else {
			if (col >= 0 && col < manager.getVariableCount()) {
				return true;
			}
			return false;
		}
	}

	@Override
	protected TableCellEditor getEnythingElseEditor(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getFirstRowHeight() {
		return tableType.getRowHeight(0);
	}

	@Override
	public int getColumnCount() {
		return super.getColumnCount() + 1;
	}

	@Override
	public int getRowCount() {
		return super.getRowCount() + 1;
	}

	@Override
	protected int getLastRowHeight() {
		return 40;
	}

}
