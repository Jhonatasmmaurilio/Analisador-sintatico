package maquinahipotetica;

/**
 * Classe utilizada pela classe "Hipotetica" para armazenar a área de
 * instruções. Esta classe, bem como as classes "Tipos", "AreaLiterais" e
 * "Hipotetica" foi criada por Maicon, Reinaldo e Fabio e adaptada para este
 * aplicativo.
 */
public class AreaInstrucoes {
	public Instrucao AI[] = new Instrucao[1000];
	public int LC;

	/**
	 * Construtor sem parâmetros. Todos os atributos são inicializados com valores
	 * padrões.
	 */
	public void AreaInstrucoes() {
		for (int i = 0; i < 1000; i++) {
			AI[i] = new Instrucao(i, i, i);
		}
	}
}
