package semantico;

import java.util.Stack;

import maquinahipotetica.MaquinaHipotetica;
import token.TabelaSimbolos;

public class Semantica {
	private TabelaSimbolos TS = new TabelaSimbolos();
	private static MaquinaHipotetica MH = new MaquinaHipotetica();
	
	private Stack pilhaIf = new Stack();
	private Stack pilhaWhile = new Stack();
	private Stack pilhaRepeat = new Stack();
	private Stack pilhProcedure = new Stack();
	private Stack pilhaCase = new Stack();
	private Stack pilhaFor = new Stack();

	private String tipoIdentificador;
	private String nIdentificador;
	private String contexto;
	
	private int endereco = 0;
	private int index =0;
	private int nivelAtual;
	private int totalVariavel;
	private int deslocamento;
	private int n;
	private int nivel;
	private int totalParametro;
	private int enderecoA;
	
	
}
