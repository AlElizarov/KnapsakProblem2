package view.components.tablemanagers.editors;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class DataCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	private JTextField field = new JTextField();

	@Override
	public Object getCellEditorValue() {
		return field.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		((AbstractDocument) field.getDocument())
				.setDocumentFilter(new DocumentFilter() {
					@Override
					public void insertString(FilterBypass fb, int off,
							String str, AttributeSet attr)
							throws BadLocationException {
						fb.insertString(off, str.replaceAll("\\D++", ""), attr); // remove
																					// non-digits
					}

					@Override
					public void replace(FilterBypass fb, int off, int len,
							String str, AttributeSet attr)
							throws BadLocationException {
						fb.replace(off, len, str.replaceAll("\\D++", ""), attr); // remove
																					// non-digits
					}
				});
		return field;
	}

}
