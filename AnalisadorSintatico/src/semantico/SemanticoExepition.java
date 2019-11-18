package semantico;

public class SemanticoExepition extends Exception {
	private String msg = "";

	public SemanticoExepition(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return this.msg;
	}
}
