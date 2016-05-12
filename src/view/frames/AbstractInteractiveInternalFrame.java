package view.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import viewmodel.TaskManager;
import net.miginfocom.swing.MigLayout;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public abstract class AbstractInteractiveInternalFrame extends
		AbstractDecoratedInternalFrame {

	protected JButton ok = new JButton("OK");
	protected JButton cancel = new JButton("cancel");

	public AbstractInteractiveInternalFrame(String title, Desktop desktop,
			int width, int height, int x, int y, TaskManager sharedViewModel) {
		super(title, desktop, width, height, x, y, sharedViewModel);
		createOkAndCancelButtons();
	}

	private void createOkAndCancelButtons() {
		JPanel panelForOkAndCancel = new JPanel(new MigLayout());
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createOkAction();
			}

		});
		JButton cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractInteractiveInternalFrame.this.dispose();
			}
		});
		panelForOkAndCancel.add(ok, "sg 1");
		panelForOkAndCancel.add(cancel, "sg 1");
		panel.add(panelForOkAndCancel);
	}

	protected abstract void createOkAction();

	public abstract void openingBind();

}
