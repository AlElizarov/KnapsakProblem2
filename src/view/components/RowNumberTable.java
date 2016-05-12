package view.components;

import java.awt.Component;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

//I'm not going to serialize this class
@SuppressWarnings("serial")
public class RowNumberTable extends JTable implements ChangeListener,
		PropertyChangeListener, TableModelListener{

	private JTable mainTable;
	private ITableManager tableManager;

	public RowNumberTable(JTable table, ITableManager tableManager) {
		mainTable = table;
		this.tableManager = tableManager;
		mainTable.addPropertyChangeListener(this);
		mainTable.getModel().addTableModelListener(this);

		setFocusable(false);
		setAutoCreateColumnsFromModel(false);
		setSelectionModel(mainTable.getSelectionModel());

		createColumn();
		setPreferredScrollableViewportSize(getPreferredSize());
	}

	private void createColumn() {
		TableColumn column = new TableColumn();
		column.setHeaderValue(" ");
		addColumn(column);
		column.setCellRenderer(new RowNumberRenderer(tableManager));
		getColumnModel().getColumn(0).setPreferredWidth(100);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		Component c = getParent();

		// Keep scrolling of the row table in sync with the main table.

		if (c instanceof JViewport) {
			JViewport viewport = (JViewport) c;
			viewport.addChangeListener(this);
		}
	}

	/*
	 * Delegate method to main table
	 */
	@Override
	public int getRowCount() {
		return mainTable.getRowCount();
	}

	@Override
	public int getRowHeight(int row) {
		int rowHeight = mainTable.getRowHeight(row);

		if (rowHeight != super.getRowHeight(row)) {
			super.setRowHeight(row, rowHeight);
		}

		return rowHeight;
	}

	/*
	 * No model is being used for this table so just use the row number as the
	 * value of the cell.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		return tableManager.getRowName(row);
	}

	/*
	 * Don't edit data in the main TableModel by mistake
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/*
	 * Do nothing since the table ignores the model
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
	}

	//
	// Implement the ChangeListener
	//
	public void stateChanged(ChangeEvent e) {
		// Keep the scrolling of the row table in sync with main table

		JViewport viewport = (JViewport) e.getSource();
		JScrollPane scrollPane = (JScrollPane) viewport.getParent();
		scrollPane.getVerticalScrollBar()
				.setValue(viewport.getViewPosition().y);
	}

	//
	// Implement the PropertyChangeListener
	//
	public void propertyChange(PropertyChangeEvent e) {
		// Keep the row table in sync with the main table

		if ("selectionModel".equals(e.getPropertyName())) {
			setSelectionModel(mainTable.getSelectionModel());
		}

		if ("rowHeight".equals(e.getPropertyName())) {
			repaint();
		}

		if ("model".equals(e.getPropertyName())) {
			setRowHeight(20);
			mainTable.getModel().addTableModelListener(this);
			for (int row = 0; row < getRowCount(); row++) {
				setRowHeight(row, mainTable.getRowHeight(row));
			}
			revalidate();
		}
	}

	//
	// Implement the TableModelListener
	//
	@Override
	public void tableChanged(TableModelEvent e) {
		revalidate();
	}

	/*
	 * Attempt to mimic the table header renderer
	 */
	private static class RowNumberRenderer extends DefaultTableCellRenderer {
		
		private ITableManager tableManager;
		
		public RowNumberRenderer(ITableManager tableManager) {
			this.tableManager = tableManager;
			setHorizontalAlignment(JLabel.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (table != null) {
				JTableHeader header = table.getTableHeader();

				if (header != null) {
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}

			if (isSelected) {
				setFont(getFont().deriveFont(Font.BOLD));
				setBackground(tableManager.getFocusRow(row));
			}
			else{
				setBackground(tableManager.getUnFocusRow(row));
			}

			setText((value == null) ? "" : value.toString());
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));

			return this;
		}
	}
}
