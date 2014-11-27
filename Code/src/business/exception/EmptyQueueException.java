package business.exception;

public class EmptyQueueException extends Exception {

	public EmptyQueueException(String msg){
		super(msg);
	}
	public EmptyQueueException(){
		super();
	}
}
