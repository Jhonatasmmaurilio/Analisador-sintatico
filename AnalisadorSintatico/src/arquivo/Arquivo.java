package arquivo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Arquivo {
    public static String Read(String Caminho){
        String conteudo = "";
        
        try {
            FileReader arq = new FileReader(Caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha="";
            
            try {
                linha = lerArq.readLine();
                
                while(linha!=null){
                	
                    conteudo += linha+"\n";
                    linha = lerArq.readLine();
                }
                
                arq.close();
                
                return conteudo;
                
            } catch (IOException ex) {
                System.out.println("Erro: n�o foi poss�vel ler o arquivo");
                return "";
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo n�o encontrado!");
            return "";
        }
    }
}
