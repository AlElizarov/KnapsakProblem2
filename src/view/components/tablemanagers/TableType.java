package view.components.tablemanagers;

import java.awt.Color;

import javax.swing.table.TableCellEditor;

import view.components.tablemanagers.editors.DataCellEditor;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.TooBigNumberException;
import viewmodel.componentsmodels.tablemodelmanagers.ITableType;

abstract class TableType implements ITableType {

	protected TaskManager manager;
	protected int columnMargin;
	protected int rowMargin;

	public TableType(TaskManager task) {
		this.manager = task;
		setColumnMargin();
		setRowMargin();
	}

	@Override
	public Object getValueAt(int row, int col) {
		row -= rowMargin;
		col -= columnMargin;
		if (isDataCell(row, col)) {
			if (isLimitCell(row, col)) {
				return manager.getValue(row, col - 1);
			} else {
				return manager.getValue(row, col);
			}
		}
		return getAllTheRest(row, col);
	}

	protected Object getAllTheRest(int row, int col) {
		if (col == manager.getVariableCount()) {
			if (row < manager.getCriterionCount()) {
				if (row >= 0) {
					return "-->";
				}
			} else {
				return manager.isMax() ? "<=" : ">=";
			}
		} else {
			return gerEnythingElse(row, col);
		}
		return null;
	}

	protected abstract Object gerEnythingElse(int row, int col);

	@Override
	public void setValue(Object value, int row, int col) {
		row -= rowMargin;
		col -= columnMargin;
		if (isDataCell(row, col)) {
			setValueInDataCell(value, row, col);
		}
		setEnythingElse(value, row, col);
	}

	protected abstract void setEnythingElse(Object value, int row, int col);

	private void setValueInDataCell(Object value, int row, int col) {
		try {
			int intValue = Integer.parseInt(value.toString());
			if (isLimitCell(row, col)) {
				manager.setValue(intValue, row, col - 1);
			} else {
				manager.setValue(intValue, row, col);
			}
		} catch (NumberFormatException exc) {
			throw new TooBigNumberException();
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		col -= columnMargin;
		row -= rowMargin;
		if (isDataCell(row, col)) {
			return true;
		}
		if (isTypeColumn(col)) {
			return false;
		}
		return isEnythingElseEditable(row, col);
	}

	private boolean isLimitCell(int row, int col) {
		return col == manager.getVariableCount() + 1
				&& row >= manager.getCriterionCount();
	}

	private boolean isTypeColumn(int col) {
		return col == manager.getVariableCount();
	}

	private boolean isDataCell(int row, int col) {
		return isLimitationCell(row, col) || isCriterionCell(row, col)
				|| isLimitCell(row, col);
	}

	private boolean isCriterionCell(int row, int col) {
		return checkColumn(col)
				&& (row >= 0 && row < manager.getCriterionCount());
	}

	private boolean isLimitationCell(int row, int col) {
		return checkColumn(col) && (row >= manager.getCriterionCount());
	}

	private boolean checkColumn(int col) {
		return col >= 0 && col < manager.getVariableCount();
	}

	@Override
	public int getColumnCount() {
		return manager.getVariableCount() + 2 + columnMargin;
	}

	@Override
	public int getRowCount() {
		return manager.getLimitationCount() + manager.getCriterionCount()
				+ rowMargin;
	}

	protected abstract boolean isEnythingElseEditable(int row, int col);

	protected abstract void setColumnMargin();

	protected abstract void setRowMargin();

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		row -= rowMargin;
		column -= columnMargin;
		if (isDataCell(row, column)) {
			return new DataCellEditor();
		}
		return getEnythingElseEditor(row, column);
	}

	protected abstract TableCellEditor getEnythingElseEditor(int row, int column);

	@Override
	public int getColumnMargin() {
		return columnMargin;
	}

	@Override
	public String getColumnName(int col) {
		col -= columnMargin;
		if (col == -2) {
			return "Name";
		} else if (col == -1) {
			return "Unit";
		} else if (col == manager.getVariableCount()) {
			return "Type";
		} else if (col == manager.getVariableCount() + 1) {
			return "B";
		} else {
			return "X" + (col + 1);
		}
	}

	@Override
	public int getRowHeight(int row) {
		if (row == 0) {
			return getFirstRowHeight();
		}
		return 20;
	}

	@Override
	public String getRowName(int row) {
		row -= rowMargin;
		if (row == -1) {
			return "Economic";
		}
		if (row < manager.getCriterionCount()) {
			return "F" + (row + 1);
		} else {
			return "Y" + (row - manager.getCriterionCount() + 1);
		}
	}

	@Override
	public Color getUnFocusRow(int row) {
		row -= rowMargin;
		if (row == -1) {
			return Color.YELLOW;
		}
		if (row < manager.getCriterionCount()) {
			return Color.LIGHT_GRAY;
		}
		if (row >= manager.getCriterionCount()) {
			return Color.GRAY;
		}
		return null;
	}

	@Override
	public Color getFocusRow(int row) {
		row -= rowMargin;
		if (row == -1) {
			return new Color(255, 215, 0);
		}
		if (row < manager.getCriterionCount()) {
			return new Color(230, 230, 250);
		}
		if (row >= manager.getCriterionCount()) {
			return new Color(112, 128, 144);
		}
		return null;
	}

	protected abstract int getFirstRowHeight();

}
