package view.components;

import java.util.List;

import javax.swing.SwingWorker;

import viewmodel.TaskManager;

public class KnapsakGuiSolver<T, V> extends SwingWorker<T, V> {

	private TaskManager manager;
	private boolean run = true;

	public KnapsakGuiSolver(TaskManager manager) {
		this.manager = manager;
	}

	@Override
	protected T doInBackground() throws Exception {
		int step = 0;
		run = true;
		while (!manager.isEnd() && run) {
			manager.solveTask();
			if (step++ % 5000 == 0)
				publish();
		}
		return null;
	}

	@Override
	protected void process(List<V> chunks) {
		manager.setTaskSolved();
	}

	@Override
	protected void done() {
		run = false;
		manager.setTaskSolved();
	}

}
