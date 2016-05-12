package viewmodel.componentsmodels.tablemodelmanagers;

import java.awt.Color;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public interface ITableManager {

	public ITableType getTableType();

	public TableCellEditor getCellEditor(int row, int column);

	public int getColumnCount();

	public int getRowCount();

	public boolean isCellEditable(int row, int col);

	public Object getValueAt(int row, int col);

	public void setValue(Object value, int row, int col);

	public TableCellRenderer getHeaderRenderer();

	public int getColumnMargin();

	int getRowHeight(int row);

	String getColumnName(int col);

	public String getRowName(int row);

	public Color getUnFocusRow(int row);

	public Color getFocusRow(int row);

	boolean isFull();

	void onFullEvent();

}
