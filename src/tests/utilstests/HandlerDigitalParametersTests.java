package tests.utilstests;

import static org.junit.Assert.assertEquals;
import handle.AbstractHandler;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import viewmodel.framesmodels.utils.HandlerDigitalParameters;

public class HandlerDigitalParametersTests {

	private AbstractHandler handler;

	@Before
	public void setUp() {
		handler = new HandlerDigitalParameters();
	}

	@Test
	public void byDefaultFieldIsBlack() {
		Color expectedColor = Color.BLACK;

		Color actualColor = handler.getFieldColor();

		assertEquals(expectedColor, actualColor);
	}

	@Test
	public void byDefaultErrorMsgEmpty() {
		String expectErrorMsg = null;

		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenInputUncorrectCharacterFieldIsRed() {
		Color expectColor = Color.RED;

		handler.setText("a");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void whenInputEmptyStringFieldIsRed() {
		Color expectColor = Color.RED;

		handler.setText("");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void whenInputUncorrectCharacterErrorMsgNotEmpty() {
		String expectErrorMsg = "please, enter only digits";

		handler.setText("1a");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenInputEmptyStringErrorMsgNotEmpty() {
		String expectErrorMsg = "please, enter count of variables";

		handler.setText("");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenClearIncorrectInputFieldIsBlack() {
		Color expectColor = Color.BLACK;

		handler.setText("aa");
		handler.setText("11");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void whenClearIncorrectInputErrorMsgEmpty() {
		String expectErrorMsg = "";

		handler.setText("aa");
		handler.setText("11");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}
	
	@Test
	public void whenInputNegativeValueErrorMsgNotEmpty() {
		String expectErrorMsg = "please, enter only positive integers";

		handler.setText("-1");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}
	
	@Test
	public void whenInputNegativeValueFieldIsRed() {
		Color expectColor = Color.RED;

		handler.setText("-1");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectColor, actualColor);
	}

}
