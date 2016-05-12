package tests.utilstests;

import static org.junit.Assert.assertEquals;
import handle.AbstractHandler;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import viewmodel.framesmodels.utils.HandlerStringParameters;

public class HandlerStringParametersTests {

	private AbstractHandler handler;

	@Before
	public void setUp() {
		handler = new HandlerStringParameters();
	}

	@Test
	public void byDefaultFieldIsBlack() {
		Color expectedColor = Color.BLACK;

		Color actualColor = handler.getFieldColor();

		assertEquals(expectedColor, actualColor);
	}

	@Test
	public void whenInputCorrectFieldIsBlack() {
		Color expectedColor = Color.BLACK;

		handler.setText("aa");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectedColor, actualColor);
	}

	@Test
	public void whenInputIncorrectCharacterFieldIsRed() {
		Color expectedColor = Color.RED;

		handler.setText("aa;");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectedColor, actualColor);
	}

	@Test
	public void whenInputOnlySpacesFieldIsRed() {
		Color expectedColor = Color.RED;

		handler.setText("   ");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectedColor, actualColor);
	}

	@Test
	public void byDefaultErrorMsgIsEmpty() {
		String expectErrorMsg = null;

		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenInputIncorrectCharacterErrorMsgIsNotEmpty() {
		String expectErrorMsg = "please, enter only 0-9 or a-Z or _";

		handler.setText("aa;");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenInputOnlySpacesErrorMsgIsNotEmpty() {
		String expectErrorMsg = "please, don't enter only spaces";

		handler.setText("  ");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenInputEmptyStringErrorMsgIsNotEmpty() {
		String expectErrorMsg = "please, enter data";

		handler.setText("");
		String actualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, actualErrorMsg);
	}

	@Test
	public void whenClearIncorrectInputfromFieldColorBlack() {
		Color expectColor = Color.BLACK;

		handler.setText("  ");
		handler.setText("aa");
		Color actualColor = handler.getFieldColor();

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void whenClearIncorrectInputfromFieldErrorMsgEmpty() {
		String expectErrorMsg = "";

		handler.setText("  ");
		handler.setText("aa");
		String ctualErrorMsg = handler.getErrorMsg();

		assertEquals(expectErrorMsg, ctualErrorMsg);
	}

}
