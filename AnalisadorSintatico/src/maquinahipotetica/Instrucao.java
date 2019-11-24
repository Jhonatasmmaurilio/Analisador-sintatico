package maquinahipotetica;

public class Instrucao {
	public String index, instrucao, op1, op2;

	public Instrucao(String index, String instrucao, String op1, String op2) {
		super();
		this.index = index;
		this.instrucao = instrucao;
		this.op1 = op1;
		this.op2 = op2;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}

	@Override
	public String toString() {
		return index + " | " + instrucao + " | " + op1 + " | " + op2;
	}
}