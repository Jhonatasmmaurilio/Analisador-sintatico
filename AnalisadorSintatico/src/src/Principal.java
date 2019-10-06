package tabelaSimbolo;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Principal {

	private  JFrame frame;
	private static JPanel panel; 
	private static JButton btnInicializarTabela;
	private static JTextArea textArea;
	private static JTextField txtNivel;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBoxCategoria;
	private static JTextField textFieldNome;
	private static JLabel lblNome;
	private static JLabel lblNivel;
	private static JLabel lblGeralB;
	private static JLabel lblGeralA;
	private static JLabel lblCategoria;
	private static JTextField textFieldGeralA;
	private static JTextField textFieldGeralB;
	private static JButton btnIncluir;
	private static JLabel lblTabelaIniciada;
	private static JButton btnMostrar;
	private static JButton btnAlterar;
	private static JButton btnExcluir;
	private static JButton btnBuscar;
	private static JButton btnOk;

	private static Simbolo[][] tbSimbolo = new Simbolo[7][10];
	private static boolean encontrou = false;
	private static int posI = 0;
	private static int posJ = 0;
	private static String controle = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 771, 585);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(10, 221, 735, 314);
		frame.getContentPane().add(textArea);

	    panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(10, 11, 744, 199);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		btnInicializarTabela = new JButton("Inic. Tabela");
		btnInicializarTabela.setHorizontalAlignment(SwingConstants.LEFT);
		btnInicializarTabela.setBounds(10, 11, 99, 23);

		comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setEnabled(false);
		comboBoxCategoria.setModel(new DefaultComboBoxModel(new String[] { "VAR", "CONST", "PROC", "PAR" }));
		comboBoxCategoria.setBounds(169, 58, 107, 20);

		lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(169, 45, 65, 14);

		txtNivel = new JTextField();
		txtNivel.setEnabled(false);
		txtNivel.setBounds(286, 58, 46, 20);
		txtNivel.setColumns(10);

		textFieldNome = new JTextField();
		textFieldNome.setEnabled(false);
		textFieldNome.setBounds(10, 58, 149, 20);
		textFieldNome.setColumns(10);

		lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 45, 46, 14);

		lblNivel = new JLabel("Nivel");
		lblNivel.setBounds(286, 45, 46, 14);

		textFieldGeralA = new JTextField();
		textFieldGeralA.setEnabled(false);
		textFieldGeralA.setBounds(342, 58, 141, 20);
		textFieldGeralA.setColumns(10);

		lblGeralA = new JLabel("Geral A");
		lblGeralA.setBounds(342, 45, 46, 14);

		textFieldGeralB = new JTextField();
		textFieldGeralB.setEnabled(false);
		textFieldGeralB.setBounds(493, 58, 141, 20);
		textFieldGeralB.setColumns(10);

		lblGeralB = new JLabel("Geral B");
		lblGeralB.setBounds(493, 45, 46, 14);

		btnAlterar = new JButton("Alterar");
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(106, 89, 89, 23);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);

		btnExcluir.setBounds(202, 89, 89, 23);
		btnBuscar = new JButton("Buscar");
		btnBuscar.setEnabled(false);
		btnBuscar.setBounds(298, 89, 89, 23);

		btnMostrar = new JButton("Mostrar");
		btnMostrar.setBounds(584, 89, 149, 23);

		btnIncluir = new JButton("Incluir");
		btnIncluir.setEnabled(false);
		btnIncluir.setBounds(10, 89, 89, 23);

		lblTabelaIniciada = new JLabel("");
		lblTabelaIniciada.setBounds(119, 15, 364, 14);

		btnOk = new JButton("OK");
		btnOk.setEnabled(false);
		btnOk.setBounds(644, 57, 89, 23);

		panel.add(comboBoxCategoria);
		panel.add(lblNivel);
		panel.add(lblNome);
		panel.add(textFieldNome);
		panel.add(txtNivel);
		panel.add(lblCategoria);
		panel.add(btnInicializarTabela);
		panel.add(textFieldGeralA);
		panel.add(lblGeralA);
		panel.add(textFieldGeralB);
		panel.add(lblGeralB);
		panel.add(btnAlterar);
		panel.add(btnExcluir);
		panel.add(btnBuscar);
		panel.add(btnMostrar);
		panel.add(btnIncluir);
		panel.add(lblTabelaIniciada);
		panel.add(btnOk);

		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");
				btnIncluir.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnBuscar.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnOk.setEnabled(true);
				textFieldNome.setEnabled(true);
				comboBoxCategoria.setEnabled(true);
				txtNivel.setEnabled(true);
				textFieldGeralA.setEnabled(true);
				textFieldGeralB.setEnabled(true);
				controle = "ALTERAR";
			}
		});

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");
				tbSimbolo[posI][posJ] = new Simbolo();
				textArea.setText("Simbolo alterado com sucesso!");
				btnIncluir.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnBuscar.setEnabled(true);
				btnAlterar.setEnabled(false);
				btnOk.setEnabled(false);
				textFieldNome.setEnabled(false);
				comboBoxCategoria.setEnabled(false);
				txtNivel.setEnabled(false);
				textFieldGeralA.setEnabled(false);
				textFieldGeralB.setEnabled(false);
				limpaCampos();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");
				textFieldNome.requestFocus();
				btnIncluir.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnBuscar.setEnabled(false);
				btnOk.setEnabled(true);
				textFieldNome.setEnabled(true);
				comboBoxCategoria.setEnabled(false);
				txtNivel.setEnabled(false);
				textFieldGeralA.setEnabled(false);
				textFieldGeralB.setEnabled(false);
				controle = "BUSCAR";
			}
		});

		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setText("");
				mostra();
			}
		});

		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				textFieldNome.requestFocus();
				textArea.setText("");
				btnIncluir.setEnabled(false);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnBuscar.setEnabled(false);
				btnOk.setEnabled(true);
				textFieldNome.setEnabled(true);
				comboBoxCategoria.setEnabled(true);
				txtNivel.setEnabled(true);
				textFieldGeralA.setEnabled(true);
				textFieldGeralB.setEnabled(true);
				controle = "INCLUIR";

			}
		});

		btnInicializarTabela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				inicializa();
				lblTabelaIniciada.setText("TABELA DE SIMBOLOS INICIALIZADA...");
				btnInicializarTabela.setEnabled(false);
				btnIncluir.setEnabled(true);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnMostrar.setEnabled(true);
				btnBuscar.setEnabled(true);
			}
		});

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (controle == "INCLUIR") {
					if (inclui()) {

						btnAlterar.setEnabled(false);
						btnExcluir.setEnabled(false);
						btnBuscar.setEnabled(true);
						btnIncluir.setEnabled(true);
						btnOk.setEnabled(false);
						textFieldNome.setEnabled(false);
						comboBoxCategoria.setEnabled(false);
						txtNivel.setEnabled(false);
						textFieldGeralA.setEnabled(false);
						textFieldGeralB.setEnabled(false);

					}
				} else if (controle == "ALTERAR") {

					altera(posI, posJ);
					btnIncluir.setEnabled(true);
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnBuscar.setEnabled(true);
					btnOk.setEnabled(false);
					textFieldNome.setEnabled(false);
					comboBoxCategoria.setEnabled(false);
					txtNivel.setEnabled(false);
					textFieldGeralA.setEnabled(false);
					textFieldGeralB.setEnabled(false);

				} else if (controle == "BUSCAR") {

					if (busca()) {
						btnIncluir.setEnabled(true);
						btnExcluir.setEnabled(true);
						btnMostrar.setEnabled(true);
						btnBuscar.setEnabled(true);
						btnAlterar.setEnabled(true);
						btnOk.setEnabled(false);
						textFieldNome.setEnabled(true);
						comboBoxCategoria.setEnabled(false);
						txtNivel.setEnabled(false);
						textFieldGeralA.setEnabled(false);
						textFieldGeralB.setEnabled(false);
					}
				} else if (controle == "EXCLUIR") {

				}
			}

		});

	}
	
	private static void inicializa() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				tbSimbolo[i][j] = new Simbolo();
			}
		}
	}

	
	public static boolean inclui() {
		if (textFieldNome.getText() != "" && txtNivel.getText() != "") {
			Simbolo s = new Simbolo();
			String nome = textFieldNome.getText();
			if (existe(nome)) {
				textArea.setText("Já existe um simbolo com esse nome!");
				return false;
			}
			String categoria = comboBoxCategoria.getSelectedItem().toString();
			int nivel = Integer.parseInt(txtNivel.getText());
			String geralA = textFieldGeralA.getText();
			String geralB = textFieldGeralB.getText();
			int i = funcaoHash(nome);
			s.insere(nome, categoria, nivel, geralA, geralB);
			int j = buscaPosicao(i);
			if (j != -1) {
				tbSimbolo[i][j] = s;
				textArea.setText("Simbolo incluido com sucesso!");
				limpaCampos();
				return true;
			} else {
				textArea.setText("Não há posições disponíveis para esse indice.");
				return false;
			}
		} else {
			return false;
		}

	}
	
	public static void altera(int i, int j) {
		tbSimbolo[i][j].setNome(textFieldNome.getText());
		tbSimbolo[i][j].setCategoria(comboBoxCategoria.getSelectedItem().toString());
		tbSimbolo[i][j].setNivel(Integer.parseInt(txtNivel.getText()));
		tbSimbolo[i][j].setGeralA(textFieldGeralA.getText());
		tbSimbolo[i][j].setGeralB(textFieldGeralB.getText());
		textArea.setText("Simbolo alterado com sucesso!");
		limpaCampos();
	}

	private static int buscaPosicao(int indice) {
		for (int i = 0; i < 10; i++) {
			if (tbSimbolo[indice][i].getNome() == null) {
				return i;
			}
		}
		return -1;

	}

	private static boolean existe(String nome) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) {
				if (tbSimbolo[i][j].getNome() != null) {
					if (tbSimbolo[i][j].getNome().equals(nome)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static void mostra() {
		String lista = "";
		for (int i = 0; i < 7; i++) {
			lista += i + " ";
			for (int j = 0; j < 10; j++) {
				if (tbSimbolo[i][j].getNome() != null) {
					lista += "- " + tbSimbolo[i][j].getNome() + " ";
				}
			}
			lista += "\n";
		}
		textArea.setText(lista);
	}

	public static void limpaCampos() {
		textFieldNome.setText("");
		txtNivel.setText("");
		textFieldGeralA.setText("");
		textFieldGeralB.setText("");
	}

	public static int funcaoHash(String nome) {
		int val = 0;
		for (int i = 0; i < nome.length(); i++) {
			val = 37 * val + nome.charAt(i);
		}
		val %= 7;
		if (val < 0) {
			val += 7;
		}
		return val;
	}

	public static boolean busca() {
		encontrou = false;
		String simboloBuscado = "";
		String nomeBusca = textFieldNome.getText();
		int i = funcaoHash(nomeBusca);
		for (int j = 0; j < 10; j++) {
			if (tbSimbolo[i][j].getNome() != null) {
				if (tbSimbolo[i][j].getNome().equals(nomeBusca)) {
					simboloBuscado = "Indice: " + i + "\n" + "Nome: " + tbSimbolo[i][j].getNome() + "\n"
							+ "Categoria: " + tbSimbolo[i][j].getCategoria() + "\n" + "Nível: "
							+ tbSimbolo[i][j].getNivel() + "\n" + "Geral A: " + tbSimbolo[i][j].getGeralA() + "\n"
							+ "Geral B: " + tbSimbolo[i][j].getGeralB();
					posI = i;
					posJ = j;
					encontrou = true;
				}
			}
		}
		if (encontrou == false) {
			textArea.setText("Simbolo não encontrado!");
			return false;
		} else {
			textArea.setText(simboloBuscado);
			textFieldNome.setText(tbSimbolo[posI][posJ].getNome());
			comboBoxCategoria.setSelectedItem(tbSimbolo[posI][posJ].getCategoria());
			txtNivel.setText(String.valueOf(tbSimbolo[posI][posJ].getNivel()));
			textFieldGeralA.setText(tbSimbolo[posI][posJ].getGeralA());
			textFieldGeralB.setText(tbSimbolo[posI][posJ].getGeralB());
			return true;
		}
	}
}
