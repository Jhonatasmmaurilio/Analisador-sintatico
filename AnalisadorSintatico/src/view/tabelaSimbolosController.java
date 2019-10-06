package view;

import src.ListaEncadeada;
import src.Simbolo;

public class tabelaSimbolosController {
	private static ListaEncadeada[] ts = new ListaEncadeada[10];
	private static int totalInseridos = 0;
	
	public static void inserir(String nome, String categoria, int nivel, String geralA, String geralB) {
		int hash, indice, posicao;
		
		if(totalInseridos < ts.length) {
			ListaEncadeada le = new ListaEncadeada();
			Simbolo s = new Simbolo(nome, categoria, nivel, geralA, geralB);
			
			System.out.println("inserindo: " + nome);
			
			indice = hash(s.getNome());
//			indice = 0;
			
			System.out.println("hash:" + indice);
			
			//verifica se a posição do hash esta disponivel
			posicao = verificaPosicao(indice);
		
			if(posicao == 1) {
				le.setPrimeiro(s);
				le.setQtd();
				ts[indice] = le;
				System.out.println(s.getNome() + " inserido");
			}else {
				System.out.println("Colisão detectada");

				Simbolo aux = ts[indice].getPrimeiro();
				
				//busca a ultima posicao
				while(aux.getProximo() != null) {
					aux = aux.getProximo();
				}
				
				aux.setProximo(s);
				ts[indice].setUltimo(aux.getProximo());
				System.out.println(s.getNome() + " inserido");
			}
		}else {
			System.out.println("Nao ha mais espaço na lista");
		}
		
		System.out.println("-------------------------\n");
	}
	
	public static int hash(String nome) {
		int val = 0, tamanho = ts.length;
		
		for (int i = 0; i < nome.length(); i++) {
			val = 37 * val + nome.charAt(i);
		}
		
		val %= tamanho;
		
		if (val < 0) {
			val += tamanho;
		}
		
		return val;
	}

	private static int verificaPosicao(int indice) {
//		System.out.println("verificando posicao");

		if(ts[indice] == null) {
			return 1;
		}else {
			return -1;
		}
	}

	private static void mostrarTabela() {
		System.out.println("TABELA------------------------------\n");
		
		for(int i = 0;i < ts.length; i++) {
			if(ts[i] != null) {
				String msg = ts[i].getPrimeiro() + "|";
				
				Simbolo aux = ts[i].getPrimeiro();
				
				//busca a ultima posicao
				while(aux.getProximo() != null) {
					msg += aux.getProximo().getNome() + "|";
					aux = aux.getProximo();
				}
				
				System.out.println(msg);
			}else {
				System.out.println(i + " - null");
			}
		}
	}
	
	public static void main(String[] args) {
		inserir("var", "VAR", 1, "", "");
		inserir("const", "VAR", 1, "", "");
		inserir("procedure", "VAR", 1, "", "");
		
		mostrarTabela();
	}
}