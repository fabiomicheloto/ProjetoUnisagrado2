package br.com.programaestagio;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Contrato {
    private String data;
    private String razaoSocial;
    private String nomeRepresentante;
    private String testemunha1Nome;
    private String testemunha1RG;
    private String testemunha2Nome;
    private String testemunha2RG;

    // Setters
    public void setData(String data) { this.data = data; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public void setNomeRepresentante(String nomeRepresentante) { this.nomeRepresentante = nomeRepresentante; }
    public void setTestemunha1(String nome, String rg) { 
        this.testemunha1Nome = nome;
        this.testemunha1RG = rg;
    }
    public void setTestemunha2(String nome, String rg) { 
        this.testemunha2Nome = nome;
        this.testemunha2RG = rg;
    }

    // Método para gerar HTML
    public String gerarHTML() throws Exception {
        String caminhoTemplate = "src/main/resources/contrato-template.html";
        String template = new String(Files.readAllBytes(Paths.get(caminhoTemplate)));

        return template
            .replace("{DATA}", data)
            .replace("{RAZAO_SOCIAL}", razaoSocial)
            .replace("{NOME_REPRESENTANTE}", nomeRepresentante)
            .replace("{TESTEMUNHA1_NOME}", testemunha1Nome)
            .replace("{TESTEMUNHA1_RG}", testemunha1RG)
            .replace("{TESTEMUNHA2_NOME}", testemunha2Nome)
            .replace("{TESTEMUNHA2_RG}", testemunha2RG);
    }
import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.StringReader;

public void gerarPDF(String html, String caminhoSaida) throws Exception {
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(caminhoSaida));
    document.open();
    HTMLWorker htmlWorker = new HTMLWorker(document);
    htmlWorker.parse(new StringReader(html));
    document.close();
}
}