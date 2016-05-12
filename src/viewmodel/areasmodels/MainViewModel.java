package viewmodel.areasmodels;

public class MainViewModel {

	private boolean isMainSplitContinouslyLayout = true;
	private boolean isMainSplitOneTouchExpandeble = true;
	private double mainSplitResizeWeight = 0.05;

	public boolean isMainSplitContinuoslyLayout() {
		return isMainSplitContinouslyLayout;
	}

	public boolean isMainSplitOneTouchExpandable() {
		return isMainSplitOneTouchExpandeble;
	}

	public double getMainSplitResizeWeight() {
		return mainSplitResizeWeight;
	}

}
