package tests.algorithm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		OneDirectTests.class,
		BiDirectSolverTests.class })
public class AlgorithmTestSuite {
}
