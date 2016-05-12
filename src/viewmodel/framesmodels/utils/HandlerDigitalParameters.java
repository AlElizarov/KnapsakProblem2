package viewmodel.framesmodels.utils;

import handle.AbstractHandler;

import java.awt.Color;

public class HandlerDigitalParameters extends AbstractHandler {

	@Override
	protected void handle() {
		if (text.length() <= 0) {
			afterHandle(Color.RED, "please, enter count of variables", false);
		} else if (text.matches(".*\\D+")) {
			afterHandle(Color.RED, "please, enter only digits", false);
		} else {
			try {
				double value = Double.parseDouble(text);
				if (value <= 0 || value - (int) value > 0) {
					afterHandle(Color.RED,
							"please, enter only positive integers", false);
				} else {
					afterHandle(Color.BLACK, "", true);
				}
			} catch (NumberFormatException exc) {
				afterHandle(Color.RED, "please, enter only digits", false);
			}
		}
	}

}
