package view.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import viewmodel.TaskManager;

import com.toedter.calendar.JDateChooser;

//I'm not going to serialize this class
@SuppressWarnings("serial")
public class ReadView extends AbstractInteractiveInternalFrame {

	private JComboBox<String> tasksList;
	private JComboBox<String> authors;
	private JCheckBox isAuthorFilter;
	private JCheckBox isDateFilter;
	private JDateChooser startChooser;
	private JDateChooser finishChooser;

	public ReadView(Desktop desktop, TaskManager sharedViewModel) {
		super("Read", desktop, 400, 500, 300, 150, sharedViewModel);
		if (tasksList.getItemCount() == 0) {
			ok.setEnabled(false);
		}
	}

	@Override
	protected void createOkAction() {
		try {
			manager.read((String) tasksList.getSelectedItem());
			ReadView.this.dispose();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Problems with database connection");
		}
	}

	@Override
	public void openingBind() {
	}

	@Override
	protected void fullPanel() {
		addTaskChoocer();

		addAuthorFilter();

		addDateListener();
	}

	private void addDateListener() {
		isDateFilter = new JCheckBox("Date filter: ");
		isDateFilter.setSelected(false);
		isDateFilter.addActionListener(new DateFilterListener());

		JPanel datePanel = new JPanel();
		startChooser = new JDateChooser();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		startChooser.setDate(cal.getTime());

		startChooser.getDateEditor().addPropertyChangeListener(
				new DatePickerListener());
		datePanel.add(new JLabel("from: "));
		datePanel.add(startChooser);

		finishChooser = new JDateChooser();
		finishChooser.setDate(new Date());
		finishChooser.getDateEditor().addPropertyChangeListener(
				new DatePickerListener());
		datePanel.add(new JLabel("to: "));
		datePanel.add(finishChooser);
		panel.add(isDateFilter, "wrap");
		panel.add(datePanel, "wrap");

		startChooser.setEnabled(false);
		finishChooser.setEnabled(false);
	}

	protected void createTasksList(Date startDate, Date finishDate) {
		java.sql.Date sd = new java.sql.Date(startDate.getTime());
		java.sql.Date fd = new java.sql.Date(finishDate.getTime());
		tasksList
				.setModel(new DefaultComboBoxModel<>(manager.readTasks(sd, fd)));
	}

	private void addAuthorFilter() {
		isAuthorFilter = new JCheckBox("Author filter: ");
		isAuthorFilter.setSelected(false);
		isAuthorFilter.addActionListener(new AuthorFilterListener());
		panel.add(isAuthorFilter, "wrap");
		createAuthorsList();
		JLabel authorLabel = new JLabel("Choice your name:");
		panel.add(authorLabel, "wrap");
		panel.add(authors);
		authors.setEnabled(false);
		authors.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (!isDateFilter.isSelected()) {
						createTasksList((String) authors.getSelectedItem());
					} else {
						createTasksList((String) authors.getSelectedItem(),
								startChooser.getDate(), finishChooser.getDate());
					}
					checkOkButton();
				}
			}
		});
		createMargin();
	}

	protected void createTasksList(String author, Date startDate,
			Date finishDate) {
		java.sql.Date sd = new java.sql.Date(startDate.getTime());
		java.sql.Date fd = new java.sql.Date(finishDate.getTime());
		tasksList.setModel(new DefaultComboBoxModel<>(manager.readTasks(author,
				sd, fd)));
	}

	private void addTaskChoocer() {
		tasksList = new JComboBox<>();
		createTasksList();
		panel.add(new JLabel("Choice task:"), "wrap");
		panel.add(tasksList);
		createMargin();
	}

	private void createTasksList(String author) {
		tasksList
				.setModel(new DefaultComboBoxModel<>(manager.readTasks(author)));
	}

	private void createAuthorsList() {
		authors = new JComboBox<>(manager.getAuthorsNames());
	}

	private void createTasksList() {
		tasksList.setModel(new DefaultComboBoxModel<>(manager.readTasks()));
	}

	private void createMargin() {
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
	}

	private class DateFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isDateFilter.isSelected() && isAuthorFilter.isSelected()) {
				startChooser.setEnabled(true);
				finishChooser.setEnabled(true);
				createTasksList((String) authors.getSelectedItem(),
						startChooser.getDate(), finishChooser.getDate());
			} else if (isDateFilter.isSelected()
					&& !isAuthorFilter.isSelected()) {
				startChooser.setEnabled(true);
				finishChooser.setEnabled(true);
				createTasksList(startChooser.getDate(), finishChooser.getDate());
			} else {
				startChooser.setEnabled(false);
				finishChooser.setEnabled(false);
				if (isAuthorFilter.isSelected()) {
					createTasksList((String) authors.getSelectedItem());
				} else {
					createTasksList();
				}
			}
			checkOkButton();
		}

	}

	private class DatePickerListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if ("date".equals(e.getPropertyName())) {
				if (finishChooser.getDate().compareTo(startChooser.getDate()) <= 0) {
					JOptionPane.showMessageDialog(null,
							"start date must be less than finish date");
					Calendar cal = Calendar.getInstance();
					cal.setTime(finishChooser.getDate());

					Calendar cal2 = Calendar.getInstance();
					cal2.set(Calendar.YEAR, cal.get(Calendar.YEAR));
					cal2.set(Calendar.MONTH, cal.get(Calendar.MONTH));
					cal2.set(Calendar.DAY_OF_MONTH,
							cal.get(Calendar.DAY_OF_MONTH) - 1);
					Date da = new Date(cal2.getTimeInMillis());
					startChooser.setDate(da);
				} else {
					if (isDateFilter.isSelected()
							&& isAuthorFilter.isSelected()) {
						createTasksList((String) authors.getSelectedItem(),
								startChooser.getDate(), finishChooser.getDate());
					}
					if (isDateFilter.isSelected()
							&& !isAuthorFilter.isSelected()) {
						createTasksList(startChooser.getDate(),
								finishChooser.getDate());
					}
					checkOkButton();
				}
			}

		}

	}

	private class AuthorFilterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isAuthorFilter.isSelected() && !isDateFilter.isSelected()) {
				authors.setEnabled(true);
				createTasksList((String) authors.getSelectedItem());
			} else if (isAuthorFilter.isSelected() && isDateFilter.isSelected()) {
				authors.setEnabled(true);
				createTasksList((String) authors.getSelectedItem(),
						startChooser.getDate(), finishChooser.getDate());
			} else {
				authors.setEnabled(false);
				if (isDateFilter.isSelected()) {
					createTasksList(startChooser.getDate(),
							finishChooser.getDate());
				} else {
					createTasksList();
				}
			}
			checkOkButton();
		}

	}

	private void checkOkButton() {
		if (tasksList.getItemCount() == 0) {
			ok.setEnabled(false);
		} else {
			ok.setEnabled(true);
		}
	}

}
