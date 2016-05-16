package view.components.tablemanagers;

import java.awt.Color;

import javax.swing.table.TableCellEditor;

import view.components.tablemanagers.editors.BoolCellEditor;
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
		columnMarginRight = 1;
	}

	@Override
	protected Object gerEnythingElse(int row, int col) {
		if (isNotSolutionCell(row, col)) {
			return tableType.getValueAt(row + rowMargin, col + columnMargin);
		} else {
			if (col >= 0 && col < manager.getVariableCount()) {
				return manager.getSolutionVariable(col) ? 1 : 0;
			}
			if (row > -1 && row < (tableType.getRowCount() - rowMargin)
					&& col == manager.getVariableCount() + 2) {
				return manager.getSum(row);
			}
			return null;
		}
	}

	@Override
	protected void setEnythingElse(Object value, int row, int col) {
		if (isNotSolutionCell(row, col)) {
			tableType.setValue(value, row + rowMargin, col + columnMargin);
		} else {
			if (col >= 0 && col < manager.getVariableCount()) {
				manager.setSolutionVariable((int) value == 1 ? true : false,
						col);
			}
		}
	}

	@Override
	protected boolean isEnythingElseEditable(int row, int col) {
		if (isNotSolutionCell(row, col)) {
			return tableType
					.isCellEditable(row + rowMargin, col + columnMargin);
		} else {
			if (col >= 0 && col < manager.getVariableCount()) {
				return true;
			}
			return false;
		}
	}

	private boolean isNotSolutionCell(int row, int col) {
		return row < tableType.getRowCount() - rowMargin
				&& col < tableType.getColumnCount() - columnMargin;
	}

	@Override
	protected TableCellEditor getEnythingElseEditor(int row, int col) {
		if (isNotSolutionCell(row, col)) {
			return tableType.getCellEditor(row + rowMargin, col + columnMargin);
		}
		return new BoolCellEditor();
	}

	@Override
	protected int getFirstRowHeight() {
		return tableType.getRowHeight(0);
	}

	@Override
	public int getColumnCount() {
		return tableType.getColumnCount() + 1;
	}

	@Override
	public int getRowCount() {
		return tableType.getRowCount() + 1;
	}

	@Override
	protected int getLastRowHeight() {
		return 40;
	}

	@Override
	public Color getColumnColor(int col) {
		col -= columnMargin;
		if ((col + columnMargin) == getColumnCount() - columnMarginRight) {
			if (manager.allSumsOk()) {
				return Color.GREEN;
			}
			return Color.RED;
		} else {
			if (col + columnMargin >= manager.getVariableCount() || col < 0) {
				return Color.LIGHT_GRAY;
			}
			if (manager.getSolutionVariable(col)) {
				return Color.GRAY;
			} else {
				return Color.LIGHT_GRAY;
			}
		}
	}

}
