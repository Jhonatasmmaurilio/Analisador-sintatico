package semantico;

import java.util.ArrayList;
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
	public static Simbolo simbolo;

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

	@SuppressWarnings("static-access")
	public void AnaliseSemantica(int acao, Token token, Token tokenAnterior) throws SemanticoExepition {
		msg("x = " + (acao - 77));
		msg("------------------------");
		msg("token atual: [" + token.getLexeme() + "]");
		msg("token anterior: [" + tokenAnterior.getLexeme() + "]");
		msg("------------------------");

		switch (acao - 77) {

		case 100:
			msg("acao: 100 - reconhece nome PROGRAMA");
			msg("inicializa programa");

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

			break;
		case 101:
			msg("ACAO: 101 - fim do programa (FEITA)");
			msg("add MH: PARA");

			MH.IncluirAI(areaInstrucoes, 26, 0, 0);
			addInstrucao(areaInstrucoes.LC, "PARA", "-", "-");

			System.out.println(instrucoes);
			break;
		case 102:
			msg("ACAO: 102 - apos declaracao de variavel (FEITA)");
			msg("add MH: AMEN");
			msg("tabela instruao");
			msg(instrucoes.toString());
			msg("deslocamento: " + deslocamento);
			msg("totalVariavel: " + totalVariavel);

			deslocamento = 3;

			MH.IncluirAI(areaInstrucoes, 24, 0, (deslocamento + totalVariavel));
			addInstrucao(areaInstrucoes.LC, "AMEN", "-", (deslocamento + totalVariavel) + "");

			break;
		case 103:
			msg("ACAO: 103 - seta identificador para rotulo (FEITA)");
			msg("tipoIdentificadorRotulo = ROTULO");

			tipoIdentificador = "ROTULO";

			break;
		case 104:
			msg("ACAO: 104 - nome de rotulo, variavel ou parametro (FEITA)");
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
					ts.inserir(tokenAnterior.getLexeme(), "VAR", nivelAtual, deslocamento + "", "-");
					totalVariavel++;
					deslocamento++;

					msg("[" + tokenAnterior.getLexeme() + "] inserido na TS");
					msg("totalVariaveis: " + totalVariavel);
					msg("deslocamento: " + deslocamento);
					System.out.println(instrucoes);
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
		case 105:
			msg("ACAO 105: reconhece nome CONSTANTE (FEITA)");
			
			identificador = ts.buscar(tokenAnterior.getLexeme());

			if (identificador == null) {
				msg("constante ainda não declarada");
				
				ts.inserir(tokenAnterior.getLexeme(), "CONSTANTE", nivelAtual, "-", "-");
				identificador = ts.buscar(tokenAnterior.getLexeme());
			} else {
				identificador = null;
				msg("Constatnte " + tokenAnterior.getLexeme() + " ja foi declarada");
				
				throw new SemanticoExepition("Erro semantico: Constante " + tokenAnterior.getLexeme() + " ja foi declarada");
			}

			break;
		case 106:
			identificador.setGeralA(tokenAnterior.getLexeme());

			msg("ACAO 106: reconhece valor da CONSTANTE (FEITA)");
			msg("constante: " + identificador.getNome());
			msg("identificador geralA: " + identificador.getGeralA());

			break;
		case 107:
			msg("acao 107: seta tipo de variavel (FEITA)");
			msg("tipoIdentificador = VAR");

			tipoIdentificador = "VAR";
			totalVariavel = 0;

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
		case 114:
			msg("ACAO 114: atribuicao a esquerda (FEITA)");

			Simbolo simboloVar = ts.buscar(tokenAnterior.getLexeme());

			if (simboloVar != null) {
				if (!simboloVar.getCategoria().equals("VAR")) {
					msg("Erro semantico: " + tokenAnterior.getLexeme() + " nao é variavel");

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
			msg("ACAO: Apos expressao atribuicao (FEITA)");
			msg("add MH: ARMZ" + ", " + n + ", " + geralA);

			MH.IncluirAI(areaInstrucoes, 4, n, geralA);
			addInstrucao(areaInstrucoes.LC, "ARMZ", n + "", geralA + "");
			System.out.println(instrucoes);

			break;
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
		case 120:
			msg("ACAO: Apos expressao IF (FEITA)");
			msg("add MH:[" + (areaInstrucoes.LC + 1) + " DVSF, -, ?]");

			pilhaIf.push(areaInstrucoes.LC);
			
			msg("pilhaIf" + pilhaIf);
			
			MH.IncluirAI(areaInstrucoes, 20, -1, -1);
			addInstrucao(areaInstrucoes.LC, "DVSF", "-", "?");
			
			System.out.println(instrucoes);

			break;
		case 121:
			msg("ACAO 121: Encontrou ELSE (FEITA)");
			msg("pilhaIf: " + pilhaIf);
			
			int posicao1 = pilhaIf.pop();
			
			msg("topo da pilaIf: " + posicao1);
			msg("busca instrucao: " + posicao1);

			msg(instrucoes.get(posicao1) + "");
			
			instrucoes.get(posicao1).setOp2(areaInstrucoes.LC + 1 + "");
			MH.AlterarAI(areaInstrucoes, posicao1, 0, areaInstrucoes.LC);
			
			System.out.println("completa DSVS gerada no #122");
			msg("seta desvio para instrucao: " + (areaInstrucoes.LC + 1));
			msg(instrucoes + "");
			
			break;
		case 122:
			msg("ACAO 122: Encontrou ELSE (FEITA)");
			msg("pilhaIf: " + pilhaIf);
			
			int posicao2 = pilhaIf.pop();

			msg("topo da pilhaIF: " + (posicao2));
			msg("busca pela instrucao: " + (posicao2));
			msg(instrucoes.get(posicao2 - 1) + "");
			
			instrucoes.get(posicao2 - 1).setOp2((areaInstrucoes.LC + 1) + "");
			
			msg("seta o valor ? do ELSE");
			msg(instrucoes.get(posicao2 - 1)+ "");
			
			MH.AlterarAI(areaInstrucoes, posicao2, 0, areaInstrucoes.LC + 1);
			
			pilhaIf.push(areaInstrucoes.LC);
			
			MH.IncluirAI(areaInstrucoes, 19, 0, 0);
			addInstrucao(areaInstrucoes.LC, "DSVS", "-", "?");
			
			msg("altera na MH");
			msg("resolve DSVF de #120");
			msg("add MH: [" + areaInstrucoes.LC + ", DSVF" + ", -, - ]");
			msg(instrucoes + "");
			break;
		case 123:
			msg("ACAO 123: WHILE antes da expressao (FEITA)");
			msg("insere na pilha while:" + areaInstrucoes.LC);
			
			pilhaWhile.push(areaInstrucoes.LC);

			msg("pilhaWhile: " + pilhaWhile);
			break;
		case 124:
			msg("ACAO: WHILE depois da expresao (FEITA)");
			
			addInstrucao(areaInstrucoes.LC,"DVSF","-","?");
			
			pilhaWhile.push(areaInstrucoes.LC);
			msg("add pilhaWhile: " + areaInstrucoes.LC);
			msg("pilhaWhile: " + pilhaWhile);
			
			MH.IncluirAI(areaInstrucoes, 20, 0, 0);
			msg("add MH: [" + areaInstrucoes.LC + ", DSVF, -, ?");

			break;
		case 125:
			System.out.println("ACAO 125: WHILE (FEITA)");
		
			msg("pilhaWhile: " + pilhaWhile);
			int posicao3 = pilhaWhile.pop();
			msg("topo pilha: " + posicao3);
			
			MH.AlterarAI(areaInstrucoes,posicao3 , 0, areaInstrucoes.LC+1);
			msg("altera MH");
			
			msg("intrução na posição: " + posicao3);
			msg(instrucoes.get(posicao3) + "");
			
			instrucoes.get(posicao3).setOp2((areaInstrucoes.LC + 1) + "");
			
			msg(instrucoes.get(posicao3) + "");
			
			int aux = pilhaWhile.pop();
			
			msg("topo pilhaWhile: " + aux);
			msg("pilhaWhila: " + pilhaWhile);
			
			addInstrucao(areaInstrucoes.LC,"DSVS", "-",""+(aux));
			MH.IncluirAI(areaInstrucoes, 19, 0,aux);
			
			msg("add MH: [" + areaInstrucoes.LC + ", DSVS, - , " + aux);
			break;
		case 128:
			msg("ACAO 128: READLN (FEITA)");
			msg("contexto = readln");

			contexto = "READLN";

			break;
		case 129:
			msg("ACAO 129: identificador de variavel (FEITA)");

			int nivelA = 0;
			int geralA = 0;
			int diferencaNivel = 0;
			int deslocamento = 0;

			simbolo = ts.buscar(tokenAnterior.getLexeme());

			if (simbolo != null) {
				if (contexto.equals("READLN")) {
					msg("contexto igual a READLN");

					if (simbolo.getCategoria().equals("VAR")) {
						MH.IncluirAI(areaInstrucoes, 21, 0, 0);
						addInstrucao(areaInstrucoes.LC, "LEIT", "-", "-");

						nivelA = simbolo.getNivel() - nivelAtual;
						geralA = Integer.parseInt(simbolo.getGeralA());

						MH.IncluirAI(areaInstrucoes, 4, nivel, geralA);
						addInstrucao(areaInstrucoes.LC, "ARMZ", nivel + "", geralA + "");

						msg("adiciona na MH ");
						msg("inclui LEIT na MH: " + 21);
						msg("diferenca de nivel: " + nivelA);
						msg("endereco relativo: " + geralA);
						msg("inclui instrucao ARMZ na MH");
						System.out.println(instrucoes);
					} else {
						msg("Simbolo [" + tokenAnterior.getLexeme() + "] não é do tipo variável");

						throw new SemanticoExepition(tokenAnterior.getLexeme() + " não é do tipo variável");
					}
				} else if (contexto.equals("EXP")) {
					msg("contexto é igual a EXP");

					String categoria = simbolo.getCategoria();

					msg(tokenAnterior.getLexeme() + " encontrado, tipo:" + categoria);

					if (categoria.equals("PROCEDURE") || categoria.equals("ROTULO")) {
						msg("categoria igual a variavel");

						throw new SemanticoExepition(
								"Erro Semântico: " + tokenAnterior.getLexeme() + " não é variável");
					} else if (categoria.equals("CONSTANTE")) {
						geralA = Integer.parseInt(simbolo.getGeralA());

						MH.IncluirAI(areaInstrucoes, 3, 0, geralA);
						addInstrucao(areaInstrucoes.LC, "CRCT", "-", geralA + "");

						msg(tokenAnterior.getLexeme() + " é uam CONST");
						msg("index geralA: " + geralA);
					} else {
						diferencaNivel = nivelAtual - simbolo.getNivel();
						deslocamento = Integer.parseInt(simbolo.getGeralA());

						MH.IncluirAI(areaInstrucoes, 2, diferencaNivel, deslocamento);
						addInstrucao(areaInstrucoes.LC, "CRVL", diferencaNivel + "", deslocamento + "");

						msg("carrega valor na pilha");
						msg("index geralA:" + deslocamento);
						msg("add MH: CRVL");
					}
				}
			} else {
				msg("Token [" + tokenAnterior.getLexeme() + "] não foi encontrado");

				throw new SemanticoExepition(tokenAnterior.getLexeme() + " não é uma variável");
			}

			break;
		case 130:
			msg("ACAO 130: WRITELN (FEITA)");
			msg("add instrucao: IMPRL");
			msg("add [" + tokenAnterior.getLexeme() + "] na area de literais");

			MH.IncluirAL(areaLiterais, tokenAnterior.getLexeme());
			MH.IncluirAI(areaInstrucoes, 23, 0, areaLiterais.LIT - 1);
			addInstrucao(areaInstrucoes.LC, "IMPL", "0", (areaLiterais.LIT) + "");
			System.out.println(instrucoes);

			break;
		case 131:
			msg("ACAO: Gera intrucao IMPR (FEITA)");
			msg("add MH: IMPR");

			MH.IncluirAI(areaInstrucoes, 22, 0, 0);
			addInstrucao(areaInstrucoes.LC, "IMPR", "-", "-");
			System.out.println(instrucoes);

			break;
		case 137:
			msg("ACAO: apos variavel FOR (FEITA)");
			
			Simbolo s1 = ts.buscar(tokenAnterior.getLexeme());
			
			if(s1 == null || !s1.getCategoria().equals("VAR")) {
				msg("Não encontrada ou não e do tipo VAR");
				
				throw new SemanticoExepition(tokenAnterior.getLexeme() + " não foi declarado ou não é nome de variável");
			}else {
				n = s1.getNivel();
				
				System.out.println("n = " + n);
			}
			
			break;
		case 143:
			msg("ACAO: Gerar CMMA (FEITA)");
			msg("add MH: CMMA");

			MH.IncluirAI(areaInstrucoes, 14, 0, 0);
			addInstrucao(areaInstrucoes.LC, "CMMA", "-", "-");
			System.out.println(instrucoes);

			break;
		case 148:
			msg("ACAO: Gera instrucao SOMA (FEITA)");
			msg("add MH: SOMA");

			MH.IncluirAI(areaInstrucoes, 5, 0, 0);
			addInstrucao(areaInstrucoes.LC, "SOMA", "-", "-");
			System.out.println(instrucoes);

			break;
		case 149:
			msg("ACAO 149: Gera instucao SUBI (FEITA)");
			msg("add MH: SUBT");

			MH.IncluirAI(areaInstrucoes, 6, 0, 0);
			addInstrucao(areaInstrucoes.LC, "SUBT", "-", "-");
			System.out.println(instrucoes);

			break;
		case 151:
			msg("ACAO: multiplicacao (FEITA)");
			msg("add MH: MULT");

			MH.IncluirAI(areaInstrucoes, 7, 0, 0);
			addInstrucao(areaInstrucoes.LC, "MULT", "-", "-");
			System.out.println(instrucoes);

			break;
		case 152:
			System.out.println("ACAO 152: Divisao (FEITA)");
			System.out.println("add MH: DIVD");

			MH.IncluirAI(areaInstrucoes, 8, 0, 0);
			addInstrucao(areaInstrucoes.LC, "DIVD", "-", "-");
			System.out.println(instrucoes);

			break;
		case 154:
			msg("ACAO 154: Gera instucao CRCT (FEITA)");

			int valor = Integer.parseInt(tokenAnterior.getLexeme());
			
			msg("add MH: [" + areaInstrucoes.LC + ", CRCT, -, " + valor + "]");

			MH.IncluirAI(areaInstrucoes, 3, 0, valor);
			addInstrucao(areaInstrucoes.LC, "CRCT", "-", valor + "");
			System.out.println(instrucoes);

			break;
		case 156:
			msg("ACAO 156: variavel (FEITA)");
			msg("seta contexto como EXP");

			contexto = "EXP";

			break;
		default:
			break;
		}
	}
}
