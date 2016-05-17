package view.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

public class GenTableRenderer implements TableCellRenderer {

	private JLabel cell;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		cell = new JLabel();
		createCellProperties(value);
		if (row == 0 || column == 0) {
			createTableTitles(hasFocus, row, column);
		} else {
			createTableCells(hasFocus);
		}
		return cell;
	}

	private void createCellProperties(Object value) {
		cell.setText(value != null ? value.toString() : null);
		cell.setHorizontalAlignment(JLabel.CENTER);
		cell.setOpaque(true);
	}

	private void createTableTitles(boolean hasFocus, int row, int column) {
		cell.setBackground(Color.GREEN);
		if (hasFocus) {
			createFocusTitle();
		} else {
			createUnFocusTitle();
		}
		createTitleBorder(row, column);
	}

	private void createUnFocusTitle() {
		cell.setBorder(new LineBorder(null, 0));
		cell.setBackground(new Color(240, 255, 255));
	}

	private void createFocusTitle() {
		cell.setBorder(new LineBorder(new Color(99, 130, 191)));
		cell.setBackground(new Color(155, 228, 181));
	}

	private void createTitleBorder(int row, int column) {
		if (row == 0) {
			createColunmBorder(column);
		} else {
			createRowBorder();
		}
	}

	private void createColunmBorder(int column) {
		if (column == 0) {
			cell.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			cell.setBorder(new MatteBorder(1, 0, 1, 1, Color.BLACK));
		}
	}

	private void createRowBorder() {
		cell.setBorder(new MatteBorder(0, 1, 1, 1, Color.BLACK));
	}

	private void createTableCells(boolean hasFocus) {
		createCellsProperties();
		if (hasFocus) {
			createFocusCells();
		} else {
			createUnFocusCells();
		}
	}

	private void createCellsProperties() {
		cell.setBackground(Color.WHITE);
		cell.setBorder(new LineBorder(null, 0));
	}

	private void createFocusCells() {
		cell.setBorder(new LineBorder(new Color(99, 130, 191)));
		cell.setBackground(new Color(155, 228, 181));
	}

	private void createUnFocusCells() {
		cell.setBorder(new LineBorder(null, 0));
		cell.setBackground(Color.WHITE);
	}

}
