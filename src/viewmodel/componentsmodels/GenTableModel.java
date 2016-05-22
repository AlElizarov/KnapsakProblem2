package viewmodel.componentsmodels;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class GenTableModel extends AbstractTableModel {

	private Object data[][] = { { "eneration intervals", "low", "high" },
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
		int old = Integer.parseInt(data[row][col].toString());
		data[row][col] = value;
		if (Integer.parseInt(data[row][1].toString()) >= Integer.parseInt(data[row][2].toString())) {
			JOptionPane.showMessageDialog(null,
					"Lower bound must be less yhen upper bound!");
			data[row][col] = old;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

}
