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

	public void mensagens(String tipo, String msg) {
		if (tipo.equals("t")) {
			this.listaT += msg + "\n";
			this.lt.setText(this.listaT);
		} else if (tipo.equals("l")) {
			this.listaL += msg + "\n";
			this.ll.setText(this.listaL);
		} else {
			this.listaE += msg + "\n";
			this.le.setText(this.listaE);
		}
	}

	public void inicialisar(Lexico lexico, Label lt, Label ll, Label le) {
		this.listaTokens = lexico;
		this.lt = lt;
		this.ll = ll;
		this.le = le;
		boolean erros = true;

		pilha.push(Constants.DOLLAR);
		pilha.push(Constants.FIRST_NON_TERMINAL);

		String p = "PILHA: " + pilha;
		mensagens("l", p);

		try {
			tokenAtual = lexico.nextToken();
			mensagens("t", tokenAtual.toString());

		} catch (LexicalError e) {
			mensagens("l", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
			mensagens("e", "Erro Lexico: " + e.getMessage() + ", em " + e.getPosition());
		}

		try {
			while (!analise())
				;
		} catch (SintaticoException e) {
			e.printStackTrace();
		}

		if (compilado) {
			mensagens("l", "COMPILADO COM SUCESSO");
			mensagens("e", "COMPILADO COM SUCESSO");
		}
	}

	public boolean analise() throws SintaticoException {
		int x, a;
		mensagens("l", "Token atual: " + tokenAtual);

		if (tokenAtual == null) {
			mensagens("l", "Token atual é igual a null");

			tokenAtual = new Token(Constants.DOLLAR, "$", 1);
		}

		x = (Integer) pilha.pop();
		a = tokenAtual.getId();
		Lexico t;

		mensagens("l", ">Token removido do topo da pilha:" + x);
		mensagens("l", "PILHA: " + pilha);
		mensagens("l", ">Token atual é:" + tokenAtual);

		if (pilha.size() != 0) {
			if (x == 0) {
				mensagens("l", "token [" + x + "] da pilha" + " - é cadeia vazia");
				mensagens("l", "Resetando analise...");
				mensagens("l", "-----------------------");

				return false;
			} else if (terminal(x)) {
				mensagens("l", "Token [" + x + "] da pilha é terminal");

				if (x == a) {
					mensagens("l", "Token [" + x + "] " + "da pilha é igual ao token atual: " + a);

					return (comparaTokens(x, a));
				} else {
					mensagens("l", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					mensagens("e", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					throw new SintaticoException(ParserConstants.PARSER_ERROR[x]);
				}
			} else if (naoTerminal(x)) {
				mensagens("l", x + " - é um não terminal");

				if (consultaProducoes(x, a)) {
					mensagens("l", "Resetando analise...");
					mensagens("l", "-----------------------");

					return false;
				} else {
					mensagens("l", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					mensagens("e", "Erro sintático: " + ParserConstants.PARSER_ERROR[x]);
					throw new SintaticoException(ParserConstants.PARSER_ERROR[x]);
				}
			} else {
				return false;
			}
		} else {
			mensagens("l", "Pilha está vazia");

			compilado = true;
			return true;
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

		pos = ParserConstants.PARSER_TABLE[topoDaPilha - Constants.FIRST_NON_TERMINAL][tokenAtual - 1];

		mensagens("l", "Posição da regra: " + "{" + (topoDaPilha - Constants.FIRST_NON_TERMINAL) + ","
				+ (tokenAtual - 1) + "}");

		if (pos >= 0) {
			producao = ParserConstants.PRODUCTIONS[pos];
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
				String ta = tokenAtual.toString();
				mensagens("t", ta);
			}

			mensagens("l", "Novo token chamado: " + tokenAtual);
			mensagens("l", "Resetando analise...");
			mensagens("l", "-----------------------");
		} catch (LexicalError e) {
			mensagens("l", "Erro Lexico: " + e.getMessage() + "e, em " + e.getPosition());
			mensagens("e", "Erro Lexico: " + e.getMessage() + "e, em " + e.getPosition());
			return true;
		}

		return false;
	}
}