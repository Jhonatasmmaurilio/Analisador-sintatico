package view;

import src.ListaEncadeada;
import src.Simbolo;

public class tabelaSimbolosController {
	private static ListaEncadeada[] ts;
	private static int totalInseridos = 0;

	public static void inserir(String nome, String categoria, int nivel, String geralA, String geralB) {
		int indice, posicao;

		if (totalInseridos < ts.length) {
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
				System.out.println("Colis�o detectada<<<<<");

				Simbolo aux = ts[indice].getPrimeiro();

				// busca a ultima posicao
				while (aux.getProximo() != null) {
					aux = aux.getProximo();
				}

				aux.setProximo(s);
				ts[indice].setUltimo(aux.getProximo());
				System.out.println(s.getNome() + " inserido");
			}
		} else {
			System.out.println("Nao ha mais espa�o na lista");
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

	private static Simbolo Buscar2(String nome) {
		System.out.println("BUSCAR: " + nome);
		
		if(totalInseridos > 0) {
			int indice = Hashing(nome);
			
			if(ts[indice] != null) {
				Simbolo el = ts[indice].getPrimeiro();
				
				if(el.getNome().equals(nome)) {
					System.out.println("Item encontrado\n");
					return ts[indice].getPrimeiro();
				}else {
					while(el.getProximo() != null) {
						el = el.getProximo();
						
						if(el.getNome().equals(nome)) {
							System.out.println("Item encontrado\n");
							return el;
						}
					}
				}
			}else {
				System.out.println("Nenhum resultado encontrado\n");
				return null;
			}
		}else {
			System.out.println("Nenhum item inserido at� o momento\n");
		}
		
		System.out.println("Nenhum item encontrado");
		System.out.println("---------------------------");
		return null;
	}
	
	private static void Alterar(String nome, Simbolo novo) {
		System.out.println("ALTERAR:" + nome);
		Simbolo el = Buscar2(nome);
		
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

		Buscar(nome, true);

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

	private static void MonstraLista(ListaEncadeada lista) {
		System.out.println("=======================");
		System.out.println("EXIBINDO LISTA");

		if (lista != null) {
			Simbolo el = lista.getPrimeiro();
			System.out.println(el);

			if (el.getProximo() != null) {
				while (el.getProximo() != null) {
					el = el.getProximo();
					System.out.println(el);
				}
			}
		} else {
			System.out.println("Nenhum item nessa lista");
		}

		System.out.println("=======================");
	}

	public static void main(String[] args) {
		InicializarTabela(10);

		inserir("var", "VAR", 1, "", "");
		inserir("const", "VAR", 1, "", "");
		inserir("procedure1", "VAR", 0, "", "");
		inserir("procedure2", "VAR", 0, "", "");
		inserir("procedure3", "VAR", 0, "", "");
		inserir("var1", "VAR", 0, "", "");
		inserir("var2", "VAR", 0, "", "");
		inserir("var3", "VAR", 0, "", "");
		inserir("var4", "VAR", 0, "", "");
		inserir("var5", "VAR", 0, "", "");
		inserir("var6", "VAR", 0, "", "");
		inserir("var7", "VAR", 0, "", "");
		inserir("var8", "VAR", 0, "", "");
		inserir("var9", "VAR", 0, "", "");

		MostrarTabela();

//		Buscar("var", false);
//		Deletar("procedure2");
//		MostrarTabela();
//		
		Simbolo novo = new Simbolo("procedure4", "VAR", 1, "", "");
		Alterar2("var2", novo);
//
//		MostrarTabela();
//		inserir("var10", "VAR", 0, "", "");
		MostrarTabela();
		
	}
}