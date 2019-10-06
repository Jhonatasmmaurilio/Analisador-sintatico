package src;

public class Simbolo {
	private String nome;
	private String categoria;
	private int nivel;
	private String geralA, geralB;
	private Simbolo proximo = null;
	private int qtd = 0;

	public Simbolo getProximo() {
		return proximo;
	}

	public void setProximo(Simbolo proximo) {
		this.proximo = proximo;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public Simbolo(String nome, String categoria, int nivel, String geralA, String geralB) {
		this.nome = nome;
		this.categoria = categoria;
		this.nivel = nivel;
		this.geralA = geralA;
		this.geralB = geralB;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getGeralA() {
		return geralA;
	}

	public void setGeralA(String geralA) {
		this.geralA = geralA;
	}

	public String getGeralB() {
		return geralB;
	}

	public void setGeralB(String geralB) {
		this.geralB = geralB;
	}

	@Override
	public String toString() {
		return "Simbolo: " + this.nome;
	}

}
