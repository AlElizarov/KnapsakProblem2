package view.components.tablemanagers;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import view.components.tablemanagers.renderers.HeaderRenderer;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableType;

public class TableManager implements ITableManager, Observer {

	private TaskManager manager;
	private ITableType tableType = new NotCreatedManager();

	public TableManager(TaskManager manager) {
		this.manager = manager;
		manager.addObserver(this);
	}

	@Override
	public ITableType getTableType() {
		return tableType;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		return tableType.getCellEditor(row, column);
	}

	@Override
	public int getColumnCount() {
		return tableType.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return tableType.getRowCount();
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return tableType.isCellEditable(row, col);
	}

	@Override
	public Object getValueAt(int row, int col) {
		return tableType.getValueAt(row, col);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (manager.isTaskEconom()) {
			tableType = new NotSolutionEconomManager(manager);
		} else {
			tableType = new NotSolutionNotEconomicManager(manager);
		}
		if(isFull()){
			manager.setTaskFull(true);
		}
	}

	@Override
	public void setValue(Object value, int row, int col) {
		tableType.setValue(value, row, col);
	}

	@Override
	public TableCellRenderer getHeaderRenderer() {
		return new HeaderRenderer(this);
	}

	@Override
	public int getColumnMargin() {
		return tableType.getColumnMargin();
	}

	@Override
	public String getColumnName(int col) {
		return tableType.getColumnName(col);
	}

	@Override
	public int getRowHeight(int row) {
		return tableType.getRowHeight(row);
	}

	@Override
	public String getRowName(int row) {
		return tableType.getRowName(row);
	}

	@Override
	public Color getUnFocusRow(int row) {
		return tableType.getUnFocusRow(row);
	}

	@Override
	public Color getFocusRow(int row) {
		return tableType.getFocusRow(row);
	}

	@Override
	public boolean isFull() {
		return tableType.isFull();
	}

	@Override
	public void onFullEvent() {
		manager.onFullEvent();
	}

}
