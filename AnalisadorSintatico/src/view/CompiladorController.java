package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import src.Lexico;
import src.Sintatico;
import src.SintaticoException;

public class CompiladorController {
	@FXML
	private Button selecionarArquivo;

	@FXML
	private TextArea areaCodigo = new TextArea();

	@FXML
	private Button gerarCodigo01;

	@FXML
	private Button gerarCodigo02;

	@FXML
	private Button gerarCodigo03;
	
	@FXML
	private Button gerarCodigo04;

	@FXML
	private Button btnCompilar;

	@FXML
	private Button btnLimpar;

	@FXML
	private Pane areaOutput;

	@FXML
	private Label outputTokens;

	@FXML
	private Label outputErros;

	@FXML
	private Label outputLogs;

	Lexico lexico = new Lexico();
	Sintatico sintatico = new Sintatico();
	
	private String msgErro = "";

	public void selecionarArqAction(ActionEvent event) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "Textos (*.txt)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName().toLowerCase();
					return filename.endsWith(".txt");
				}
			}
		});

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = jfc.getSelectedFile();
				BufferedReader reader;
				StringBuilder sb = new StringBuilder();
				reader = new BufferedReader(new FileReader(selectedFile));

				String linha = reader.readLine();
				String codigo = linha + "\n";

				while (linha != null) {
					linha = reader.readLine();

					if (linha != null) {
						codigo += linha + "\n";
					}
				}

				areaCodigo.setText(codigo);

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void gerarCodigo01Action(ActionEvent event) {
		getCodigo(1);
	}

	public void gerarCodigo02Action(ActionEvent event) {
		getCodigo(2);
	}

	public void gerarCodigo03Action(ActionEvent event) {
		getCodigo(3);
	}
	
	public void gerarCodigo04Action(ActionEvent event) {
		getCodigo(4);
	}

	public void getCodigo(int num) {
		String arquivo = "codigos/codigo-01.txt";

		if (num == 1) {
			arquivo = "codigos/codigo-01.txt";
		} else if (num == 2) {
			arquivo = "codigos/codigo-02.txt";
		} else if (num == 3) {
			arquivo = "codigos/codigo-03.txt";
		} else {
			arquivo = "codigos/codigo-04.txt";
		}

		try {
			BufferedReader reader;
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new FileReader(arquivo));

			String linha = reader.readLine();
			String codigo = "";

			while (linha != null) {
				codigo += linha + "\n";
				linha = reader.readLine();
			}

			areaCodigo.setText(codigo);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void Compilar(ActionEvent event) throws SintaticoException {
		this.outputErros.setText(null);
		this.outputLogs.setText(null);
		this.outputTokens.setText(null);
		
		Lexico lexico = new Lexico();
		Sintatico sintatico = new Sintatico();

		String linha = "";
		String codigo = "";
		int i = 0;

		codigo = areaCodigo.getText();

		if (!codigo.isEmpty()) {
			lexico.setInput(codigo.toUpperCase());
			sintatico.inicialisar(lexico, outputTokens, outputLogs, outputErros);
		} else {
			outputErros.setText("INSIRA UM CÓDIGO NO CAMPO ACIMA!");
		}
	}

	@FXML
	void LimparAction(ActionEvent event) {
		areaCodigo.setText(null);
	}
}
