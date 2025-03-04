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

	public static Stack<Integer> pilhaIf, pilhaWhile, pilhaRepeat, pilhaProcedure, pilhaCase, pilhaFor;
	public static Stack<Simbolo>pilhaParametros;

	public static Simbolo identificador, procedureAtual, simbolo, simbolo137, simbolo139, simbolo140;

	public static String tipoIdentificador, nIdentificador, contexto, token, nomeIdentificador, nomeProcedure;

	public static int endereco,  nivelAtual, totalVariavel,  deslocamento, nivel, totalParametro, enderecoA, escopo;
	public static int geralA, geralA137, nivel137, nivel114, nivel140, geralA140, posicao139;

	public static boolean temParametro;

	public void msg(String msg) {
		System.out.println(msg);
	}

	public void addInstrucao(int index, String ins, String op1, String op2) {
		instrucoes.add(new Instrucao(index + "", ins, op1, op2));
	}

	@SuppressWarnings("static-access")
	public void AnaliseSemantica(int acao, Token token, Token tokenAnterior, Token tokenAntPenultimo) throws SemanticoExepition {
		msg("x = " + (acao - 77));
		msg(">>>token atual: [" + token.getLexeme() + "]");
		msg(">>>token anterior: [" + tokenAnterior.getLexeme() + "]\n");

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
//			ptLivre = 1;
			escopo = 1;
			totalVariavel = 0;
			deslocamento = 3;
//			lc = 1;
//			lit = 1;

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
			msg("deslocamento: " + deslocamento);
			msg("totalVariavel: " + totalVariavel);

			deslocamento = 3;

			MH.IncluirAI(areaInstrucoes, 24, 0, (deslocamento + totalVariavel));
			addInstrucao(areaInstrucoes.LC, "AMEN", "-", (deslocamento + totalVariavel) + "");
			msg("add MH: [" + areaInstrucoes.LC + ", AMEN, - ," + (deslocamento + totalVariavel) + "");
			msg(instrucoes + "");
			
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
						msg("Erro semantico: " + "O R�tulo (" + tokenAnterior.getLexeme() + ") ja foi declarado");
						throw new SemanticoExepition(
								"Erro semantico: O R�tulo (" + tokenAnterior.getLexeme() + ") ja foi declarado");
					}
				}
			}

			if (tipoIdentificador.equals("VAR")) {
				msg("tipoIdentificador � igual a VAR");

				if (s == null) {
					ts.inserir(tokenAnterior.getLexeme(), "VAR", nivelAtual, deslocamento + "", "-");
					totalVariavel++;
					deslocamento++;

					msg("[" + tokenAnterior.getLexeme() + "] inserido na TS");
					msg("totalVariaveis: " + totalVariavel);
					msg("deslocamento: " + deslocamento);
				} else {
					if (s.getNivel() != nivelAtual) {
						ts.inserir(tokenAnterior.getLexeme(), "VAR", nivelAtual, "0", "-");
						totalVariavel++;

						msg("[" + tokenAnterior.getLexeme() + "] encontrado na TS, inserido na TS com nivel: "
								+ nivelAtual);
						msg("totalVariavel: " + totalVariavel);
					} else {
						msg("Erro semantico: A vari�vel (" + tokenAnterior.getLexeme() + ") j� foi declarada");
						throw new SemanticoExepition(
								"Erro semantico: A vari�vel (" + tokenAnterior.getLexeme() + ") j� foi declarada");
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
						msg("Erro semantico: Parametro (" + tokenAnterior.getLexeme() + ") j� declarado");
						throw new SemanticoExepition(
								"Erro semantico: Parametro (" + tokenAnterior.getLexeme() + ") j� declarado");
					}
				}
			}

			break;
		case 105:
			msg("ACAO 105: reconhece nome CONSTANTE (FEITA)");

			identificador = ts.buscar(tokenAnterior.getLexeme());

			if (identificador == null) {
				msg("constante ainda n�o declarada");

				ts.inserir(tokenAnterior.getLexeme(), "CONSTANTE", nivelAtual, "-", "-");
				identificador = ts.buscar(tokenAnterior.getLexeme());
			} else {
				identificador = null;
				msg("Constatnte " + tokenAnterior.getLexeme() + " ja foi declarada");

				throw new SemanticoExepition(
						"Erro semantico: Constante " + tokenAnterior.getLexeme() + " ja foi declarada");
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
					msg("Erro semantico: " + tokenAnterior.getLexeme() + " nao � variavel");

					throw new SemanticoExepition("Erro semantico: " + nomeIdentificador + " n�o � uma vari�vel");
				} else {
					nivel114 = nivelAtual - simboloVar.getNivel();

					geralA = Integer.parseInt(simboloVar.getGeralA());

					msg("nivel atual - nivel do simbolo: " + nivel114);
					msg("geralA: " + geralA);
				}

			} else {
				msg("Simbolo " + nomeIdentificador + " n foi declarada");
				throw new SemanticoExepition("Vari�vel " + nomeIdentificador + " n�o foi declarada");
			}

			break;
		case 115:
			msg("ACAO: Apos expressao atribuicao (FEITA)");
			msg("add MH: ARMZ" + ", " + nivel114 + ", " + geralA);

			MH.IncluirAI(areaInstrucoes, 4, nivel114, geralA);
			addInstrucao(areaInstrucoes.LC, "ARMZ", nivel114 + "", geralA + "");
			System.out.println(instrucoes);

			break;
