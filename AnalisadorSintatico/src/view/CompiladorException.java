package view;

public class CompiladorException {
	private String msg = "";

	public CompiladorException(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return this.msg;
	}
}
