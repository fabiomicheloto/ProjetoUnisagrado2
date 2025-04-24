/import br.com.programaestagio.documento.DocumentoTCE;
import java.util.HashMap;
import java.util.Map;

public class GeradorTCE {
    public static void main(String[] args) {
        try {
            TermodeCompromisso.docx = new TermodeCompromisso();

            Map<String, String> dados = new HashMap<>();
            dados.put("razaoSocial", "Farmacêutica Exemplo LTDA");
            dados.put("logradouro", "Rua das Flores, 123");
            dados.put("bairro", "Centro");
            dados.put("municipio", "Ourinhos");
            dados.put("uf", "SP");
            dados.put("telefoneEmpresa", "(14) 1234-5678");
            dados.put("emailEmpresa", "empresa@exemplo.com");
            dados.put("cnpj", "12.345.678/0001-90");
            dados.put("nomeRepresentante", "Carlos Silva");
            dados.put("cargoRepresentante", "Diretor");

            dados.put("nomeAluno", "João da Silva");
            dados.put("rgAluno", "12.345.678-9");
            dados.put("cpfAluno", "123.456.789-00");
            dados.put("dataNascimento", "01/01/2000");
            dados.put("cepAluno", "19900-000");
            dados.put("curso", "Farmácia");
            dados.put("inicio", "01/05/2025");
            dados.put("fim", "30/07/2025");
            dados.put("dias", "segunda a sexta-feira");
            dados.put("horaInicio", "08:00");
            dados.put("horaFim", "12:00");

            dados.put("orientador", "Prof. Dr. Marcelo Tavares");
            dados.put("inscricaoOrientador", "123456 -
