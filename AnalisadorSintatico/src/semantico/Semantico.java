package semantico;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import TabelaSimbolo.Simbolo;
import TabelaSimbolo.TabelaSimbolos;
import maquinahipotetica.AreaInstrucoes;
import maquinahipotetica.AreaLiterais;
import maquinahipotetica.Instrucao;
import maquinahipotetica.MaquinaHipotetica;
import token.Token;

public class Semantico {
	public static TabelaSimbolos ts;
	public static MaquinaHipotetica MH;
	public static ArrayList<Instrucao> instrucoes;
	public static AreaInstrucoes areaInstrucoes;
	public static AreaLiterais areaLiterais;

	public static Stack<Integer> pilhaIf;
	public static Stack<Integer> pilhaWhile;
	public static Stack<Integer> pilhaRepeat;
	public static Stack<Integer> pilhaProcedure;
	public static Stack<Integer> pilhaCase;
	public static Stack<Integer> pilhaFor;
	public static Stack<Simbolo> pilhaParametros;

	public static Simbolo identificador;
	public static Simbolo procedureAtual;
	public static Simbolo index;

	public static String tipoIdentificador;
	public static String nIdentificador;
	public static String contexto;
	public static String token;
	public static String nomeIdentificador;
	public static String nomeProcedure;

	public static int endereco;
	public static int nivelAtual;
	public static int totalVariavel;
	public static int deslocamento;
	public static int n;// nao sei pra que serve
	public static int nivel;
	public static int totalParametro;
	public static int enderecoA;
	public static int ptLivre;
	public static int escopo;
	public static int lc;
	public static int lit;
	public static int geralA;

	public static boolean temParametro;

	public void msg(String msg) {
		System.out.println(msg);
	}

	public void addInstrucao(int index, String ins, String op1, String op2) {
		instrucoes.add(new Instrucao(index + "", ins, op1, op2));
	}

//	public void mostraInst() {
//		Iterator iter = instrucoes.iterator();
//		
//		while (iter.hasNext()) {
//			System.out.println(iter.getInstrucao());
//		}
//	}

