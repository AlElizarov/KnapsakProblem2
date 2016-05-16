package view.components.tablemanagers.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class HeaderRenderer extends JLabel implements TableCellRenderer {

	private ITableManager tableManager;

	public HeaderRenderer(ITableManager tableManager) {
		this.tableManager = tableManager;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean arg2, boolean arg3, int row, int col) {
		setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		setForeground(Color.BLACK);
		setHorizontalAlignment(JLabel.CENTER);
		setColumnText(tableManager.getColumnName(col));
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setBackground(tableManager.getColumnColor(col));
		setOpaque(true);
		return this;
	}

	private void setColumnText(String columnText) {
		setText(columnText);
		setToolTipText(columnText);
	}

}