//		case 116:
//			Simbolo simbolo116 = ts.buscar(token.getLexeme());
//
//			if (simbolo116 != null) {
//				if (simbolo116.getCategoria().equals("PROCEDURE")) {
//					nomeProcedure = token.getLexeme();
//				} else {
//					msg("Erro semantico: " + token.getCategoria() + " n�o � do tipo PROCEDURE");
//					throw new SemanticoExepition(
//							"Erro semantico: " + token.getCategoria() + " n�o � do tipo PROCEDURE");
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
			msg(instrucoes.get(posicao2 - 1) + "");

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

			addInstrucao(areaInstrucoes.LC, "DVSF", "-", "?");

			pilhaWhile.push(areaInstrucoes.LC);
			msg("add pilhaWhile: " + areaInstrucoes.LC);
			msg("pilhaWhile: " + pilhaWhile);

			MH.IncluirAI(areaInstrucoes, 20, 0, 0);
			msg("add MH: [" + areaInstrucoes.LC + ", DSVF, -, ?");

			break;
		case 125:
			msg("ACAO 125: WHILE (FEITA)");

			msg("pilhaWhile: " + pilhaWhile);
			int posicao3 = pilhaWhile.pop();
			msg("topo pilha: " + posicao3);

			MH.AlterarAI(areaInstrucoes, posicao3, 0, areaInstrucoes.LC + 1);
			msg("altera MH");

			msg("intru��o na posi��o: " + posicao3);
			msg(instrucoes.get(posicao3) + "");

			instrucoes.get(posicao3).setOp2((areaInstrucoes.LC + 1) + "");

			msg(instrucoes.get(posicao3) + "");

			int aux = pilhaWhile.pop();

			msg("topo pilhaWhile: " + aux);
			msg("pilhaWhila: " + pilhaWhile);

			addInstrucao(areaInstrucoes.LC, "DSVS", "-", "" + (aux));
			MH.IncluirAI(areaInstrucoes, 19, 0, aux);

			msg("add MH: [" + areaInstrucoes.LC + ", DSVS, - , " + aux);
			break;
		case 126:
			msg("ACAO 126:  Comando REPEAT � in�cio (FEITA)");
			pilhaRepeat.push(areaInstrucoes.LC);
			msg("pilhaRepeat: " + pilhaRepeat);
			
			break;
		case 127:
			msg("ACAO 127:  Comando REPEAT � fim (FEITA)");
			int repeat126 = pilhaRepeat.pop() + 1;
			
			MH.IncluirAI(areaInstrucoes, 20, 0, repeat126);
			addInstrucao(areaInstrucoes.LC, "DVSF", "-", repeat126 + "");
			msg(instrucoes + "");
			
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
						msg(instrucoes + "");
					} else {
						msg("Simbolo [" + tokenAnterior.getLexeme() + "] n�o � do tipo vari�vel");

						throw new SemanticoExepition(tokenAnterior.getLexeme() + " n�o � do tipo vari�vel");
					}
				} else if (contexto.equals("EXP")) {
					msg("contexto � igual a EXP");

					String categoria = simbolo.getCategoria();

					msg(tokenAnterior.getLexeme() + " encontrado, tipo:" + categoria);

					if (categoria.equals("PROCEDURE") || categoria.equals("ROTULO")) {
						msg("categoria igual a variavel");

						throw new SemanticoExepition(
								"Erro Sem�ntico: " + tokenAnterior.getLexeme() + " n�o � vari�vel");
					} else if (categoria.equals("CONSTANTE")) {
						geralA = Integer.parseInt(simbolo.getGeralA());

						MH.IncluirAI(areaInstrucoes, 3, 0, geralA);
						addInstrucao(areaInstrucoes.LC, "CRCT", "-", geralA + "");

						msg(tokenAnterior.getLexeme() + " � uam CONST");
						msg("index geralA: " + geralA);
					} else {
						diferencaNivel = nivelAtual - simbolo.getNivel();
						deslocamento = Integer.parseInt(simbolo.getGeralA());

						MH.IncluirAI(areaInstrucoes, 2, diferencaNivel, deslocamento);
						addInstrucao(areaInstrucoes.LC, "CRVL", diferencaNivel + "", deslocamento + "");

						msg("carrega valor na pilha");
						msg("index geralA:" + deslocamento);
						msg("add MH: CRVL");
						
						msg(instrucoes + "");
					}
				}
			} else {
				msg("Token [" + tokenAnterior.getLexeme() + "] n�o foi encontrado");

				throw new SemanticoExepition(tokenAnterior.getLexeme() + " n�o � uma vari�vel");
			}

			break;
		case 130:
			msg("ACAO 130: WRITELN (FEITA)");
			msg("add instrucao: IMPRL");
			msg("add [" + tokenAnterior.getLexeme() + "] na area de literais");

			MH.IncluirAL(areaLiterais, tokenAnterior.getLexeme());
			MH.IncluirAI(areaInstrucoes, 23, 0, areaLiterais.LIT - 1);
			addInstrucao(areaInstrucoes.LC, "IMPL", "-", (areaLiterais.LIT - 1) + "");
			msg(instrucoes + "");

			break;
		case 131:
			msg("ACAO: Gera intrucao IMPR (FEITA)");
			msg("add MH: IMPR");

			MH.IncluirAI(areaInstrucoes, 22, 0, 0);
			addInstrucao(areaInstrucoes.LC, "IMPR", "-", "-");
			System.out.println(instrucoes);

			break;
		case 132:
			msg("ACAO 132: : Ap�s palavra reservada CASE (FEITA)");
			msg("inicio do case");
			
			pilhaCase.push(areaInstrucoes.LC);
			msg("pilhaCase: " + pilhaCase);
			
			break;
		case 133:
			msg("ACAO 133:  Ap�s comando CASE (FEITA)");
			
			msg("pilhaCase: " + pilhaCase);
			int aux133 = pilhaCase.pop() - 1;
			
			msg(instrucoes.get(aux133) + "");
			MH.AlterarAI(areaInstrucoes, aux133, 0, areaInstrucoes.LC);
			instrucoes.get(aux133).setOp2(areaInstrucoes.LC + "");
			msg(instrucoes.get(aux133) + "");
			
			MH.IncluirAI(areaInstrucoes, 24, 0, -1);
			addInstrucao(areaInstrucoes.LC,"AMEN", "-","-1");
			msg("add MH: [" + areaInstrucoes.LC + ", AMEN, - , -1");
			
			break;
		case 134:
			msg("ACAO 134:  Ramo do CASE ap�s inteiro, �ltimo da lista (FEITA)");
			
			MH.IncluirAI(areaInstrucoes, 28, 0, 0);
			addInstrucao(areaInstrucoes.LC, "COPI", "-", "-");
			msg("gera instru��o COPI");

			MH.IncluirAI(areaInstrucoes, 3, 0, Integer.parseInt(tokenAntPenultimo.getLexeme()));
			addInstrucao(areaInstrucoes.LC, "CRCT", "-", tokenAntPenultimo.getLexeme() + "");
			msg("gera instru��o CRCT");
			
			MH.IncluirAI(areaInstrucoes, 15, 0, 0);
			addInstrucao(areaInstrucoes.LC, "CMIG", "-", "-");
			msg("gera instru��o CMIG");
			
			if(!pilhaCase.isEmpty()) {
				msg("resolvendo desvio");
				int desvioCase134 = pilhaCase.pop()-1;
				
				msg(instrucoes.get(desvioCase134) + "");
				MH.AlterarAI(areaInstrucoes, desvioCase134 - 1, 0, areaInstrucoes.LC + 2);
				instrucoes.get(desvioCase134).setOp2((areaInstrucoes.LC + 2) + "");
				msg(instrucoes.get(desvioCase134) + "");
			}

			MH.IncluirAI(areaInstrucoes, 20, 0, 0);
			addInstrucao(areaInstrucoes.LC, "DSVF", "-", "?");
			msg("gera instru��o DSVF");
			
			pilhaCase.push(areaInstrucoes.LC);
			msg("pilhaCase: " + pilhaCase);
			
			msg(instrucoes + "");
			
			break;
		case 135:
			msg("ACAO 135:  Ap�s comando em CASE (FEITA)33333333333333333333333333333");
			
			msg("pilhaCase: " + pilhaCase);
			int desvioCase135 = pilhaCase.pop() - 1;
			
			msg(instrucoes.get(desvioCase135) + "");
			MH.AlterarAI(areaInstrucoes, desvioCase135, 0, (areaInstrucoes.LC + 1));
			instrucoes.get(desvioCase135).setOp2((areaInstrucoes.LC + 1) + "");
			msg(instrucoes.get(desvioCase135) + "");			
			
			MH.IncluirAI(areaInstrucoes, 20, 0, 0);
			addInstrucao(areaInstrucoes.LC, "DSVF", "-", "?");
			msg("gera instru��o DSVF");
			
			pilhaCase.push(areaInstrucoes.LC);
			msg("pilhaCase: " + pilhaCase);
			
			break;
		case 136:
			msg("ACAO 136: Ramo do CASE: ap�s inteiro (FEITA)11111111111111111111111111");
			
			MH.IncluirAI(areaInstrucoes, 28, 0, 0);
			addInstrucao(areaInstrucoes.LC, "COPI", "-", "-");
			msg("gera instru��o COPI");
			
			MH.IncluirAI(areaInstrucoes, 3, 0, Integer.parseInt(tokenAntPenultimo.getLexeme()));
			addInstrucao(areaInstrucoes.LC, "CRCT", "-", tokenAntPenultimo.getLexeme() + "");
			msg("gera instru��o CRCT");
			
			MH.IncluirAI(areaInstrucoes, 15, 0, 0);
			addInstrucao(areaInstrucoes.LC, "CMIG", "-", "-");
			msg("gera instru��o CMIG");
			
			MH.IncluirAI(areaInstrucoes,29, 0, 0);
			addInstrucao(areaInstrucoes.LC, "DSVT", "-", "?");
			msg("gera instru��o DSVT");
			
			pilhaCase.push(areaInstrucoes.LC);
			
			msg(instrucoes + "");
			
			msg("pilhaCase: " + pilhaCase);
			
			break;
		case 137:
			msg("ACAO: apos variavel FOR (FEITA)");

			simbolo137 = ts.buscar(tokenAnterior.getLexeme());

			if (simbolo137 == null || !simbolo137.getCategoria().equals("VAR")) {
				msg("N�o encontrada ou n�o e do tipo VAR");

				throw new SemanticoExepition(
						tokenAnterior.getLexeme() + " n�o foi declarado ou n�o � nome de vari�vel");
			} else {
				nivel137 = simbolo137.getNivel();

				System.out.println("nivel137 = " + nivel137);
			}
			break;
		case 138:
			msg("ACAO 138:  Ap�s express�o valor inicial (FEITA)");

			int geralA137 = Integer.parseInt(simbolo137.getGeralA());

			MH.IncluirAI(areaInstrucoes, 4, nivel137, geralA137);
			addInstrucao(areaInstrucoes.LC, "ARMZ", nivel137 + "", geralA137 + "");

			msg("add MH: [" + areaInstrucoes.LC + ", ARMZ, " + nivel137 + ", " + geralA137 +"]");

			msg("geralA de " + simbolo137.getNome() + ": " + geralA137);

			break;
		case 139:
			msg("ACAO 139: Ap�s express�o � valor final (FEITA)");
			
			//guarda inicio COPY
			MH.IncluirAI(areaInstrucoes, 28, 0, 1); 	
			addInstrucao(areaInstrucoes.LC, "COPI", "-", "-");
			msg("add MH: [" + areaInstrucoes.LC + ", COPI, - , - ]");
			pilhaFor.push(areaInstrucoes.LC);
			msg("insere na pilhaFor: " + areaInstrucoes.LC);
			msg("pilhaFor: " + pilhaFor);
			
			msg("simbolo: " + simbolo137.getNome());
			nivel137 = simbolo137.getNivel() - nivelAtual;
			msg("nivel: " + nivel137);
			
			geralA137 = Integer.parseInt(simbolo137.getGeralA());
			msg("geralA: " + geralA137);
			
			MH.IncluirAI(areaInstrucoes, 2, nivel137, geralA137);
			addInstrucao(areaInstrucoes.LC, "CRVL", nivel137+"",""+ geralA137);	
			msg("add MH: [" + areaInstrucoes.LC + ", CRVL, " + nivel137 + ", " + geralA137 + "]");
			
			MH.IncluirAI(areaInstrucoes, 18, 0, 0);
			addInstrucao(areaInstrucoes.LC,"CMAI","-","-");
			msg("add MH: [" + areaInstrucoes.LC + ", " + "CMAI, - , - ]");
			
			MH.IncluirAI(areaInstrucoes, 20,0, 0);		
			addInstrucao(areaInstrucoes.LC,"DVSF","-","?");
			msg("add MH: [" + areaInstrucoes.LC + ", DSVF, - , ? ]");
			
			pilhaFor.push(areaInstrucoes.LC);
			msg("add pilhaFor: " + areaInstrucoes.LC);
			msg("pilhaFor: " + pilhaFor);
			
			simbolo139 = simbolo137;
			posicao139 = areaInstrucoes.LC;

			msg("guarda simbolo: " + simbolo139.getNome());
