package view.components.tablemanagers.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

public class TableRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JLabel cell = new JLabel();
		cell.setHorizontalAlignment(JLabel.CENTER);
		cell.setOpaque(true);
		if (value != null)
			cell.setText(value.toString());
		if (table.isCellSelected(row, col)) {
			cell.setBackground(Color.GREEN);
			cell.setBorder(new LineBorder(Color.black, 1));
		} else {
			cell.setBackground(Color.WHITE);
		}
		return cell;
	}

}
