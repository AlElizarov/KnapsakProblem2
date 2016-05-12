package viewmodel.componentsmodels.tablemodelmanagers;

import java.awt.Color;

import javax.swing.table.TableCellEditor;

public interface ITableType{
	
	public Object getValueAt(int row, int col);

	public boolean isCellEditable(int row, int col);

	public TableCellEditor getCellEditor(int row, int column);

	public void setValue(Object value, int row, int col);

	public int getColumnMargin();

	public String getColumnName(int col);

	public int getRowHeight(int row);
	
	public int getColumnCount();

	public int getRowCount();

	public String getRowName(int row);

	public Color getUnFocusRow(int row);

	public Color getFocusRow(int row);

	public boolean isFull();

}
