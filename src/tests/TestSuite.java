package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.tabletests.TableTestSuite;
import tests.tasktests.TaskTests;
import tests.utilstests.UtilsTestSuite;
import tests.viewmodeltests.ViewModelTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ViewModelTestSuite.class, UtilsTestSuite.class,
		TaskTests.class, DesktopTests.class, TableTestSuite.class })
public class TestSuite {
}