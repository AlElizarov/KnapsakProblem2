package view.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import viewmodel.TaskManager;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.toedter.calendar.JDateChooser;

//I/m not going to serialize this class
@SuppressWarnings("serial")
public class SaveView extends AbstractInteractiveInternalFrame {

	private JCheckBox canRewrie;
	private JDateChooser chooser;
	private JComboBox<String> authors;
	private JTextArea note;

	public SaveView(Desktop desktop, TaskManager manager) {
		super("save", desktop, 400, 500, 200, 150, manager);
		if(authors.getItemCount() == 0){
			ok.setEnabled(false);
		}
	}

	@Override
	protected void createOkAction() {
		Date sqlDate = new Date(chooser.getDate().getTime());
		try {
			manager.save((String) authors.getSelectedItem(), sqlDate, canRewrie.isSelected(), note.getText());
			manager.setCanRewrite(canRewrie.isSelected());
		} 
		catch (MySQLIntegrityConstraintViolationException exc){
			exc.printStackTrace();
			JOptionPane.showMessageDialog(null, "Task already exists");
		}
		catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problems with database connection");
		}
		SaveView.this.dispose();
	}

	@Override
	public void openingBind() {
	}

	@Override
	public void fullPanel() {
		createAuthorsList();
		JLabel authorLabel = new JLabel("Choice your name:");
		panel.add(authorLabel, "wrap");
		JButton addAuthor = createAddAuthorButton();
		panel.add(authors);
		panel.add(addAuthor);
		createMargin();

		panel.add(new JLabel("date:"), "wrap");
		chooser = new JDateChooser();
		chooser.setDate(new java.util.Date());
		panel.add(chooser);
		createMargin();
		
		panel.add(new JLabel("note:"), "wrap");
		note = new JTextArea(10,18);
		note.setFont(note.getFont().deriveFont(14f));
		JScrollPane scrollForNote = new JScrollPane(note);
		panel.add(scrollForNote);
		createMargin();
		
		canRewrie = new JCheckBox();
		canRewrie.setSelected(true);
		panel.add(new JLabel("<html>Whether this task<br>could be rewrite?"), "wrap");
		panel.add(canRewrie);
		createMargin();
	}

	public void createAuthorsList() {
		authors = new JComboBox<>(manager.getAuthorsNames());
	}

	private JButton createAddAuthorButton() {
		JButton addAuthor = new JButton("add");
		addAuthor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.setLayout(null);
				AddAuthorFrame iframe = new AddAuthorFrame(
						desktop, manager, SaveView.this);
				desktop.addIFrame(iframe);
			}
		});
		return addAuthor;
	}
	
	private void createMargin() {
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
	}

	public JComboBox<String> getAuthors() {
		return authors;
	}

}
