package tests.viewmodeltests.areasmodelstests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.areasmodels.MainViewModel;

public class MainViewModelTests {

	private MainViewModel mainViewModel;
	private final double epsilan = 1e-10;

	@Before
	public void setUp() {
		mainViewModel = new MainViewModel();
	}

	@Test
	public void byDefaultMainSplitResizeWeightEqualsTo5Percent() {
		double expectResizeWeight = 0.05;

		double actualResizeWeight = mainViewModel.getMainSplitResizeWeight();

		assertEquals(expectResizeWeight, actualResizeWeight, epsilan);
	}

	@Test
	public void byDefaultMainSplitContinuoslyLayout() {
		assertTrue(mainViewModel.isMainSplitContinuoslyLayout());
	}

	@Test
	public void byDefaultMainSplitOneTouchExpandable() {
		assertTrue(mainViewModel.isMainSplitOneTouchExpandable());
	}

}