package view.components.tablemanagers.editors;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class UnitsCellEditor extends AbstractCellEditor implements TableCellEditor{

	private JComboBox<String> comboBox = new JComboBox<>();
	
	public UnitsCellEditor() {
		comboBox.addItem("kg");
		comboBox.addItem("m");
		comboBox.addItem("$");
		comboBox.setSelectedItem(0);
	}
	
	@Override
	public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return comboBox;
	}

}
