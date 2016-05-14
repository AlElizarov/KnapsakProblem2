package view.components.tablemanagers;

import java.awt.Color;

import javax.swing.table.TableCellEditor;

import viewmodel.componentsmodels.tablemodelmanagers.ITableType;

class NotCreatedManager implements ITableType {

	@Override
	public Object getValueAt(int row, int col) {
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 0;
	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		return null;
	}

	@Override
	public void setValue(Object value, int row, int col) {
	}

	@Override
	public int getColumnMargin() {
		return 0;
	}

	@Override
	public String getColumnName(int col) {
		return null;
	}

	@Override
	public int getRowHeight(int row) {
		return 0;
	}

	@Override
	public String getRowName(int row) {
		return null;
	}

	@Override
	public Color getUnFocusRow(int row) {
		return null;
	}

	@Override
	public Color getFocusRow(int row) {
		return null;
	}

}
