package tests.viewmodeltests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;
import viewmodel.framesmodels.GenerationViewModel;

public class GenerationViewModelTests {

	private GenerationViewModel generationModel;
	private TaskManager taskManager;
	private double epsilan = 1e-10;

	@Before
	public void setUp() {
		taskManager = TaskManager.getInstance();
		generationModel = new GenerationViewModel(taskManager);
		taskManager.setStartState();
	}

	@Test
	public void byDefaultAllButtonEnable() {
		assertTrue(generationModel.isAllButtonEnable());
	}

	@Test
	public void byDefaultRandomlyButtonEnable() {
		assertTrue(generationModel.isRandomlyButtonEnable());
	}

	@Test
	public void byDefaultGenCoeff50Persent() {
		double expectCeoff = 0.5;

		double actualCoeff = generationModel.getPartOfUpperBoundForLimits();
		
		assertEquals(expectCeoff, actualCoeff, epsilan );
	}
	
	@Test
	public void typeConditionButtonAndInputCoeff() {
		double expectCeoff = 0.7;

		generationModel.setRandomlyButtonEnable(false);
		generationModel.setPartOfUpperBoundForLimits(0.7);
		double actualCoeff = generationModel.getPartOfUpperBoundForLimits();
		
		assertEquals(expectCeoff, actualCoeff, epsilan );
	}
	
	@Test
	public void dontTypeConditionButtonAndInputCoeff() {
		double expectCeoff = 0.5;

		generationModel.setPartOfUpperBoundForLimits(0.7);
		double actualCoeff = generationModel.getPartOfUpperBoundForLimits();
		
		assertEquals(expectCeoff, actualCoeff, epsilan );
	}

}
