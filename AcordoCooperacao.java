package br.com.programaestagio.documento;

import java.io.File;
import java.io.InputStream; // ✅ Import necessário
import java.util.HashMap;
import java.util.Map;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import br.com.programaestagio.Concedente;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class AcordoCooperacao {

    public void gerar(Concedente concedente) throws Exception {
        // Carrega o template da pasta resources/template
        InputStream templateStream = getClass().getClassLoader().getResourceAsStream("template/acordo_template.docx");
        WordprocessingMLPackage template = WordprocessingMLPackage.load(templateStream);
        MainDocumentPart mainPart = template.getMainDocumentPart(); // ✅ Corrigido: mainPart declarado

        // Dados a preencher
        Map<String, String> dados = new HashMap<>();
        dados.put("razaoSocial", concedente.getRazaoSocial());
        dados.put("logradouro", concedente.getLogradouro());
        dados.put("numero", String.valueOf(concedente.getNumero()));
        dados.put("bairro", concedente.getBairro());
        dados.put("municipio", concedente.getMunicipio());
        dados.put("uf", concedente.getUF());
        dados.put("telefone", concedente.getTelefone());
        dados.put("email", concedente.getEmail());
        dados.put("cnpj", concedente.getCnpj());
        dados.put("nomeRepresentante", concedente.getRepresentanteLegal());
        dados.put("cargoRepresentante", concedente.getCargoRepresentante());

        // Data atual formatada
        LocalDate hoje = LocalDate.now();
        dados.put("dia", String.valueOf(hoje.getDayOfMonth()));
        dados.put("mes", hoje.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        dados.put("ano", String.valueOf(hoje.getYear()));

        // Substitui os campos no documento
        mainPart.variableReplace(dados);

        // Salva o novo arquivo gerado
        template.save(new File("acordo_cooperacao_" + concedente.getRazaoSocial().replaceAll(" ", "_") + ".docx"));
    }
}

