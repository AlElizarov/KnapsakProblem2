package view.frames;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class Desktop extends JDesktopPane {

	private List<Class<?>> listOfAddingInternalFrames = new ArrayList<>();

	@Override
	public void moveToFront(Component component) {
		if (component instanceof MainInternalFrameView) {
			super.moveToBack(component);
		} else {
			super.moveToFront(component);
		}
	}

	public void addIFrame(JInternalFrame iframe) {
		if (!listOfAddingInternalFrames.contains(iframe.getClass())) {
			add(iframe);
			try {
				iframe.setSelected(true);
			} catch (PropertyVetoException exc) {
				exc.printStackTrace();
			}
			listOfAddingInternalFrames.add(iframe.getClass());
		}
	}

	public void deleteIFrame(JInternalFrame iframe) {
		listOfAddingInternalFrames.remove(iframe.getClass());
	}

}
