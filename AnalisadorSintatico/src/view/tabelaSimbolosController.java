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

//			System.out.println("INSERIR: (" + nome + ")");

			indice = Hashing(s.getNome());

//			System.out.println("hash:" + indice);

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
				ts[indice].setUltimo(aux.getProximo());
				System.out.println(s.getNome() + " inserido");
			}
		} else {
			System.out.println("Nao ha mais espaço na lista");
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

	private static boolean Buscar(String nome, boolean deletar) {
		System.out.println("BUSCAR por: " + nome);

		if (totalInseridos > 0) {
			for (int i = 0; i < ts.length; i++) {

				if (ts[i] != null) {
					Simbolo el = ts[i].getPrimeiro();

					if (el.getNome().equals(nome)) {
						System.out.println("item encontrado na posição: " + i);

						if (deletar) {
							if (el.getProximo() == null) {
								ts[i] = null;
								System.out.println("Item deletado com sucesso");
								totalInseridos--;
							} else {
								ts[i].setPrimeiro(el.getProximo());
								System.out.println("Item deletado com sucesso");
							}
						}

						return true;
					} else {
						if (el.getProximo() != null) {
							Simbolo aux = el;

							while (aux.getProximo() != null) {
								Simbolo elProx = aux.getProximo();

								if (elProx.getNome().equals(nome)) {
									System.out.println("Item encontrado na posição: " + i + "(colisão)");

									if (deletar) {
										aux.setProximo(elProx.getProximo());
										System.out.println("Item deletado com sucesso");
									}

									return true;
								} else {
									aux = aux.getProximo();
								}
							}
						}
					}
				}
			}

			System.out.println("Item não encontrado");
		} else {
			System.out.println("Voce não inseriu nenhum item até o momento");
		}

		return false;
	}

	private static boolean Alterar(String nome, Simbolo novo) {
		System.out.println("ALTERAR: " + nome);

		if (totalInseridos > 0) {
			for (int i = 0; i < ts.length; i++) {
				if (ts[i] != null) {
					Simbolo el = ts[i].getPrimeiro();

					if (el.getNome().equals(nome)) {
						el.setNome(novo.getNome());
						el.setCategoria(novo.getCategoria());
						el.setNivel(novo.getNivel());
						el.setGeralA(novo.getGeralA());
						el.setGeralB(novo.getGeralB());

						System.out.println("Item alterado com sucesso");

						return true;
					} else {
						if (el.getProximo() != null) {
							Simbolo aux = el;

							while (aux.getProximo() != null) {
								Simbolo elProx = aux.getProximo();

								if (elProx.getNome().equals(nome)) {
									elProx.setNome(novo.getNome());
									elProx.setCategoria(novo.getCategoria());
									elProx.setNivel(novo.getNivel());
									elProx.setGeralA(novo.getGeralA());
									elProx.setGeralB(novo.getGeralB());

									System.out.println("Item alterado com sucesso2");

									return true;
								} else {
									aux = aux.getProximo();
								}
							}
						}
					}
				}
			}

			System.out.println("Item não encontrado");
		} else {
			System.out.println("Voce não inseriu nenhum item até o momento");
		}

		return false;
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

		Buscar("var", false);

		Deletar("procedure2");

		MostrarTabela();

		Simbolo s = new Simbolo("procedure4", "VAR", 1, "", "");
		Alterar("procedure3", s);

		MostrarTabela();

		inserir("var10", "VAR", 0, "", "");

		MostrarTabela();
	}
}