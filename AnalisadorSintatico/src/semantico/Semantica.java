package semantico;

import java.util.Stack;

import maquinahipotetica.AreaInstrucoes;
import maquinahipotetica.AreaLiterais;
import maquinahipotetica.MaquinaHipotetica;
import token.Simbolo;
import token.TabelaSimbolos;

public class Semantica {
	private TabelaSimbolos ts;
	private static MaquinaHipotetica MH = new MaquinaHipotetica();
	private String[][] instrucoes = new String[1000][5];

	private Stack<Integer> pilhaIf;
	private Stack<Integer> pilhaWhile;
	private Stack<Integer> pilhaRepeat;
	private Stack<Integer> pilhaProcedure;
	private Stack<Integer> pilhaCase;
	private Stack<Integer> pilhaFor;
	private Stack<Simbolo> pilhaParametros;

	private Simbolo identificador;
	private Simbolo procedureAtual;
	private Simbolo index;

	private String tipoIdentificador;
	private String nIdentificador;
	private String contexto;
	private String token;
	private String nomeIdentificador;
	private String nomeProcedure;

	private int endereco;
	private int nivelAtual;
	private int totalVariavel;
	private int deslocamento;
	private int n;// nao sei pra que serve
	private int nivel;
	private int totalParametro;
	private int enderecoA;
	private int ptLivre;
	private int escopo;
	private int lc;
	private int lit;
	private int geralA;

	private boolean temParametro;

	private AreaInstrucoes areaInstrucoes;
	private AreaLiterais areaLiterais;

	public void msg(String msg) {
		System.out.println(msg);
	}

	public void addInstrucao(int index, String ins, String op1, String op2) {
		instrucoes[index][1] = index + "";
		instrucoes[index][2] = ins;
		instrucoes[index][3] = op1;
		instrucoes[index][4] = op2;
	}

