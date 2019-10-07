package src;

public class ListaEncadeada {
	private Simbolo primeiro;
	private Simbolo ultimo;
	private int qtd = 0;

	public Simbolo getPrimeiro() {
		return primeiro;
	}

	public void setPrimeiro(Simbolo primeiro) {
		this.primeiro = primeiro;
	}

	public Simbolo getUltimo() {
		return ultimo;
	}

	public void setUltimo(Simbolo ultimo) {
		this.ultimo = ultimo;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd() {
		this.qtd = qtd + 1;
	}
	
	public void diminuiQtd() {
		this.qtd = qtd - 1;
	}

	@Override
	public String toString() {
		return "ListaEncadeada [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
