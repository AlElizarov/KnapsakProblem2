package view.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import view.frames.AboutProgrammView;
import view.frames.AbstractDecoratedInternalFrame;
import view.frames.AbstractInteractiveInternalFrame;
import view.frames.Desktop;
import view.frames.NewTaskCreatingView;
import viewmodel.TaskManager;
import viewmodel.areasmodels.MenuBarViewModel;

import com.sun.glass.events.KeyEvent;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class MenuBarView extends JMenuBar implements BindableArea{

	private JMenu menu;
	private JMenuItem item;
	private MenuBarViewModel menuBarViewModel;
	private Desktop desktop;
	private TaskManager manager;

	public MenuBarView(Desktop desktop, TaskManager manager) {
		this.manager = manager;
		menuBarViewModel = new MenuBarViewModel(manager);
		this.desktop = desktop;
		createMenuTask();
		add(menu);
		createMenuSolve();
		add(menu);
		createMenuHelp();
		add(menu);
		createMenuTable();
		add(menu);
	}

	private void createMenuTable() {
		menu = new JMenu("Table");
		createDeleteRow();
		createAddRow();
		createAddVarItem();
		createDeleteVarItem();
		createGenItem();
	}

	private void createDeleteRow() {
		JMenu deleteRow = new JMenu("Delete row");
		item = new JMenuItem("Delete limitation");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteLimitation();
			}
		});
		deleteRow.add(item);
		item = new JMenuItem("Delete criterion");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteCrit();
			}
		});
		deleteRow.add(item);
		menu.add(deleteRow);
	}

	private void createAddRow() {
		JMenu addRow = new JMenu("Add row");
		item = new JMenuItem("Add limitation");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.addLimitation();
			}
		});
		addRow.add(item);
		item = new JMenuItem("Add criterion");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.addCriterion();
			}
		});
		addRow.add(item);
		menu.add(addRow);
	}

	private void createGenItem() {
		item = new JMenuItem("Generation");
		menu.add(item);
	}

	private void createDeleteVarItem() {
		item = new JMenuItem("Delete variable");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteVariable();
			}
		});
		menu.add(item);
	}

	private void createAddVarItem() {
		item = new JMenuItem("Add variable");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.addVariable();
			}
		});
		menu.add(item);
	}

	private void createMenuHelp() {
		menu = new JMenu("Help");
		createReferenceItem();
		createAboutItem();
	}

	private void createAboutItem() {
		item = new JMenuItem("About", new ImageIcon("images/ic_info_18pt.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.setLayout(null);
				AbstractDecoratedInternalFrame iframe = new AboutProgrammView(
						desktop);
				desktop.addIFrame(iframe);
			}
		});
		menu.add(item);
	}

	private void createReferenceItem() {
		item = new JMenuItem("Enquiry", new ImageIcon(
				"images/ic_live_help_18pt.png"));
		menu.add(item);
	}

	private void createMenuSolve() {
		menu = new JMenu("Solution");
		createAutoItem();
		createStudyItem();
		createManuallyItem();
		createEconomItem();
	}

	private void createEconomItem() {
		item = new JMenuItem("Economics");
		menu.add(item);
	}

	private void createManuallyItem() {
		item = new JMenuItem("Handless");
		menu.add(item);
	}

	private void createStudyItem() {
		item = new JMenuItem("Educative");
		menu.add(item);
	}

	private void createAutoItem() {
		item = new JMenuItem("Auto mode");
		menu.add(item);
	}

	private void createMenuTask() {
		menu = new JMenu("Task");
		createNewItem();
		createSaveItem();
		createReadItem();
	}

	private void createNewItem() {
		item = new JMenuItem("New");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.setLayout(null);
				AbstractInteractiveInternalFrame iframe = new NewTaskCreatingView(
						desktop, manager);
				desktop.addIFrame(iframe);
				iframe.openingBind();
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	private void createReadItem() {
		item = new JMenuItem("Read", new ImageIcon(
				"images/ic_folder_open_18pt.png"));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	private void createSaveItem() {
		item = new JMenuItem("Save", new ImageIcon("images/ic_save_18pt.png"));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	@Override
	public void bind() {
		getMenu(0).getItem(1).setEnabled(
				menuBarViewModel.isSaveAndSolutionEnable());
		getMenu(1).setEnabled(menuBarViewModel.isSaveAndSolutionEnable());
		getMenu(1).getItem(0).setEnabled(menuBarViewModel.isSaveAndSolutionEnable());
		getMenu(1).getItem(1).setEnabled(menuBarViewModel.isSaveAndSolutionEnable());
		getMenu(1).getItem(2).setEnabled(menuBarViewModel.isSaveAndSolutionEnable());
		getMenu(1).getItem(3).setEnabled(menuBarViewModel.isEconomButtonEnabled());
		getMenu(3).setEnabled(menuBarViewModel.isTableMenuEnabled());
	}

}
