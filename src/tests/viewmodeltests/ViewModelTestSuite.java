package tests.viewmodeltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.viewmodeltests.areasmodelstests.AreasModelsTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ AreasModelsTestSuite.class, TaskManagerTests.class,
		NewTaskCreatingViewModelTests.class, GenerationViewModelTests.class })
public class ViewModelTestSuite {
}
