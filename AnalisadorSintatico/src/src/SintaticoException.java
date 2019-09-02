package src;
public class SintaticoException extends Exception {
	private String msg = "";
	private int posicao;

	public SintaticoException(String msg, int pos) {
		this.msg = msg;
		this.posicao = pos;
	}
	
	@Override
	public String toString() {
		return this.msg;
	}
}
