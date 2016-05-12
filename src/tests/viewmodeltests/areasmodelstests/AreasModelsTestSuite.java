package tests.viewmodeltests.areasmodelstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CenterViewModelTests.class, InfoPanelViewModelTests.class,
		MainViewModelTests.class, MenuBarViewModelTests.class,
		ToolBarViewModelTests.class })
public class AreasModelsTestSuite {
}
