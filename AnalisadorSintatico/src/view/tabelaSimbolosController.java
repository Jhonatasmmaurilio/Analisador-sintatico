package view;

import src.ListaEncadeada;
import src.Simbolo;

public class tabelaSimbolosController {
	private static ListaEncadeada[] ts;
	private static int totalInseridos = 0;

	public static void inserir(String nome, String categoria, int nivel, String geralA, String geralB) {
		int indice, posicao;

		ListaEncadeada le = new ListaEncadeada();
		Simbolo s = new Simbolo(nome, categoria, nivel, geralA, geralB);

		indice = Hashing(s.getNome());

		posicao = VerificaPosicao(indice);

		if (posicao == 1) {
			ts[indice] = le;
			ts[indice].setPrimeiro(s);
			System.out.println(s.getNome() + " inserido");
			totalInseridos++;
		} else {
			System.out.println("Colisão detectada<<<<<");

			Simbolo aux = ts[indice].getPrimeiro();

			// busca a ultima posicao
			while (aux.getProximo() != null) {
				aux = aux.getProximo();
			}

			aux.setProximo(s);
			s.setAnterior(aux);
			
			ts[indice].setUltimo(aux.getProximo());
			
			System.out.println(s.getNome() + " inserido");
		}
			
		System.out.println("-------------------------");
	}

	public static int Hashing(String nome) {
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

	private static int VerificaPosicao(int indice) {
//		System.out.println("verificando posicao");

		if (ts[indice] == null) {
			return 1;
		} else {
			return -1;
		}
	}

	private static Simbolo Buscar(String nome) {
		System.out.println("BUSCAR: " + nome);
		
		if(totalInseridos > 0) {
			int indice = Hashing(nome);
			
			if(ts[indice] != null) {
				Simbolo el = ts[indice].getPrimeiro();
				
				if(el.getNome().equals(nome)) {
					System.out.println("Item encontrado");
					return ts[indice].getPrimeiro();
				}else {
					while(el.getProximo() != null) {
						el = el.getProximo();
						
						if(el.getNome().equals(nome)) {
							System.out.println("Item encontrado");
							return el;
						}
					}
				}
			}else {
				System.out.println("Nenhum resultado encontrado\n");
				return null;
			}
		}else {
			System.out.println("Nenhum item inserido até o momento\n");
		}
		
		System.out.println("Nenhum item encontrado");
		System.out.println("---------------------------");
		return null;
	}
	
	private static void Alterar(String nome, Simbolo novo) {
		System.out.println("ALTERAR:" + nome);
		Simbolo el = Buscar(nome);
		
		if(el != null) {
			el.setNome(novo.getNome());
			el.setCategoria(novo.getCategoria());
			el.setNivel(novo.getNivel());
			el.setGeralA(novo.getGeralA());
			el.setGeralB(novo.getGeralB());
			
			System.out.println("Item alterado com sucesso");
			
		}else {
			System.out.println("Nenhum item encontrado");
		}
		
		System.out.println("--------------------");
	}
	
	private static boolean Deletar(String nome) {
		System.out.println("DELETAR");

		Simbolo el = Buscar(nome);
		System.out.println(el);
		
		if(el != null) {
			//primeiro da fila
			if(el.getAnterior() == null) {
				ts[Hashing(nome)].setPrimeiro(el.getProximo());
			}
			//unico na fila
			if(el.getAnterior() == null && el.getProximo() == null) {
				ts[Hashing(nome)] = null;
			}
			//ultimo da fila
			if(el.getAnterior() != null && el.getProximo() == null) {
				el.getAnterior().setProximo(null);
				el.setAnterior(null);
				el.setProximo(null);
			}
			//no meio da fila
			if(el.getAnterior() != null && el.getProximo() != null) {
				el.getAnterior().setProximo(el.getProximo());
				el.getProximo().setAnterior(el.getAnterior());
				el.setAnterior(null);
				el.setProximo(null);
			}
		}else {
			System.out.println("Item não encontrado");
		}
				
		System.out.println("-----------------------------------");

		return true;
	}

	private static void InicializarTabela(int tamanho) {
		ts = new ListaEncadeada[tamanho];
		System.out.println("Tabela inicializada com tamanho:" + tamanho);
		System.out.println("--------------------------------");
	}

	private static void MostrarTabela() {
		System.out.println("TABELA\n");

		for (int i = 0; i < ts.length; i++) {
			if (ts[i] != null) {
				String msg = i + " - [" + ts[i].getPrimeiro();

				Simbolo aux = ts[i].getPrimeiro();

				// busca a ultima posicao
				while (aux.getProximo() != null) {
					msg += "|" + aux.getProximo();
					aux = aux.getProximo();
				}

				System.out.println(msg + "]");
			} else {
				System.out.println(i + " - null");
			}
		}

		System.out.println("------------------------------------");
	}

	public static void main(String[] args) {
		InicializarTabela(10);

		inserir("teste1", "VAR", 1, "", "");
		inserir("teste2", "VAR", 1, "", "");
		inserir("qwer1", "CONST", 0, "", "");
		inserir("qwert2", "CONST", 0, "", "");
		inserir("qwert3", "CONST", 0, "", "");
		inserir("loren01", "PAR", 0, "", "");
		inserir("ipsum01", "PAR", 0, "", "");
		inserir("loren02", "PROC", 0, "", "");
		inserir("loren03", "PROC", 0, "", "");
		inserir("ipslum01", "VAR", 0, "", "");

		MostrarTabela();

		Simbolo el1 = new Simbolo("qwert3", "CONST", 0, "", "");
		Simbolo el2 = new Simbolo("ipsum01", "CONST", 0, "", "");
		Simbolo el3 = new Simbolo("loren01", "CONST", 0, "", "");
		Simbolo el4 = new Simbolo("qwert1", "CONST", 0, "", "");
		Simbolo el5 = new Simbolo("loren03", "CONST", 0, "", "");
		
		Alterar(el1.getNome(), el1);
		Alterar(el2.getNome(), el2);
		Alterar(el3.getNome(), el3);
		Alterar(el4.getNome(), el4);
		Alterar(el5.getNome(), el5);
		
		MostrarTabela();
		
		Deletar("qwert3");
		Deletar("ipsum01");
		Deletar("ipslum01");
		
		MostrarTabela();
		
		Buscar("charbel");
		
		System.out.println(Buscar("teste1") + "\n-----------------");
		System.out.println(Buscar("teste2") + "\n-----------------");
		System.out.println(Buscar("qwert2") + "\n-----------------");
	}
}