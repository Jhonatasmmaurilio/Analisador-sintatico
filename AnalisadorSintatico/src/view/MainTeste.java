package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import src.LexicalError;
import src.Lexico;
import src.Sintatico;
import src.SintaticoException;
import src.Token;

public class MainTeste {
	public static void main(String[] args) throws SintaticoException {
		Lexico lexico = new Lexico();
		Sintatico sintatico = new Sintatico();

		String caminhoArquivo = "codigos/codigo-03.txt";
		int i = 0;

		try {
			BufferedReader reader;
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new FileReader(caminhoArquivo));

			String linha = reader.readLine();
			String codigo = linha + "\n";

			while (linha != null) {
				linha = reader.readLine();

				if (linha != null) {
					codigo += linha + "\n";
				}
			}

			lexico.setInput(codigo);

			Token token = null;
			
			while ((token = lexico.nextToken()) != null) {
				System.out.println(i + "-" + token.getId() + ":[" + token.getLexeme() + "]" + token.getPosition());
				i++;
			}

		} catch (FileNotFoundException ex) {
			System.out.println("Erro: Arquivo não encontrado!");
		} catch (IOException ex) {
			System.out.println("Erro: não foi possível ler o arquivo");
		} catch (LexicalError e) {
			System.err.println(e.getMessage() + "e;, em " + e.getPosition());
		}

	}
}
