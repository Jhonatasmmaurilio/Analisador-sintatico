package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javafx.scene.control.Label;

public class Sintatico {
	private Lexico listaTokens;
	private Stack pilha = new Stack();
	private boolean compilado = false;
	private Token tokenAtual;
	private Token tokenAnterior;
	private Label lt;
	private Label ll;
	private Label le;
	private String listaT = "";
	private String listaL = "";
	private String listaE = "";

	public void mensagens(String tipo,String msg) {
		
		if(tipo.equals("t")) {
			this.listaT += msg + "\n";
			this.lt.setText(this.listaT);
		}else if(tipo.equals("l")) {
			this.listaL += msg + "\n";
			System.out.println(this.listaL);
			this.ll.setText(this.listaL);
		}else {
			this.listaE += msg + "\n";
			this.le.setText(this.listaE);
		}
	}
	public void inicialisar(Lexico lexico, Label lt, Label ll, Label le) throws SintaticoException {
		this.listaTokens = lexico;
		this.lt = lt;
		this.ll = ll;
		this.le = le;
		
		pilha.clear();
		pilha.push(Constants.DOLLAR);
		pilha.push(Constants.START_SYMBOL);

		String p = "PILHA: " + pilha;
		mensagens("l", p);
		
		try {
			tokenAtual = lexico.nextToken();
			mensagens("l",tokenAtual.toString());
			
		} catch (LexicalError e) {
			System.err.println(e.getMessage() + "e;, em " + e.getPosition());
		}

		while (!analise());

		if (compilado) {
			System.out.println("COMPILADO COM SUCESSO");
		}
	}

	public boolean analise() throws SintaticoException {
		int x, a;
		
		System.out.println("Token atual: " + tokenAtual);

		if (tokenAtual == null) {
			int pos = 0;
			if (tokenAnterior != null) {
				pos = tokenAnterior.getPosition() + tokenAnterior.getLexeme().length();
			}
			tokenAtual = new Token(Constants.DOLLAR, "$", pos);
		}

		x = (Integer) (pilha.pop());
		a = tokenAtual.getId();
		Lexico t;
		
		System.out.println(">Token removido do topo da pilha:" + x);
		System.out.println("PILHA: " + pilha);
		System.out.println(">Token atual é:" + tokenAtual);

		if (x == Constants.EPSILON) {
			System.out.println("token [" + x + "] da pilha" + " - é igual a " + Constants.EPSILON);
			System.out.println("Resetando analise...");
			System.out.println("-----------------------");
			return false;
		} else if (terminal(x)) {
			System.out.println("Token [" + x + "] da pilha é terminal");
			if (x == a) {
				System.out.println("Token [" + x + "] "+ "da pilha é igual ao token atual: " + a);

				if (pilha.empty()) {
					System.out.println("Pilha está vazia");

					compilado = true;
					return true;
				} else {
					try {
						tokenAnterior = tokenAtual;
						tokenAtual = listaTokens.nextToken();
						
						mensagens("t",tokenAtual.toString());
						System.out.println("Novo token chamado: " + tokenAtual);
						System.out.println("Resetando analise...");
						System.out.println("-----------------------");
					} catch (LexicalError e) {
						System.err.println(e.getMessage() + "e;, em " + e.getPosition());
						return true;
					}
					
					return false;
				}
			} else {
				throw new SintaticoException(ParserConstants.PARSER_ERROR[x], tokenAtual.getPosition());
			}
		} else if (naoTerminal(x)) {
			System.out.println(x + " - é um não terminal");
			
			if (consultaProducoes(x, a)) {
				System.out.println("Resetando analise...");
				System.out.println("-----------------------");
				return false;
			} else {
				throw new SintaticoException(ParserConstants.PARSER_ERROR[x], tokenAtual.getPosition());
			}
		} else {
			return false;
		}
	}

	public boolean terminal(int token) {
		return token < Constants.FIRST_NON_TERMINAL;
	}

	public boolean naoTerminal(int token) {
		return token >= Constants.FIRST_NON_TERMINAL;
	}

	private boolean consultaProducoes(int topoDaPilha, int tokenAtual) {
		int pos, tamanho;
		int[] producao;

		pos = ParserConstants.PARSER_TABLE[topoDaPilha - ParserConstants.FIRST_NON_TERMINAL][tokenAtual - 1];
		System.out.println("Posição da regra: " + "{" + (topoDaPilha - ParserConstants.FIRST_NON_TERMINAL) + ","
				+ (tokenAtual - 1) + "}");

		if (pos >= 0) {
			producao = ParserConstants.PRODUCTIONS[pos];
			tamanho = producao.length;

			System.out.println("Nova produção encontrada - " + Arrays.toString(producao));

			for (int i = tamanho - 1; i >= 0; i--) {
				pilha.push(producao[i]);
			}
			System.out.println(">>Adicionando a pilha..");
			System.out.println("PILHA: " + pilha);
			return true;
		} else {
			System.out.println("Não foi encontrado regra para o conjunto de tokens: token atual [" + topoDaPilha + "] e [" + tokenAtual + "]");
			return false;
		}
	}
}
