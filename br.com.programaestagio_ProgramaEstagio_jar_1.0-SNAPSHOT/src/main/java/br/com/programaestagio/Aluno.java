package br.com.programaestagio;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno extends Pessoa {
    private String id;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String termo;
    private String curso;
    private List<Estagio> estagios = new ArrayList<>();
    private SupervisorAcademico supervisorAcademico;

    // Construtor privado para usar com o Builder
    private Aluno() {}

    // Validações nos setters
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser vazio");
        }
        this.id = id;
    }

    public void setCpf(String cpf) {
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        if (rg == null || rg.trim().isEmpty()) {
            throw new IllegalArgumentException("RG não pode ser vazio");
        }
        this.rg = rg;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now().minusYears(14))) {
            throw new IllegalArgumentException("Data de nascimento inválida ou aluno muito jovem");
        }
        this.dataNascimento = dataNascimento;
    }

    public void setTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            throw new IllegalArgumentException("Termo não pode ser vazio");
        }
        this.termo = termo;
    }

    public void setCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("Curso não pode ser vazio");
        }
        this.curso = curso;
    }

    // Métodos de negócio
    public boolean podeIniciarEstagio() {
        return !estaEmEstagioAtivo() && 
               Period.between(dataNascimento, LocalDate.now()).getYears() >= 16;
    }

    public boolean estaEmEstagioAtivo() {
        return estagios.stream().anyMatch(Estagio::estaAtivo);
    }

    public void adicionarEstagio(Estagio estagio) {
        if (estagio == null) {
            throw new IllegalArgumentException("Estágio não pode ser nulo");
        }
        this.estagios.add(estagio);
    }

    public void definirSupervisor(SupervisorAcademico supervisor) {
        if (supervisor == null) {
            throw new IllegalArgumentException("Supervisor não pode ser nulo");
        }
        this.supervisorAcademico = supervisor;
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    // Método auxiliar para validar CPF
    private boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false;
        }
        
        // Aqui implementaria o algoritmo de validação de CPF
        return true;
    }

    // Padrão Builder
    public static class Builder {
        private final Aluno aluno = new Aluno();

        public Builder comId(String id) {
            aluno.setId(id);
            return this;
        }

        public Builder comNome(String nome) {
            aluno.setNome(nome);
            return this;
        }

        public Builder comCpf(String cpf) {
            aluno.setCpf(cpf);
            return this;
        }

        public Builder comDataNascimento(LocalDate data) {
            aluno.setDataNascimento(data);
            return this;
        }

        public Builder comCurso(String curso) {
            aluno.setCurso(curso);
            return this;
        }

        public Builder comSupervisor(SupervisorAcademico supervisor) {
            aluno.definirSupervisor(supervisor);
            return this;
        }

        public Aluno build() {
            Objects.requireNonNull(aluno.id, "ID é obrigatório");
            Objects.requireNonNull(aluno.cpf, "CPF é obrigatório");
            Objects.requireNonNull(aluno.nome, "Nome é obrigatório");
            return aluno;
        }
    }

    // Getters
    public String getId() { return id; }
    public String getCpf() { return cpf; }
    public String getRg() { return rg; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTermo() { return termo; }
    public String getCurso() { return curso; }
    public List<Estagio> getEstagios() { return new ArrayList<>(estagios); }
    public SupervisorAcademico getSupervisorAcademico() { return supervisorAcademico; }

    @Override
    public String toString() {
        return "Aluno{" +
                "id='" + id + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + cpf + '\'' +
                ", curso='" + curso + '\'' +
                ", idade=" + calcularIdade() +
                '}';
    }
}