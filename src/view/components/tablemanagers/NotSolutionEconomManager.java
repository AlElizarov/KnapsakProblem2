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
			return manager.getEconomValue(row + 1, col);
		}
		if (col == -2 && row > -1) {
			return manager.getEconomValue(row+1, col + 2);
		}

		if (col == -1 && row > -1) {
			return manager.getEconomValue(row+1, col + 2);
		}
		return null;
	}

	@Override
	protected void setEnythingElse(Object value, int row, int col) {
		if (row == -1) {
			manager.setEconomValue(value, row + 1, col);
		} else if (col == -2) {
			manager.setEconomValue(value, row+1, col + 2);
		} else if (col == -1) {
			manager.setEconomValue(value, row+1, col + 2);
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

}
