package maquinahipotetica;

/**
 * Classe utilizada pela classe "Hipotetica" para armazenar a √°rea de
 * instru√ß√µes. Esta classe, bem como as classes "Tipos", "AreaLiterais" e
 * "Hipotetica" foi criada por Maicon, Reinaldo e Fabio e adaptada para este
 * aplicativo.
 */
public class AreaInstrucoes {
	public Tipos AI[]= new Tipos[1000];
	public int LC;
	
    /**
   * Construtor sem par‚metros.
   * Todos os atributos s„o inicializados com valores padrıes.
   */
	public AreaInstrucoes(){
		for(int i=0; i<1000; i++){
			AI[i]=new Tipos();
		}
	}
}
