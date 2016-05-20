package view.frames;

import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import viewmodel.TaskManager;

import com.toedter.calendar.JDateChooser;

//I/m not going to serialize this class
@SuppressWarnings("serial")
public class SaveView extends AbstractInteractiveInternalFrame {

	private JCheckBox canRewrie;
	private JDateChooser chooser;
	private JComboBox<String> authors;

	public SaveView(Desktop desktop, TaskManager manager) {
		super("save", desktop, 400, 500, 200, 150, manager);
	}

	@Override
	protected void createOkAction() {
		Date sqlDate = new Date(chooser.getDate().getTime());
		try {
			manager.save((String) authors.getSelectedItem(), sqlDate, canRewrie.isSelected());
		} catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Problems with database connection");
		}
		SaveView.this.dispose();
	}

	@Override
	public void openingBind() {
	}

	@Override
	protected void fullPanel() {
		authors = new JComboBox<>(manager.getAuthorsNames());
		JLabel authorLabel = new JLabel("Choice your name:");
		panel.add(authorLabel, "wrap");
		panel.add(authors);
		createMargin();

		panel.add(new JLabel("date:"), "wrap");
		chooser = new JDateChooser();
		panel.add(chooser);
		createMargin();
		
		canRewrie = new JCheckBox();
		canRewrie.setSelected(true);
		panel.add(new JLabel("<html>Whether this task<br>could be rewrite?"), "wrap");
		panel.add(canRewrie);
		createMargin();
	}
	
	private void createMargin() {
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
	}

}
