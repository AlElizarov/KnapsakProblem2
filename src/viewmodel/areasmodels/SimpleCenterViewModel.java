package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class SimpleCenterViewModel {

	private boolean isSplitForTableAndSolutionOneTouchExpandeble = true;
	private boolean isSplitForTableAndSolutionContinouslyLayout = true;
	private TaskManager manager;

	public SimpleCenterViewModel(TaskManager manager) {
		this.manager = manager;
	}

	public boolean isSplitForTableAndSolutionOneTouchExpandable() {
		return isSplitForTableAndSolutionOneTouchExpandeble;
	}

	public boolean isSplitForTableAndSolutionContinuoslyLayout() {
		return isSplitForTableAndSolutionContinouslyLayout;
	}

	public int getSplitForTableAndSolutionDivivderSize() {
		if (manager.isTaskSolved()) {
			return 5;
		}
		return 0;
	}

	public boolean isPanelForTableVisible() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

	public boolean isPanelForSolutionVisible() {
		if (manager.isTaskSolved() && !manager.isRead()) {
			return true;
		}
		return false;
	}

	public double getResizeWeight() {
		if (manager.isTaskSolved() && !manager.isRead()) {
			return 0.7;
		}
		return 0;
	}

	public boolean isSvertkaVisible() {
		if (manager.isTaskSolved() && manager.getCriterionCount() > 1 && !manager.isRead()) {
			return true;
		}
		return false;
	}

	public String setSvertkaText() {
		if (isSvertkaVisible())
			return "<html><pre>   U = " + manager.getSvertka()+"</pre></html>";
		return "";
	}

	public String getSolutionText() {
		if (manager.isTaskSolved() && !manager.isRead()) {
			return "    H = " + manager.getH() + "<br>" + "   V = " + manager.getV()
					+"<br>iteration: "+manager.getIteraton()+"<br>vertex deleted: "+manager.vertexDeleted();
		}
		return "";
	}

}
