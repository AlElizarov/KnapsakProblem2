package view.areas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import view.components.FrameClosingListener;
import view.frames.Desktop;
import view.frames.MainInternalFrameView;
import viewmodel.TaskManager;
import viewmodel.areasmodels.MainViewModel;

public class MainView implements Observer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
				}
				new MainView();
			}
		});
	}

	private JFrame frame = new JFrame("Knapsak Problem");
	private JSplitPane mainSplit = new JSplitPane();

	private Desktop desktop = new Desktop();
	private MainViewModel mainViewModel;
	private TaskManager manager;
	private List<BindableArea> leafs = new ArrayList<>();

	public MainView() {
		mainViewModel = new MainViewModel();
		manager = TaskManager.getInstance();
		manager.addObserver(this);
		createAndShowGui();
		createFrameProperties();
		createUiProperties();
	}

	private void createAndShowGui() {
		createMenu();
		ToolBarView toolBar = createToolBar();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(desktop, BorderLayout.CENTER);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		addMainInternalFrame();
		createMainSplit();

		bind();
	}

	private ToolBarView createToolBar() {
		ToolBarView toolBar = new ToolBarView(desktop, manager);
		leafs.add(toolBar);
		return toolBar;
	}

	private void createMenu() {
		MenuBarView menu = new MenuBarView(desktop, manager);
		leafs.add(menu);
		frame.setJMenuBar(menu);
	}

	private void createMainSplit() {
		InfoPanelView info = new InfoPanelView(manager);
		leafs.add(info);
		JScrollPane leftScroll = new JScrollPane(info);
		mainSplit.setLeftComponent(leftScroll);

		Center center = new Center(manager);
		leafs.add(center);
		mainSplit.setRightComponent(center.getCenterView());
	}

	private void addMainInternalFrame() {
		MainInternalFrameView mainInternalFrame = new MainInternalFrameView();
		mainInternalFrame.add(mainSplit);

		desktop.setLayout(new BorderLayout());
		desktop.add(mainInternalFrame);
		desktop.setBackground(Color.WHITE);
	}

	private void createFrameProperties() {
		frame.setIconImage(frame.getToolkit().getImage(
				"images/icon_prog_48.png"));
		
		frame.addWindowListener(new FrameClosingListener());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(1000, 700));
	}

	private void bind() {
		mainSplitBind();
		for (BindableArea leaf : leafs) {
			leaf.bind();
		}
	}

	private void mainSplitBind() {
		mainSplit.setContinuousLayout(mainViewModel
				.isMainSplitContinuoslyLayout());
		mainSplit.setOneTouchExpandable(mainViewModel
				.isMainSplitOneTouchExpandable());
		mainSplit.setResizeWeight(mainViewModel.getMainSplitResizeWeight());
	}

	private void createUiProperties() {
		UIManager.put("InternalFrame.border", new LineBorder(Color.GRAY, 1));
		UIManager.put("InternalFrame.activeTitleBackground", Color.white);
	}

	@Override
	public void update(Observable subject, Object arg) {
		bind();
	}

}
