package view.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.TemplateLoader;
import view.frames.AboutProgrammView;
import view.frames.AbstractDecoratedInternalFrame;
import view.frames.AbstractInteractiveInternalFrame;
import view.frames.AddAuthorFrame;
import view.frames.DeleteAuthorFrame;
import view.frames.Desktop;
import view.frames.GenerationAreaView;
import view.frames.NewTaskCreatingView;
import view.frames.ReadView;
import view.frames.SaveView;
import viewmodel.TaskManager;
import viewmodel.areasmodels.MenuBarViewModel;

import com.sun.glass.events.KeyEvent;

import db.TaskMapper;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class MenuBarView extends JMenuBar implements BindableArea {

	private JMenu menu;
	private JMenuItem item;
	private MenuBarViewModel menuBarViewModel;
	private Desktop desktop;
	private TaskManager manager;
	private final String url = "jdbc:mysql://localhost:3306/knapsak";
	private final String user = "root";
	private final String password = "npibdvjnpeov";

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
		createMenuAuthors();
		add(menu);
		createMenuReports();
		add(menu);
	}

	private void createMenuReports() {
		menu = new JMenu("Reports");
		createTaskreport();
		menu.add(item);
		createAuthorsreport();
		menu.add(item);
	}

	private void createAuthorsreport() {
		item = new JMenuItem("Authors report");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TaskMapper mapper = new TaskMapper();
				Vector<String> aus = mapper.getAuthorsNames();

				String query = "";
				for (int au = 0; au < aus.size(); au++) {
					aus.set(au, aus.get(au).trim());

					String queryTemp = "select count(id) from tasks where id_author = "
							+ "(select id from authors where CONCAT(surname, ' ', authors.name, ' ', futhername) = \'"+aus.get(au)+"\')";
					
					int count = 0;
					try (Connection con = DriverManager.getConnection(url, user,
							password);
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery(queryTemp);) {

						while (rs.next()) {
							count = rs.getInt(1);
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					if(count > 0){
					query += "(select CONCAT(surname, \' \', authors.name, \' \', futhername) as author, tasks.name as name"
							+ " from authors, tasks"
							+ " where CONCAT(surname, \' \', authors.name, \' \', futhername) = \'"
							+ aus.get(au)
							+ "\'"
							+ " and tasks.id_author = authors.id"
							+ " order by name)";
					query += " UNION ALL";
					query += "(select author, CONCAT(\'Sum: \', count(name)) as name from TempAuthors"
							+ " where author = \'"
							+ aus.get(au)
							+ "\'"
							+ " order by name)";
					if (au < aus.size() - 1) {
						query += " UNION ALL";
					}
					}
				}

				Map map = new HashMap();
				map.put("date", new Date(new java.util.Date().getTime()));

				try (Connection con = DriverManager.getConnection(url, user,
						password);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);) {

					try {
						InputStream reportStream = TemplateLoader
								.loadTemplate("reportAuthors.jrxml");
						JasperDesign jasperDesign = JRXmlLoader
								.load(reportStream);
						JasperReport jasperReport = JasperCompileManager
								.compileReport(jasperDesign);

						JasperPrint jasperPrint = JasperFillManager.fillReport(
								jasperReport, map,
								new JRResultSetDataSource(rs));
						JasperViewer jw = new JasperViewer(jasperPrint, false);
						jw.setVisible(true);

					} catch (JRException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	private void createTaskreport() {
		item = new JMenuItem("Task report");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!manager.isTaskCreated()) {
					JOptionPane.showMessageDialog(null, "Choice task!");
					return;
				}
				String queryStart = "select min(id) from criterions where id_task = (select id from tasks where "
						+ checkTask() + ")";
				int startIdCr = 0;
				try (Connection con = DriverManager.getConnection(url, user,
						password);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(queryStart);) {

					while (rs.next()) {
						startIdCr = rs.getInt(1);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				String queryStart2 = "select min(id) from variables where id_task = (select id from tasks where "
						+ checkTask() + ")";
				int startIdVar = 0;
				try (Connection con = DriverManager.getConnection(url, user,
						password);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(queryStart2);) {

					while (rs.next()) {
						startIdVar = rs.getInt(1);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				String queryStart3 = "select min(id) from limitations where id_task = (select id from tasks where "
						+ checkTask() + ")";
				int startIdLim = 0;
				try (Connection con = DriverManager.getConnection(url, user,
						password);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(queryStart3);) {

					while (rs.next()) {
						startIdLim = rs.getInt(1);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}

				String query = "";
				for (int row = 0; row < manager.getCriterionCount(); row++) {
					for (int col = 0; col < manager.getVariableCount(); col++) {
						query += "select \'f"
								+ (row + 1)
								+ "\' as f,"
								+ "(select if(isEconom = true, criterions.name, \'-\') from tasks where "
								+ checkTask()
								+ ") as cname,"
								+ "(select if(isEconom = true, unit, \'-\') from tasks where "
								+ checkTask()
								+ ") as unit,"
								+ "\'v"
								+ (col + 1)
								+ "\'as v, (select if(isEconom = true, variables.name, \'-\') from tasks where "
								+ checkTask()
								+ ") as vname,"
								+ "cost as res, \'-->\' as T, (select"
								+ " if(isMax = true,\'MAX\', \'MIN\') from tasks where "
								+ checkTask() + ") as B"
								+ " from criterions, costs, variables"
								+ " where criterions.id = " + (row + startIdCr)
								+ " and costs.id_criterion = "
								+ (row + startIdCr)
								+ " and costs.id_variable = "
								+ (col + startIdVar)
								+ " group by f, v UNION ALL ";

					}
				}

				for (int row = 0; row < manager.getLimitationCount(); row++) {
					for (int col = 0; col < manager.getVariableCount(); col++) {
						query += "select \'y"
								+ (row + 1)
								+ "\' as f,"
								+ "(select if(isEconom = true, limitations.name, \'-\') from tasks where "
								+ checkTask()
								+ ") as cname,"
								+ "(select if(isEconom = true, unit, \'-\') from tasks where "
								+ checkTask()
								+ ") as unit,"
								+ "\'v"
								+ (col + 1)
								+ "\'as v, (select if(isEconom = true, variables.name, \'-\') from tasks where "
								+ checkTask()
								+ ") as vname,"
								+ "weight as res, (select if(isMax = true, \'<=\', \'>=\') from tasks where "
								+ checkTask() + ") as T, limitValue as B"
								+ " from limitations, weights, variables"
								+ " where limitations.id = "
								+ (row + startIdLim)
								+ " and weights.id_limitation = "
								+ (row + startIdLim)
								+ " and weights.id_variable = "
								+ (col + startIdVar) + " group by f, v ";
						if (row < (manager.getLimitationCount() - 1)
								|| col < (manager.getVariableCount() - 1)) {
							query += "UNION ALL ";
						}
					}
				}

				Map map = new HashMap();
				map.put("taskName", manager.getTaskName());
				map.put("au", manager.getAuthor());
				map.put("date", manager.getDate());
				map.put("note",
						(manager.getNote() == null ? "---" : manager.getNote()));

				try (Connection con = DriverManager.getConnection(url, user,
						password);
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);) {

					try {
						InputStream reportStream = TemplateLoader
								.loadTemplate("reportTasks.jrxml");
						JasperDesign jasperDesign = JRXmlLoader
								.load(reportStream);
						JasperReport jasperReport = JasperCompileManager
								.compileReport(jasperDesign);

						JasperPrint jasperPrint = JasperFillManager.fillReport(
								jasperReport, map,
								new JRResultSetDataSource(rs));
						JasperViewer jw = new JasperViewer(jasperPrint, false);
						jw.setVisible(true);

					} catch (JRException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			private String checkTask() {
				String temp = "name = \'"
						+ manager.getTaskName()
						+ "\' and id_author = (select id from authors where "
						+ "(select CONCAT(surname, \' \', name, \' \', futhername) = \'"
						+ manager.getAuthor() + "\') and dateOfCreating = \'"
						+ manager.getDate2() + "\')";
				return temp;
			}
		});
	}

	private void createMenuAuthors() {
		menu = new JMenu("Authors");
		createAddAuthor();
		createDeleteAuthor();
	}

	private void createDeleteAuthor() {
		item = new JMenuItem("delete");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.setLayout(null);
				DeleteAuthorFrame iframe = new DeleteAuthorFrame(desktop,
						manager);
				desktop.addIFrame(iframe);
			}
		});
		menu.add(item);
	}

	private void createAddAuthor() {
		item = new JMenuItem("add");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.setLayout(null);
				AddAuthorFrame iframe = new AddAuthorFrame(desktop, manager,
						null);
				desktop.addIFrame(iframe);
			}
		});
		menu.add(item);
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
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.setLayout(null);
				AbstractInteractiveInternalFrame iframe = new GenerationAreaView(
						desktop, manager);
				desktop.addIFrame(iframe);
			}
		});
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
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.execute();
			}
		});
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
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	private void createReadItem() {
		item = new JMenuItem("Read", new ImageIcon(
				"images/ic_folder_open_18pt.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.setLayout(null);
				ReadView iframe = new ReadView(desktop, manager);
				desktop.addIFrame(iframe);
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	private void createSaveItem() {
		item = new JMenuItem("Save", new ImageIcon("images/ic_save_18pt.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.setLayout(null);
				SaveView iframe = new SaveView(desktop, manager);
				desktop.addIFrame(iframe);
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		menu.add(item);
	}

	@Override
	public void bind() {
		getMenu(0).getItem(1).setEnabled(menuBarViewModel.isSaveEnable());
		getMenu(1).setEnabled(menuBarViewModel.isSolutionEnable());
		getMenu(1).getItem(0).setEnabled(menuBarViewModel.isSolutionEnable());
		getMenu(1).getItem(1).setEnabled(menuBarViewModel.isSolutionEnable());
		getMenu(1).getItem(2).setEnabled(menuBarViewModel.isSolutionEnable());
		getMenu(1).getItem(3).setEnabled(
				menuBarViewModel.isEconomButtonEnabled());
		getMenu(3).setEnabled(menuBarViewModel.isTableMenuEnabled());
	}

}
