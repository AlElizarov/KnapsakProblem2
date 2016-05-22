package view.frames;

import handle.FieldAndLabelArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import viewmodel.TaskManager;
import viewmodel.framesmodels.utils.HandlerStringParameters;

//I'm not going to serialize this class
@SuppressWarnings("serial")
public class AddAuthorFrame extends AbstractInteractiveInternalFrame {

	private FieldAndLabelArea nameArea;
	private FieldAndLabelArea surnameArea;
	private FieldAndLabelArea futhernameArea;
	private SaveView saveView;

	public AddAuthorFrame(Desktop desktop, TaskManager manager,
			SaveView saveView) {
		super("Add author", desktop, 300, 300, 200, 250, manager);
		this.saveView = saveView;
	}

	@Override
	protected void createOkAction() {
		try {
			manager.addAuthor(nameArea.getText(), surnameArea.getText(),
					futhernameArea.getText());
			if (saveView != null) {
				saveView.getAuthors().setModel(
						new DefaultComboBoxModel<>(manager.getAuthorsNames()));
				saveView.afterAddAuthor();
			}
		} catch (MySQLIntegrityConstraintViolationException exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"User already exists, please enter new data");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Problems with database connection");
		}
		AddAuthorFrame.this.dispose();
	}

	@Override
	public void openingBind() {
		ok.setEnabled(false);
	}

	@Override
	protected void fullPanel() {
		nameArea = new FieldAndLabelArea(new HandlerStringParameters(), "name:");
		panel.add(nameArea, "wrap");
		nameArea.getField().addKeyListener(new MyKeyListener(nameArea));

		surnameArea = new FieldAndLabelArea(new HandlerStringParameters(),
				"surname:");
		panel.add(surnameArea, "wrap");
		surnameArea.getField().addKeyListener(new MyKeyListener(surnameArea));

		futhernameArea = new FieldAndLabelArea(new HandlerStringParameters(),
				"futhername:");
		panel.add(futhernameArea, "wrap");
		futhernameArea.getField().addKeyListener(
				new MyKeyListener(futhernameArea));
	}

	private class MyKeyListener extends KeyAdapter {

		private FieldAndLabelArea area;

		public MyKeyListener(FieldAndLabelArea area) {
			this.area = area;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			area.backBind();
			area.bind();
			bindOkButton();
		}

	}

	public void bindOkButton() {
		if (nameArea.getHandler().isGood()
				&& surnameArea.getHandler().isGood()
				&& futhernameArea.getHandler().isGood()
				|| (nameArea.getHandler().isGood()
						&& surnameArea.getHandler().isGood() && futhernameArea
						.getText().equals(""))) {
			ok.setEnabled(true);
		} else {
			ok.setEnabled(false);
		}
	}

}
