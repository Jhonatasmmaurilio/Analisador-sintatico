package maquinahipotetica;

public class Instrucao {
	public int codigo; 
	public int op1;
	public int op2;
	
	public Instrucao(int codigo, int op1, int op2) {
		super();
		this.codigo = codigo;
		this.op1 = op1;
		this.op2 = op2;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getOp1() {
		return op1;
	}
	public void setOp1(int op1) {
		this.op1 = op1;
	}
	public int getOp2() {
		return op2;
	}
	public void setOp2(int op2) {
		this.op2 = op2;
	}
	
	@Override
	public String toString() {
		return "instrucoes [codigo=" + codigo + ", op1=" + op1 + ", op2=" + op2 + "]";
	}
}
