package src;
public class SintaticoException extends Exception {
	private String msg = "";

	public SintaticoException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return this.msg;
	}
}
