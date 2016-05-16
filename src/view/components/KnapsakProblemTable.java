package view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class KnapsakProblemTable extends JTable {

	private ITableManager manager;

	public KnapsakProblemTable(ITableManager manager) {
		this.manager = manager;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellSelectionEnabled(true);
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(true);
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return getPreferredSize().width < getParent().getWidth();
	}

	// что бы столбцы расширялись по содержимому
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component component = super.prepareRenderer(renderer, row, column);
		int rendererWidth = component.getPreferredSize().width;
		TableColumn tableColumn = getColumnModel().getColumn(column);
		tableColumn
				.setPreferredWidth(Math.max(rendererWidth
						+ getIntercellSpacing().width,
						tableColumn.getPreferredWidth()));
		return component;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		return manager.getCellEditor(row, column);
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

	@Override
	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		if (manager != null && manager.getRowCount() > 0) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					createColumnNames();
					createRowHeights();

					setColumnSelectionInterval(0, 0);
					setRowSelectionInterval(0, 0);
					requestFocus();
					KnapsakProblemTable.this.getTableHeader().repaint();
					KnapsakProblemTable.this.getTableHeader().revalidate();
				}

				private void createRowHeights() {
					for (int i = 0; i < KnapsakProblemTable.this.getRowCount(); i++) {
						KnapsakProblemTable.this.setRowHeight(i,
								manager.getRowHeight(i));
					}
				}

				private void createColumnNames() {
					for (int i = 0; i < KnapsakProblemTable.this
							.getColumnModel().getColumnCount(); i++) {
						KnapsakProblemTable.this.getColumnModel().getColumn(i)
								.setHeaderRenderer(manager.getHeaderRenderer());
					}
				}
			});
		}
	}

}