//			msg("guarda posicao LC: " + posicao139);
			
			break;
		case 140:
			msg("ACAO 140: Ap�s comando em FOR (FEITA)");

//			msg("pilhaFor: " + pilhaFor);
//			int posicao140 = pilhaFor.pop();
//			msg("topo pilha: " + posicao140);
			
			nivel140 = simbolo139.getNivel() - nivelAtual;
			msg("nivel: " + nivel140);
			
			msg(simbolo139.getNome() + " geralA: " + simbolo139.getGeralA());
			geralA140 = Integer.parseInt(simbolo139.getGeralA());
			msg("geralA: " + geralA140);
			
			MH.IncluirAI(areaInstrucoes, 2, nivel140, geralA140);
			addInstrucao(areaInstrucoes.LC, "CRVL", nivel140+"", geralA140+"");
			msg("add MH: [" + areaInstrucoes.LC + ", CRVL, " + nivel140 + ", " + geralA140+"]");
			
			MH.IncluirAI(areaInstrucoes, 3, 0, 1);
			addInstrucao(areaInstrucoes.LC,"CRCT","-","1");
			msg("add MH: [" + areaInstrucoes.LC + ", CRCT, - , 1 ]");
			
			MH.IncluirAI(areaInstrucoes, 5,0, 0);
			addInstrucao(areaInstrucoes.LC,"SOMA","-","-");
			msg("add MH: [" + areaInstrucoes.LC + ", SOMA, - , - ]");
			
			MH.IncluirAI(areaInstrucoes, 4, nivel140, geralA140);
			addInstrucao(areaInstrucoes.LC,"ARMZ",nivel140+"",geralA140+"");
			msg("add MH: [" + areaInstrucoes.LC + ", ARMZ, " + nivel140 + ", " + geralA140 + "]");
			
			//resolve retorno do DSVF do FOR
			msg("pilhaFor: " + pilhaFor);
			int posicao140 = pilhaFor.pop();
			msg("pilhaFor: " + posicao140);
			
			MH.IncluirAI(areaInstrucoes, 19, 0,(0));
			addInstrucao(areaInstrucoes.LC,"DSVS", "-",""+(0));
			msg("add MH: [" + areaInstrucoes.LC + ", DSVS, - ," + (0) + "]");
			
			MH.AlterarAI(areaInstrucoes, posicao140-1 , -1, areaInstrucoes.LC+1);
			msg(instrucoes.get(posicao140-1) + "");
			instrucoes.get(posicao140-1).setOp2((areaInstrucoes.LC + 1) + "");
			msg(instrucoes.get(posicao140-1) + "");
			
			posicao140 = pilhaFor.pop();
			msg(posicao140 + "");
			
			MH.AlterarAI(areaInstrucoes, areaInstrucoes.LC, -1, posicao140);
			msg(instrucoes.get(areaInstrucoes.LC - 1) + "");
			instrucoes.get(areaInstrucoes.LC - 1).setOp2(posicao140 + "");
			msg(instrucoes.get(areaInstrucoes.LC - 1) + "");
			
			MH.IncluirAI(areaInstrucoes, 24, 0, -1);
			addInstrucao(areaInstrucoes.LC,"AMEN", "-","-1");
			msg("add MH: [" + areaInstrucoes.LC + ", AMEN, - , -1");
			
			System.out.println(instrucoes);
			
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
			msg("ACAO 151: multiplicacao (FEITA)");
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
