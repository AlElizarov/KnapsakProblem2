package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import view.frames.AboutProgrammView;
import view.frames.Desktop;
import view.frames.NewTaskCreatingView;
import viewmodel.TaskManager;

public class DesktopTests {

	private Desktop desktop;
	private TaskManager sharedViewModel;
	private NewTaskCreatingView firstNewView;
	private NewTaskCreatingView secondNewView;

	@Before
	public void setUp() {
		sharedViewModel = TaskManager.getInstance();
		desktop = new Desktop();
		firstNewView = new NewTaskCreatingView(desktop, sharedViewModel);
		secondNewView = new NewTaskCreatingView(desktop, sharedViewModel);
	}

	@Test
	public void byDefaultComponentCountIs0() {
		int expectComponentCount = 0;

		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

	@Test
	public void whenNewTaskInternalFrameAddComponentCountIs1() {
		int expectComponentCount = 1;

		desktop.addIFrame(firstNewView);
		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

	@Test
	public void whenNewTaskInternalFrameAddAgainComponentCountIs1() {
		int expectComponentCount = 1;

		desktop.addIFrame(firstNewView);
		desktop.addIFrame(secondNewView);
		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

	@Test
	public void whenInternalFrameDisposedComponentCountIs0() {
		int expectComponentCount = 0;

		desktop.addIFrame(firstNewView);
		firstNewView.dispose();
		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

	@Test
	public void whenNewTaskInternalFrameAddAfterDisposeComponentCountIs1() {
		int expectComponentCount = 1;

		desktop.addIFrame(firstNewView);
		firstNewView.dispose();
		desktop.addIFrame(secondNewView);
		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

	@Test
	public void whenNewTaskAndAboutProgrammInternalFramesAddComponentCountIs2() {
		int expectComponentCount = 2;

		desktop.addIFrame(firstNewView);
		desktop.addIFrame(new AboutProgrammView(desktop));
		int actualComponentCount = desktop.getComponentCount();

		assertEquals(expectComponentCount, actualComponentCount);
	}

}
