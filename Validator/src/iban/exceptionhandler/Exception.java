package iban.exceptionhandler;

public class Exception extends RuntimeException{
	
	//private static final long serialVersionUID = 1L;
	private String msg;
	
	public Exception(String msg){
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
