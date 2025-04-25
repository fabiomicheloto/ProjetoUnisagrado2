package br.com.programaestagio;

import java.util.Objects;

public class EmpresaConcedente extends Pessoa {
    private String razaoSocial;
    private String cnpj;
    private String representanteLegal;
    private String cargoRepresentante;

    // Construtor privado para o Builder
    private EmpresaConcedente() {}

    // Getters e Setters com validações
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        if (razaoSocial == null || razaoSocial.trim().isEmpty()) {
            throw new IllegalArgumentException("Razão social não pode ser vazia");
        }
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

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        if (representanteLegal == null || representanteLegal.trim().isEmpty()) {
            throw new IllegalArgumentException("Representante legal não pode ser vazio");
        }
        this.representanteLegal = representanteLegal;
    }

    public String getCargoRepresentante() {
        return cargoRepresentante;
    }

    public void setCargoRepresentante(String cargoRepresentante) {
        this.cargoRepresentante = cargoRepresentante;
    }

    // Método de validação de CNPJ
    private boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;
        
        cnpj = cnpj.replaceAll("[^0-9]", "");
        
        if (cnpj.length() != 14 || cnpj.matches(cnpj.charAt(0) + "{14}")) {
            return false;
        }
        
        // Aqui implementaria o algoritmo de validação de CNPJ
        return true;
    }

    // Padrão Builder
    public static class Builder {
        private final EmpresaConcedente concedente = new EmpresaConcedente();

        public Builder comRazaoSocial(String razaoSocial) {
            concedente.setRazaoSocial(razaoSocial);
            return this;
        }

        public Builder comCnpj(String cnpj) {
            concedente.setCnpj(cnpj);
            return this;
        }

        public Builder comRepresentanteLegal(String representante, String cargo) {
            concedente.setRepresentanteLegal(representante);
            concedente.setCargoRepresentante(cargo);
            return this;
        }

        public Builder comEndereco(String logradouro, String bairro, String municipio, String uf, String cep) {
            concedente.setLogradouro(logradouro);
            concedente.setBairro(bairro);
            concedente.setMunicipio(municipio);
            concedente.setUf(uf);
            concedente.setCep(cep);
            return this;
        }

        public Builder comContato(String telefone, String email) {
            concedente.setTelefone(telefone);
            concedente.setEmail(email);
            return this;
        }

        public EmpresaConcedente build() {
            Objects.requireNonNull(concedente.razaoSocial, "Razão social é obrigatória");
            Objects.requireNonNull(concedente.cnpj, "CNPJ é obrigatório");
            return concedente;
        }
    }

    @Override
    public String toString() {
        return "Concedente{" +
                "razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", representanteLegal='" + representanteLegal + '\'' +
                ", cargoRepresentante='" + cargoRepresentante + '\'' +
                '}';
    }
}