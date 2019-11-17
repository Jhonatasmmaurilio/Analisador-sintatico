package sintatico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javafx.scene.control.Label;
import lexico.LexicalError;
import lexico.Lexico;
import token.Constants;
import token.ParserConstants;
import token.Token;

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
	boolean erro = false;

	public void mensagens(String tipo, String msg) {
		if (tipo.equals("t")) {
			listaT += msg + "\n";
			lt.setText(listaT);
		} else if (tipo.equals("l")) {
			listaL += msg + "\n";
			ll.setText(listaL);
		} else {
			listaE += msg + "\n";
			le.setText(listaE);
		}
	}

	public void inicialisar(Lexico lexico, Label lt, Label ll, Label le) {
		listaTokens = lexico;
		this.lt = lt;
		this.ll = ll;
		this.le = le;

		try {
			tokenAtual = lexico.nextToken();
			mensagens("t", tokenAtual.toString() + " - " + getNomeToken(tokenAtual.getId()));
			
			pilha.push(Constants.DOLLAR);
			pilha.push(Constants.FIRST_NON_TERMINAL);

			mensagens("l", "PILHA: " + pilha);

		} catch (LexicalError e) {
			mensagens("l", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
			mensagens("e", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
		}

		do {
			try {
				analise();
			} catch (SintaticoException e) {
				e.printStackTrace();
			}
		} while (!erro);

		if (compilado) {
			mensagens("l", "COMPILADO COM SUCESSO");
			mensagens("e", "COMPILADO COM SUCESSO");
			pilha.clear();
		}
	}

	public void analise() throws SintaticoException {
		int x, a;
		mensagens("l", "Token atual: " + tokenAtual);

		if (tokenAtual == null) {
			mensagens("l", "Token atual é igual a null");

			tokenAtual = new Token(Constants.DOLLAR, "$", 1);
		}

		if (pilha.size() != 0) {
			x = (Integer) pilha.pop();
			a = tokenAtual.getId();
			Lexico t;

			mensagens("l", ">Token removido do topo da pilha:" + x);
			mensagens("l", "PILHA: " + pilha);
			mensagens("l", ">Token atual é:" + tokenAtual);
			
			if (x == 0) {
				mensagens("l", "token [" + x + "] da pilha" + " - é cadeia vazia");
				mensagens("l", "Resetando analise...");
				mensagens("l", "-----------------------");

				this.erro = false;
			} else if (x < Constants.FIRST_NON_TERMINAL) {
				mensagens("l", "Token [" + x + "] da pilha é Terminal");

				if (x == a) {
					mensagens("l", "Token [" + x + "] " + "da pilha é igual ao token atual: " + a);

					this.erro = comparaTokens(x, a);
				} else {
					mensagens("l", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					mensagens("e", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					
					this.erro = true;
					throw new SintaticoException(ParserConstants.PARSER_ERROR[x]);
				}
			} else if (x >= Constants.FIRST_NON_TERMINAL) {
				mensagens("l", x + " - é um Não terminal");

				if (consultaProducoes(x, a)) {
					mensagens("l", "Resetando analise...");
					mensagens("l", "-----------------------");

					this.erro = false;
				} else {
					mensagens("l", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					mensagens("e", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					
					this.erro = true;
					throw new SintaticoException(ParserConstants.PARSER_ERROR[x]);
				}
			} else if(x < Constants.FIRST_SEMANTIC_ACTION) {
				
			} else {
				this.erro = false;
			}
		} else {
			mensagens("l", "Pilha está vazia");

			compilado = true;
			this.erro = true;
		}
	}

	private boolean consultaProducoes(int topoDaPilha, int tokenAtual) {
		int posPro, tamanho;
		int[] producao;

		posPro = ParserConstants.PARSER_TABLE[topoDaPilha - Constants.FIRST_NON_TERMINAL][tokenAtual - 1];

		mensagens("l", "Posição da regra: " + "{" + (topoDaPilha - Constants.FIRST_NON_TERMINAL) + ","
				+ (tokenAtual - 1) + "}");

		if (posPro >= 0) {
			producao = ParserConstants.PRODUCTIONS[posPro];
			tamanho = producao.length;

			mensagens("l", "Nova produção encontrada - " + Arrays.toString(producao));

			for (int i = tamanho - 1; i >= 0; i--) {
				pilha.push(producao[i]);
			}

			mensagens("l", ">>Adicionando a pilha..");
			mensagens("l", "PILHA: " + pilha);

			return true;
		} else {
			mensagens("l", "Não foi encontrado regra para o conjunto de tokens: token atual [" + topoDaPilha + "] e ["
					+ tokenAtual + "]");

			return false;
		}
	}

	private boolean comparaTokens(int x, int a) {
		try {
			tokenAnterior = tokenAtual;
			tokenAtual = listaTokens.nextToken();

			if (tokenAtual != null) {
				mensagens("t", tokenAtual.toString() + " - " + getNomeToken(tokenAtual.getId()));
			}

			mensagens("l", "Novo token chamado: " + tokenAtual);
			mensagens("l", "Resetando analise...");
			mensagens("l", "-----------------------");
		} catch (LexicalError e) {
			mensagens("l", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
			mensagens("e", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
			return true;
		}

		return false;
	}

	public String getNomeToken(int id) {
		if (id >= 2 && id <= 25) {
			return "Palavra reservada";
		} else if (id == 26) {
			return "Abre Parenteses";
		} else if (id == 27) {
			return "Fecha Parenteses";
		} else if (id == 28) {
			return "Ponto e virgula";
		} else if (id == 29) {
			return "Ponto";
		} else if (id == 30) {
			return "Adição";
		} else if (id == 31) {
			return "Subtração";
		} else if (id == 32) {
			return "Multiplicação";
		} else if (id == 33) {
			return "Divisão";
		} else if (id == 34) {
			return "Virgula";
		} else if (id == 35) {
			return "Dois pontos";
		} else if (id == 36) {
			return "Atribuição";
		} else if (id == 37) {
			return "Igualdade";
		} else if (id == 38) {
			return "Menor";
		} else if (id == 39) {
			return "Maior ou igual";
		} else if (id == 40) {
			return "Menor";
		} else if (id == 41) {
			return "Menor igual";
		} else if (id == 42) {
			return "Diferente";
		} else if (id == 43) {
			return "Identificador";
		} else if (id == 44) {
			return "Inteiro";
		} else {
			return "Literal";
		}
	}
}