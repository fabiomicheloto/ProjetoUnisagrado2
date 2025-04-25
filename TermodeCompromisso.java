
package br.com.programaestagio.documento;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TermodeCompromisso {

    public void gerar(Map<String, String> dados, String nomeArquivoSaida) throws Exception {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new File("src/main/resources/template/TCE_template.docx"));
        MainDocumentPart mainPart = template.getMainDocumentPart();
        mainPart.variableReplace(dados);
        template.save(new File("output/" + nomeArquivoSaida));
    }
}
