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
import javafx.scene.control.TextArea;
import src.Lexico;
import src.Sintatico;
import src.SintaticoException;
import src.Token;

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
	
	public void getCodigo(int num) {
		String arquivo = "codigos/codigo-01.txt";
		
		if(num == 1) {
			arquivo = "codigos/codigo-01.txt";
		}else if(num == 2) {
			arquivo = "codigos/codigo-02.txt";
		}else {
			arquivo = "codigos/codigo-03.txt";
		}
		
		try {
			BufferedReader reader;
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new FileReader(arquivo));
			
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

	public void Compilar() throws SintaticoException {
		Lexico lexico = new Lexico();
		Sintatico sintatico = new Sintatico();

		String caminhoArquivo = "codigo.txt";
		String linha = "";
		int i = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
			i = 1;

			try {
				lexico.setInput(br);

				Token token = null;
				sintatico.inicialisar(lexico);

				br.close();
			} catch (IOException ex) {
				System.out.println("Erro: não foi possível ler o arquivo");
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Erro: Arquivo não encontrado!");
		}
	}
}
