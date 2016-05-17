package view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import view.components.tablemanagers.editors.DataCellEditor;
import viewmodel.componentsmodels.GenTableModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class GenTable extends JTable {

	public GenTable() {
		setModel(new GenTableModel());
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		return new DataCellEditor();
	}

	@Override
	public Component prepareEditor(TableCellEditor editor, int row, int column) {
		final Component editorComponent = super.prepareEditor(editor, row,
				column);
		if (editorComponent instanceof JTextComponent) {
			((JTextComponent) editorComponent).setBorder(new LineBorder(
					Color.RED));
			((JTextComponent) editorComponent).selectAll();
		}
		return editorComponent;
	}

}
