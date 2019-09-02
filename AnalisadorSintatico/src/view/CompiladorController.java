package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class CompiladorController {
	@FXML
	private Button selecionarArquivo;

	@FXML
	private TextArea areaCodigo = new TextArea();;

	public void selecionarArqAction(ActionEvent event) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = jfc.getSelectedFile();
				BufferedReader reader;
				StringBuilder sb = new StringBuilder();
				reader = new BufferedReader(new FileReader(selectedFile));
				String linha = reader.readLine();
				String linha2 = linha + "\n";

				while (linha != null) {
					linha = reader.readLine();
					linha2 += linha + "\n";
				}
				
				areaCodigo.setText(linha2);

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}
}