	@SuppressWarnings("static-access")
	public void AnaliseSemantica(int acao, Token token, Token tokenAnterior) throws SemanticoExepition {
		msg("x = " + (acao - 77));
		msg("-> token atual: [" + token.getLexeme() + "]");
		msg("-> token anterior: [" + tokenAnterior.getLexeme() + "]");

		switch (acao - 77) {

		case 100:
			MH = new MaquinaHipotetica();
			instrucoes = new ArrayList<Instrucao>();

			ts = new TabelaSimbolos();
			ts.InicializarTabela(1000);

			areaInstrucoes = new AreaInstrucoes();
			areaLiterais = new AreaLiterais();

			MH.InicializaAI(areaInstrucoes);
			MH.InicializaAL(areaLiterais);

			pilhaIf = new Stack();
			pilhaWhile = new Stack();
			pilhaRepeat = new Stack();
			pilhaProcedure = new Stack();
			pilhaCase = new Stack();
			pilhaFor = new Stack();
			pilhaParametros = new Stack();

			nivelAtual = 0;
			ptLivre = 1;
			escopo = 1;
			totalVariavel = 0;
			deslocamento = 3;
			lc = 1;
			lit = 1;

			msg("acao: 100 - reconhece nome PROGRAMA");
			msg("inicializa programa");

			break;
		case 101:
			MH.IncluirAI(areaInstrucoes, 26, 0, 0);
			addInstrucao(areaInstrucoes.LC, "PARA", "-", "-");

			msg("acao: 101 - encontra programa");
			msg("add MH: PARA");

			break;
		case 102:
			MH.IncluirAI(areaInstrucoes, 24, 0, (deslocamento + totalVariavel));
			addInstrucao(areaInstrucoes.LC, "AMEN", "-", (deslocamento + totalVariavel) + "");

			msg("acao: 102 - apos declaracao de variavel");
			msg("add MH: AMEN");
			msg("tabela instruao");
			msg(instrucoes.toString());
			msg("deslocamento: " + deslocamento);
			msg("totalVariavel: " + totalVariavel);

			break;
//		case 103:
//			tipoIdentificador = "ROTULO";
//
//			msg("acao: 103 - identificador rotulo");
//
//			break;
		case 104:
			msg("acao: 104 - nome de rotulo, variavel ou parametro");
			msg("token atual: [" + tokenAnterior.getLexeme() + "]");
			Simbolo s = ts.buscar(tokenAnterior.getLexeme());

			if (tipoIdentificador.equals("ROTULO")) {
				msg("token (" + tokenAnterior.getLexeme() + ") igual a rotulo");

				if (s == null) {
					ts.inserir(tokenAnterior.getLexeme(), "ROTULO", nivelAtual, "0", "-");

					msg("[" + tokenAnterior + "] nao encontrado na TS");
					msg("[" + tokenAnterior + "] inserido na TS");
				} else {
					msg("[" + tokenAnterior + "] ja esta na TS");

					if (s.getNivel() != nivelAtual) {
						ts.inserir(tokenAnterior.getLexeme(), "ROTULO", nivelAtual, "0", "-");

						msg("[" + tokenAnterior + "] inserido na TS com nivel: " + nivelAtual);
					} else {
						msg("Erro semantico: " + "O Rótulo (" + tokenAnterior.getLexeme() + ") ja foi declarado");
						throw new SemanticoExepition(
								"Erro semantico: O Rótulo (" + tokenAnterior.getLexeme() + ") ja foi declarado");
					}
				}
			}

			if (tipoIdentificador.equals("VAR")) {
				msg("tipoIdentificador é igual a VAR");

				if (s == null) {
					ts.inserir(tokenAnterior.getLexeme(), "VAR", nivelAtual, "0", "-");
					totalVariavel++;

					msg("[" + tokenAnterior.getLexeme() + "] inserido na TS");
					msg("totalVariaveis: " + totalVariavel);
				} else {
					if (s.getNivel() != nivelAtual) {
						ts.inserir(tokenAnterior.getLexeme(), "VAR", nivelAtual, "0", "-");
						totalVariavel++;

						msg("[" + tokenAnterior.getLexeme() + "] encontrado na TS, inserido na TS com nivel: "
								+ nivelAtual);
						msg("totalVariavel: " + totalVariavel);
					} else {
						msg("Erro semantico: A variável (" + tokenAnterior.getLexeme() + ") já foi declarada");
						throw new SemanticoExepition(
								"Erro semantico: A variável (" + tokenAnterior.getLexeme() + ") já foi declarada");
					}
				}
			}

			if (tipoIdentificador.equals("PARAMETRO")) {
				msg("tipoIdentificador igual a PARAMETRO");

				if (s == null) {
					ts.inserir(tokenAnterior.getLexeme(), "PARAMETRO", nivelAtual, "-", "-");

					msg("[" + tokenAnterior.getLexeme() + "] n encontrado na TS");
					msg("[" + tokenAnterior.getLexeme() + "] inserido na TS");
				} else {
					if (s.getNivel() != nivelAtual) {
						Simbolo parametro = new Simbolo(tokenAnterior.getLexeme(), "PARAMETRO", nivelAtual, "-", "-");

						ts.inserir(tokenAnterior.getLexeme(), "PARAMETRO", nivelAtual, "-", "-");
						pilhaParametros.push(parametro);
						totalParametro++;

						msg("[" + tokenAnterior.getLexeme() + "] encontrado na TS, inserido na tS com nivel: "
								+ nivelAtual);
						msg("add pilhaParametro: [" + tokenAnterior.getLexeme());
						msg("totalParametro: " + totalParametro);
					} else {
						msg("Erro semantico: Parametro (" + tokenAnterior.getLexeme() + ") já declarado");
						throw new SemanticoExepition(
								"Erro semantico: Parametro (" + tokenAnterior.getLexeme() + ") já declarado");
					}
				}
			}

			break;
//		case 105:
//			msg("acao 105: reconhece nome CONSTANTE");
//
//			s = ts.buscar(token.getLexeme());
//
//			if (s == null) {
//				ts.inserir(token.getLexeme(), "CONSTANTE", nivelAtual, "-", "-");
//				identificador = token;
//			} else {
//				msg("Constatnte " + token.getLexeme() + " ja declarada");
//				throw new SemanticoExepition("Erro semantico: Constante " + token.getLexeme() + " ja declarada");
//			}
//
//			break;
//		case 106:
//			identificador.setGeralA(token.getLexeme());
//
//			msg("acao 106: reconhece valor da onstante");
//			msg("identificador geralA: " + identificador.getGeralA());
//
//			break;
		case 107:
			tipoIdentificador = "VAR";
			totalVariavel = 0;

			msg("acao 107: seta tipo de variavel");
			msg("tipoIdentificador = VAR");

			break;
//		case 108:
//			procedureAtual = new Simbolo(token.getLexeme(), "PROCEDURE", nivelAtual, (areaInstrucoes.LC + 1) + "", "0");
//			ts.inserir(token.getLexeme(), "PROCEDURE", nivelAtual, (areaInstrucoes.LC + 1) + "", "0");
//			nivelAtual++;
//			totalVariavel++;
//			totalParametro = 0;
////			temParametro = false;
//
//			msg("acao: apos nome da procedure");
//			msg("insere novo simbolo");
//			msg("nivelAtual: " + nivelAtual);
//			msg("deslocamento: " + deslocamento);
//			msg("totalVariavel: " + totalVariavel);
//			msg("totalParametro: 0");
//
//			break;
//		case 109:
//			msg("acao 109: apos declaracao da procedure");
//
//			if (totalParametro > 0) {
//				msg("tem parametro");
//
//				procedureAtual.setGeralB(totalParametro + "");
//
//				msg("setado total de parametros" + totalParametro);
//				msg("procedure (" + procedureAtual.getNome() + ") parametros: " + totalParametro);
//
//				for (int i = 0; i < totalParametro; i++) {
//					Simbolo p = (Simbolo) pilhaParametros.pop();
//					p.setGeralA(-(totalParametro - i) + "");
//
//					msg(p.getNome() + " geralA: " + (-(totalParametro - i)));
//				}
//			}
//
//			addInstrucao(areaInstrucoes.LC, "DSVS", "-", "-" + "");
//
//			MH.IncluirAI(areaInstrucoes, 19, 0, 0);
//			totalParametro = 0;
//			pilhaProcedure.push(areaInstrucoes.LC - 1);
//			pilhaProcedure.push(totalParametro);
//
//			System.out.println("add MH: " + areaInstrucoes.LC + ", DSVS, 0, 0");
//			System.out.println("add pilha procedure: " + (areaInstrucoes.LC - 1));
//			System.out.println("add pilha procedure: " + totalParametro);
//
//			break;
//		case 110:
//			int parametro = pilhaProcedure.pop();
//			endereco = pilhaProcedure.pop();
//			addInstrucao(areaInstrucoes.LC, "RETU", "-", (parametro + 1) + "");
//			MH.IncluirAI(areaInstrucoes, 1, 0, (parametro + 1));
//
////				String[] b = instrucoes.get(endereco-1);
////				b[3] = AI.LC+"";
////				instrucoes.set(endereco-1, b);
////				maquinaVirtual.AlterarAI(AI, endereco, 0, AI.LC);
////				TS.exclui(nivelAtual);
////				nivelAtual--;
////				
////				System.out.println("regra: fim da procedure");
////				System.out.println("topo pilha procedure: " + par);
////				System.out.println("topo pilha procedure: " + endereco);
////				System.out.println("add MH: " + AI.LC + ", RETU" + ", - " + ", " + (par+1));
////				System.out.println("alterar MH: ");
////				System.out.println("exclui da TS: " + nivelAtual);
////				System.out.println("nivelAtual--: " + nivelAtual);
//
//			break;
//		case 111:
//			tipoIdentificador = "PAR";
//			temParametro = true;
//
//			System.out.println("acao: antes da procedure");
//			System.out.println("tipoIdentificador: PAR");
//			System.out.println("tem parametros: true");
//
//			break;
//		case 112:
//			nomeIdentificador = token.getLexeme();
//
//			System.out.println("acao: identificador da instrucao");
//			System.out.println("nm_ident: " + token.getLexeme());
//
//			break;
//		case 113:
//			Simbolo simbolo113 = ts.buscar(nomeIdentificador);
//
////				if(simbolo113 != null && simbolo113.getCategoria().equals("ROTULO")) {
////					if(simbolo113.getNivel() != nivelAtual) {
////						throw new SemanticoExepition("Erro semantico: " + token.getLexeme() + " nao declarado");
////					}else {
////						simbolo113.setGeralA(areaInstrucoes.LC + "");
////					}
////				}
//		case 114:
//			Simbolo simboloVar = ts.buscar(token.getLexeme());
//
//			msg("acao 114: atribuicao a esquerda");
//			msg("busca na TS por " + token.getLexeme());
//
//			if (simboloVar != null) {
//				msg("item encontrado");
//
//				if (!simboloVar.getCategoria().equals("VAR")) {
//					msg("Erro semantico: " + token.getLexeme() + " nao é variavel");
//
//					throw new SemanticoExepition("Erro semantico: " + nomeIdentificador + " não é uma variável");
//				} else {
//					n = nivelAtual - simboloVar.getNivel();
//
//					geralA = Integer.parseInt(simboloVar.getGeralA());
//
//					msg("nivel atual - nivel do simbolo: " + n);
//					msg("geralA: " + geralA);
//				}
//
//			} else {
//				msg("Simbolo " + nomeIdentificador + " n foi declarada");
//				throw new SemanticoExepition("Variável " + nomeIdentificador + " não foi declarada");
//			}
//
//			break;
//		case 115:
//			addInstrucao(areaInstrucoes.LC, "ARMZ", n + "", geralA + "");
//			MH.IncluirAI(areaInstrucoes, 4, n, geralA);
//
//			msg("acao: apos expressao atribuicao");
//			msg("add MH: ARMZ" + ", " + n + ", " + geralA);
//
//			break;
//		case 116:
//			Simbolo simbolo116 = ts.buscar(token.getLexeme());
//
//			if (simbolo116 != null) {
//				if (simbolo116.getCategoria().equals("PROCEDURE")) {
//					nomeProcedure = token.getLexeme();
//				} else {
//					msg("Erro semantico: " + token.getCategoria() + " não é do tipo PROCEDURE");
//					throw new SemanticoExepition(
//							"Erro semantico: " + token.getCategoria() + " não é do tipo PROCEDURE");
//				}
//			} else {
//				msg("Erro semantico: " + token.getLexeme() + " nao foi declarado");
//				throw new SemanticoExepition("Erro semantico: " + token.getLexeme() + " nao foi declarado");
//			}
		case 130:
			MH.IncluirAL(areaLiterais, tokenAnterior.getLexeme());			
			MH.IncluirAI(areaInstrucoes, 23, 0, areaLiterais.LIT-1);
			addInstrucao(areaInstrucoes.LC,"IMPL","0",(areaLiterais.LIT)+"");

			msg("acao 130: WRITELN");
			msg("add instrucao: IMPRL");
			msg("add [" + tokenAnterior.getLexeme() + "] na area de literais");
			
			break;
		default:
			break;

		}
	}

}
