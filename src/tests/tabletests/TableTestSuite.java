package tests.tabletests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NotSolutionEconomicManagerTests.class,
		NotSolutionNotEconomicManagerTests.class, KnapsakTableModelTests.class,
		SolvedEconomManagerTests.class,SolvedNotEconomManager.class })
public class TableTestSuite {
}
