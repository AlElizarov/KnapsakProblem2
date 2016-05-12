package view.components.tablemanagers.editors;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class StringCellEditor extends AbstractCellEditor implements TableCellEditor {

	private JTextField field = new JTextField();

	@Override
	public Object getCellEditorValue() {
		return field.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		if (value != null) {
			field.setText(value.toString());
		}
		return field;
	}

}
