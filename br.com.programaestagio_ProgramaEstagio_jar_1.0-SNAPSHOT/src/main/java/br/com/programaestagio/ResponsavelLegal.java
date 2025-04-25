package br.com.programaestagio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpresaConcedente extends Pessoa {
    private String razaoSocial;
    private String cnpj;
    private List<ResponsavelLegal> responsaveis = new ArrayList<>();

    // Construtor privado para Builder
    private EmpresaConcedente() {}

    // Getters e Setters
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        Objects.requireNonNull(razaoSocial, "Razão social não pode ser nula");
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (!validarCNPJ(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpj;
    }

    public List<ResponsavelLegal> getResponsaveis() {
        return new ArrayList<>(responsaveis); // Retorna cópia para evitar modificações externas
    }

    // Métodos para gerenciar responsáveis
    public void adicionarResponsavel(ResponsavelLegal responsavel) {
        Objects.requireNonNull(responsavel, "Responsável não pode ser nulo");
        this.responsaveis.add(responsavel);
    }

    public void removerResponsavel(ResponsavelLegal responsavel) {
        this.responsaveis.remove(responsavel);
    }

    // Builder Pattern
    public static class Builder {
        private final EmpresaConcedente empresa = new EmpresaConcedente();
        private final List<ResponsavelLegal> responsaveisTemp = new ArrayList<>();

        public Builder comRazaoSocial(String razaoSocial) {
            empresa.setRazaoSocial(razaoSocial);
            return this;
        }

        public Builder comCnpj(String cnpj) {
            empresa.setCnpj(cnpj);
            return this;
        }

        public Builder comResponsavel(ResponsavelLegal responsavel) {
            this.responsaveisTemp.add(responsavel);
            return this;
        }

        public Builder comEndereco(String logradouro, String bairro, String municipio, String uf, String cep) {
            empresa.setLogradouro(logradouro);
            empresa.setBairro(bairro);
            empresa.setMunicipio(municipio);
            empresa.setUf(uf);
            empresa.setCep(cep);
            return this;
        }

        public EmpresaConcedente build() {
            Objects.requireNonNull(empresa.razaoSocial, "Razão social é obrigatória");
            Objects.requireNonNull(empresa.cnpj, "CNPJ é obrigatório");
            
            if (!responsaveisTemp.isEmpty()) {
                responsaveisTemp.forEach(empresa::adicionarResponsavel);
            } else {
                throw new IllegalStateException("Pelo menos um responsável legal deve ser informado");
            }
            
            return empresa;
        }
    }
}