package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

//I'm not going to serialize
@SuppressWarnings("serial")
public class MySpinner extends JSpinner {
	
	public MySpinner(Object[] items) {
		setModel(new SpinnerCircularListModel(items));
		setPreferredSize(new Dimension(65, (int)getPreferredSize().getHeight()));
		JTextField tf = ((JSpinner.DefaultEditor) getEditor()).getTextField();
		tf.setForeground(Color.YELLOW); 
		tf.setBackground(new Color(100,149,237));
		tf.setEditable(false);
	}

	class SpinnerCircularListModel extends SpinnerListModel {
		public SpinnerCircularListModel(Object[] items) {
			super(items);
		}

		public Object getNextValue() {
			//не сумел изменить
			@SuppressWarnings("rawtypes")
			List list = getList();
			int index = list.indexOf(getValue());

			index = (index >= list.size() - 1) ? 0 : index + 1;
			return list.get(index);
		}

		public Object getPreviousValue() {
			//не сумел изменить
			@SuppressWarnings("rawtypes")
			List list = getList();
			int index = list.indexOf(getValue());

			index = (index <= 0) ? list.size() - 1 : index - 1;
			return list.get(index);
		}
	}

}
