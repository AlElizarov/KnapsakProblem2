package viewmodel.framesmodels.utils;

import handle.AbstractHandler;

import java.awt.Color;

public class HandlerStringParameters extends AbstractHandler{

	@Override
	protected void handle() {
		if(text == null){
			fieldColor = Color.BLACK;
			errorMsg = "";
			return;
		}
		if (text.length() <= 0) {
			errorMsg = "please, enter data";
			fieldColor = Color.RED;
			isGood = false;
		} else if (!text.matches("(\\w+|\\s+)")) {
			errorMsg = "please, enter only 0-9 or a-Z or _";
			fieldColor = Color.RED;
			isGood = false;
		} else if (text.matches("^\\s+$")) {
			errorMsg = "please, don't enter only spaces";
			fieldColor = Color.RED;
			isGood = false;
		} else {
			fieldColor = Color.BLACK;
			errorMsg = "";
			isGood = true;
		}
	}

}
