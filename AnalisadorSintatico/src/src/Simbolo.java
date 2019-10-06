package tabelaSimbolo;

public class Simbolo {
	private String nome;
	private String categoria;
	private int nivel;
	private String geralA, geralB;

	public void insere(String nome, String categoria, int nivel, String geralA, String geralB) {
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
	
	
}
