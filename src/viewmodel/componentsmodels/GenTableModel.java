package viewmodel.componentsmodels;

import javax.swing.table.AbstractTableModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class GenTableModel extends AbstractTableModel {

	Object data[][] = { { "eneration intervals", "low", "high" },
			{ "costs", 1, 100 }, { "weights", 1, 100 } };

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return 3;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (row == 0 || col == 0) {
			return false;
		}
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
