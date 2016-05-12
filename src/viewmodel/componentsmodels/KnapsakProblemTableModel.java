package viewmodel.componentsmodels;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class KnapsakProblemTableModel extends AbstractTableModel {

	private ITableManager tableManager;

	public KnapsakProblemTableModel(ITableManager tableManager) {
		this.tableManager = tableManager;
	}

	@Override
	public int getColumnCount() {
		return tableManager.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return tableManager.getRowCount();
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return tableManager.isCellEditable(row, col);
	}

	@Override
	public Object getValueAt(int row, int col) {
		return tableManager.getValueAt(row, col);
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (!value.toString().equals("")) {
			try {
				tableManager.setValue(value, row, col);
				if (tableManager.isFull()) {
					tableManager.onFullEvent();
				}
			} catch (TooBigNumberException exc) {
				showMsg("Please, don't enter values bigger then "
						+ Integer.MAX_VALUE);
			}
		}
	}

	@Override
	public void fireTableStructureChanged() {
		System.out.println("yes");
		super.fireTableStructureChanged();
		if (tableManager.isFull()) {
			tableManager.onFullEvent();
		}
	}

	private void showMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

}
