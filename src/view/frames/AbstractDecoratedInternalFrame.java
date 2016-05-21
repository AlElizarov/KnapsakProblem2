package view.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import net.miginfocom.swing.MigLayout;
import viewmodel.TaskManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public abstract class AbstractDecoratedInternalFrame extends JInternalFrame {

	protected Desktop desktop;
	protected JPanel panel = new JPanel(new MigLayout());
	protected TaskManager manager;

	public AbstractDecoratedInternalFrame(String title, Desktop desktop,
			int width, int height, int x, int y, TaskManager manager) {
		super(title, true, true, false, false);
		this.manager = manager;
		createInternalFrame(desktop, width, height, x, y);
	}

	public AbstractDecoratedInternalFrame(String title, Desktop desktop,
			int width, int height, int x, int y) {
		super(title, true, true, false, false);
		createInternalFrame(desktop, width, height, x, y);
	}

	public void createInternalFrame(Desktop desktop, int width, int height,
			int x, int y) {
		this.desktop = desktop;
		fullPanel();
		add(panel);
		createNorthPanelProperties();
		createNorthPanel();
		setMinimumSize(new Dimension(width, height));
		setBounds(x, y, width, height);
		setVisible(true);
	}

	private void createNorthPanel() {
		JComponent title = ((BasicInternalFrameUI) this.getUI()).getNorthPane();
		for (int i = 0; i < title.getComponentCount(); i++) {
			JComponent component = (JComponent) title.getComponent(i);
			if (component instanceof JButton) {
				JButton button = ((JButton) component);
				createButtonProperties(button);
			}
		}
	}

	private void createNorthPanelProperties() {
		BasicInternalFrameUI ui = new BasicInternalFrameUI(this);
		setUI(ui);
		BasicInternalFrameUI basicFrameUI = (BasicInternalFrameUI) getUI();
		basicFrameUI.getNorthPane().setPreferredSize(new Dimension(400, 28));
		basicFrameUI.getNorthPane().setBorder(new LineBorder(Color.GRAY, 1));
		basicFrameUI.getNorthPane().remove(0);
		basicFrameUI.getNorthPane().remove(1);
		basicFrameUI.getNorthPane().revalidate();
		basicFrameUI.getNorthPane().repaint();
	}

	private void createButtonProperties(JButton button) {
		createButtonListener(button);
		button.setToolTipText("Close");
		button.setMinimumSize(new Dimension(24, 24));
		button.setIcon(new ImageIcon("images/closeEnd.png"));
	}

	private void createButtonListener(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setIcon(new ImageIcon("images/closered.png"));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setIcon(new ImageIcon("images/closeEnd.png"));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
			}

		});
	}

	@Override
	public void dispose() {
		super.dispose();
		desktop.deleteIFrame(this);
	}

	protected abstract void fullPanel();

}
