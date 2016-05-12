package viewmodel.componentsmodels;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class TooBigNumberException extends RuntimeException {

	public TooBigNumberException() {
	}

	public TooBigNumberException(String msg) {
		super(msg);
	}

}