	public void AnaliseSemantica(int acao, Simbolo token, Simbolo tokenAnterior) throws SemanticoExepition {
		switch (acao - 77) {

		case 100:
			pilhaIf = new Stack();
			pilhaWhile = new Stack();
			pilhaRepeat = new Stack();
			pilhaProcedure = new Stack();
			pilhaCase = new Stack();
			pilhaFor = new Stack();
			pilhaParametros = new Stack();

			ts = new TabelaSimbolos();
			ts.InicializarTabela(1000);

			areaInstrucoes = new AreaInstrucoes();
			areaLiterais = new AreaLiterais();

			MaquinaHipotetica.InicializaAI(areaInstrucoes);
			MaquinaHipotetica.InicializaAL(areaLiterais);

			nivelAtual = 0;
			ptLivre = 1;
			escopo = 1;
			totalVariavel = 0;
			deslocamento = 3;
			lc = 1;
			lit = 1;

			msg("acao: 100 - reconhece nome programa");
			msg("inicializa programa");

			break;
		case 101:
			addInstrucao(areaInstrucoes.LC, "PARA", "-", "-");

			MH.IncluirAI(areaInstrucoes, 26, 0, 0);

			msg("acao: 101 - final programa");
			msg("add MH: PARA");

			break;
		case 102:
			addInstrucao(areaInstrucoes.LC, "AMEN", "-", (deslocamento + totalVariavel) + "");

			MH.IncluirAI(areaInstrucoes, 24, 0, (deslocamento + totalVariavel));

			msg("acao: 102 - apos declaaracao de variavel");
			msg("add MH: AMEN");
			msg("deslocamento: " + deslocamento);
			msg("totalVariavel: " + totalVariavel);

			break;
		case 103:
			tipoIdentificador = "ROTULO";

			msg("acao: 103 - identificador rotulo");

			break;
		case 104:
			msg("acao: 104 - nome de rotulo, variavel ou parametro");

			Simbolo s = ts.buscar(token.getNome());

			if (tipoIdentificador.equals("ROTULO")) {
				msg("token (" + token + ") igual a rotulo");

				if (s == null) {
					ts.inserir(token.getNome(), "ROTULO", nivelAtual, "0", "-");

					msg(token + " nao encontrado na TS");
					msg(token + " inserido na TS");
				} else {
					msg(token + " ja esta na TS");

					if (s.getNivel() != nivelAtual) {
						ts.inserir(token.getNome(), "ROTULO", nivelAtual, "0", "-");

						msg(token + " inserido na TS com nivel: " + nivelAtual);
					} else {
						msg("Erro semantico: " + "O Rótulo (" + token.getNome() + ") ja foi declarado");
						throw new SemanticoExepition(
								"Erro semantico: O Rótulo (" + token.getNome() + ") ja foi declarado");
					}
				}
			}

			if (tipoIdentificador.equals("VAR")) {
				msg("tipoIdentificador é igual a VAR");

				if (s == null) {
					ts.inserir(token.getNome(), "VAR", nivelAtual, "0", "-");
					totalVariavel++;

					msg(token.getNome() + " nao encontrado na TS");
					msg(token.getNome() + " inserido na TS");
					msg("totalVariaveis: " + totalVariavel);
				} else {
					if (s.getNivel() != nivelAtual) {
						ts.inserir(token.getNome(), "VAR", nivelAtual, "0", "-");
						totalVariavel++;

						msg(token.getNome() + " encontrado na TS, inserido na TS com nivel: " + nivelAtual);
						msg("totalVariavel: " + totalVariavel);
					} else {
						msg("Erro semantico: A variável (" + token.getNome() + ") já foi declarada");
						throw new SemanticoExepition(
								"Erro semantico: A variável (" + token.getNome() + ") já foi declarada");
					}
				}
			}

			if (tipoIdentificador.equals("PARAMETRO")) {
				msg("tipoIdentificador igual a PARAMETRO");

				if (s == null) {
					ts.inserir(token.getNome(), "PARAMETRO", nivelAtual, "-", "-");

					msg(token.getNome() + " nai encontrado na TS");
					msg(token.getNome() + " inserido na TS");
				} else {
					if (s.getNivel() != nivelAtual) {
						Simbolo parametro = new Simbolo(token.getNome(), "PARAMETRO", nivelAtual, "-", "-");

						ts.inserir(token.getNome(), "PARAMETRO", nivelAtual, "-", "-");
						pilhaParametros.push(parametro);
						totalParametro++;

						msg(token.getNome() + " encontrado na TS, inserido na tS com nivel: " + nivelAtual);
						msg("add pilhaParametro: " + token.getNome());
						msg("totalParametro: " + totalParametro);
					} else {
						msg("Erro semantico: Parametro (" + token.getNome() + ") já declarado");
						throw new SemanticoExepition(
								"Erro semantico: Parametro (" + token.getNome() + ") já declarado");
					}
				}
			}

			break;
		case 105:
			msg("acao 105: reconhece nome CONSTANTE");

			s = ts.buscar(token.getNome());

			if (s == null) {
				ts.inserir(token.getNome(), "CONSTANTE", nivelAtual, "-", "-");
				identificador = token;
			} else {
				msg("Constatnte " + token.getNome() + " ja declarada");
				throw new SemanticoExepition("Erro semantico: Constante " + token.getNome() + " ja declarada");
			}

			break;
		case 106:
			identificador.setGeralA(token.getNome());

			msg("acao 106: reconhece valor da onstante");
			msg("identificador geralA: " + identificador.getGeralA());

			break;
		case 107:
			tipoIdentificador = "VAR";
			totalVariavel = 0;

			msg("acao 107: seta tipo de variavel");
			msg("tipoIdentificador: VAR");

			break;
		case 108:
			procedureAtual = new Simbolo(token.getNome(), "PROCEDURE", nivelAtual, (areaInstrucoes.LC + 1) + "", "0");
			ts.inserir(token.getNome(), "PROCEDURE", nivelAtual, (areaInstrucoes.LC + 1) + "", "0");
			nivelAtual++;
			totalVariavel++;
			totalParametro = 0;
//			temParametro = false;

			msg("acao: apos nome da procedure");
			msg("insere novo simbolo");
			msg("nivelAtual: " + nivelAtual);
			msg("deslocamento: " + deslocamento);
			msg("totalVariavel: " + totalVariavel);
			msg("totalParametro: 0");

			break;
		case 109:
			msg("acao 109: apos declaracao da procedure");

			if (totalParametro > 0) {
				msg("tem parametro");

				procedureAtual.setGeralB(totalParametro + "");

				msg("setado total de parametros" + totalParametro);
				msg("procedure (" + procedureAtual.getNome() + ") parametros: " + totalParametro);

				for (int i = 0; i < totalParametro; i++) {
					Simbolo p = (Simbolo) pilhaParametros.pop();
					p.setGeralA(-(totalParametro - i) + "");

					msg(p.getNome() + " geralA: " + (-(totalParametro - i)));
				}
			}

			addInstrucao(areaInstrucoes.LC, "DSVS", "-", "-" + "");

			MH.IncluirAI(areaInstrucoes, 19, 0, 0);
			totalParametro = 0;
			pilhaProcedure.push(areaInstrucoes.LC - 1);
			pilhaProcedure.push(totalParametro);

			System.out.println("add MH: " + areaInstrucoes.LC + ", DSVS, 0, 0");
			System.out.println("add pilha procedure: " + (areaInstrucoes.LC - 1));
			System.out.println("add pilha procedure: " + totalParametro);

			break;
		case 110:
			int parametro = pilhaProcedure.pop();
			endereco = pilhaProcedure.pop();
			addInstrucao(areaInstrucoes.LC, "RETU", "-", (parametro + 1) + "");
			MH.IncluirAI(areaInstrucoes, 1, 0, (parametro + 1));

//				String[] b = instrucoes.get(endereco-1);
//				b[3] = AI.LC+"";
//				instrucoes.set(endereco-1, b);
//				maquinaVirtual.AlterarAI(AI, endereco, 0, AI.LC);
//				TS.exclui(nivelAtual);
//				nivelAtual--;
//				
//				System.out.println("regra: fim da procedure");
//				System.out.println("topo pilha procedure: " + par);
//				System.out.println("topo pilha procedure: " + endereco);
//				System.out.println("add MH: " + AI.LC + ", RETU" + ", - " + ", " + (par+1));
//				System.out.println("alterar MH: ");
//				System.out.println("exclui da TS: " + nivelAtual);
//				System.out.println("nivelAtual--: " + nivelAtual);

			break;
		case 111:
			tipoIdentificador = "PAR";
			temParametro = true;

			System.out.println("acao: antes da procedure");
			System.out.println("tipoIdentificador: PAR");
			System.out.println("tem parametros: true");

			break;
		case 112:
			nomeIdentificador = token.getNome();

			System.out.println("acao: identificador da instrucao");
			System.out.println("nm_ident: " + token.getNome());

			break;
		case 113:
			Simbolo simbolo113 = ts.buscar(nomeIdentificador);

//				if(simbolo113 != null && simbolo113.getCategoria().equals("ROTULO")) {
//					if(simbolo113.getNivel() != nivelAtual) {
//						throw new SemanticoExepition("Erro semantico: " + token.getNome() + " nao declarado");
//					}else {
//						simbolo113.setGeralA(areaInstrucoes.LC + "");
//					}
//				}
		case 114:
			Simbolo simboloVar = ts.buscar(token.getNome());

			msg("acao 114: atribuicao a esquerda");
			msg("busca na TS por " + token.getNome());

			if (simboloVar != null) {
				msg("item encontrado");

				if (!simboloVar.getCategoria().equals("VAR")) {
					msg("Erro semantico: " + token.getNome() + " nao é variavel");

					throw new SemanticoExepition("Erro semantico: " + nomeIdentificador + " não é uma variável");
				} else {
					n = nivelAtual - simboloVar.getNivel();

					geralA = Integer.parseInt(simboloVar.getGeralA());

					msg("nivel atual - nivel do simbolo: " + n);
					msg("geralA: " + geralA);
				}

			} else {
				msg("Simbolo " + nomeIdentificador + " n foi declarada");
				throw new SemanticoExepition("Variável " + nomeIdentificador + " não foi declarada");
			}

			break;
		case 115:
			addInstrucao(areaInstrucoes.LC, "ARMZ", n + "", geralA + "");
			MH.IncluirAI(areaInstrucoes, 4, n, geralA);

			msg("acao: apos expressao atribuicao");
			msg("add MH: ARMZ" + ", " + n + ", " + geralA);

			break;
		case 116:
			Simbolo simbolo116 = ts.buscar(token.getNome());

			if (simbolo116 != null) {
				if (simbolo116.getCategoria().equals("PROCEDURE")) {
					nomeProcedure = token.getNome();
				} else {
					msg("Erro semantico: " + token.getCategoria() + " não é do tipo PROCEDURE");
					throw new SemanticoExepition(
							"Erro semantico: " + token.getCategoria() + " não é do tipo PROCEDURE");
				}
			} else {
				msg("Erro semantico: " + token.getNome() + " nao foi declarado");
				throw new SemanticoExepition("Erro semantico: " + token.getNome() + " nao foi declarado");
			}
		default:
			break;

		}
	}

}
