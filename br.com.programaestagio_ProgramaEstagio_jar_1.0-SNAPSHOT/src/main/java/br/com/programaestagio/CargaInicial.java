package br.com.programaestagio.inicializacao;

import br.com.programaestagio.*;
import java.time.LocalDate;

public class CargaInicial {
    public static void main(String[] args) {
        // 1. Criar supervisor acadêmico
        SupervisorAcademico supervisor = new SupervisorAcademico.Builder()
            .comNome("Prof. Carlos Eduardo")
            .comEmail("carlos.eduardo@faculdade.edu.br")
            .comInscricaoConselho("12345/SP")
            .comConselhoClasse("CONFEA")
            .build();

        // 2. Criar aluno exemplo
        Aluno alunoExemplo = new Aluno.Builder()
            .comId("A1001")
            .comNome("João Silva")
            .comCpf("123.456.789-09")
            .comDataNascimento(LocalDate.of(1995, 5, 15))
            .comCurso("Engenharia de Software")
            .comSupervisor(supervisor)
            .build();

        // 3. Criar responsáveis legais
        ResponsavelLegal responsavel1 = new ResponsavelLegal.Builder()
            .comNome("Maria Oliveira")
            .comCpf("987.654.321-00")
            .comCargo("Diretora Executiva")
            .comDocumentoIdentificacao("SP-1234567")
            .build();

        ResponsavelLegal responsavel2 = new ResponsavelLegal.Builder()
            .comNome("Pedro Santos")
            .comCpf("456.789.123-00")
            .comCargo("Gerente de RH")
            .build();

        // 4. Criar empresa concedente
        EmpresaConcedente empresa = new EmpresaConcedente.Builder()
            .comRazaoSocial("Tech Solutions Ltda")
            .comCnpj("12.345.678/0001-99")
            .comResponsavel(responsavel1)
            .comResponsavel(responsavel2)
            .comEndereco("Av. Paulista, 1000", "Bela Vista", "São Paulo", "SP", "01310-100")
            .comContato("(11) 9999-8888", "contato@techsolutions.com.br")
            .build();

        // 5. Criar estágio
        Estagio estagio = new Estagio.Builder()
            .comAluno(alunoExemplo)
            .comEmpresa(empresa)
            .comSupervisorAcademico(supervisor)
            .comAreaEstagio("Desenvolvimento de Software")
            .comDataInicio(LocalDate.now())
            .comDataTermino(LocalDate.now().plusMonths(6))
            .build();

        // 6. Persistir os dados (exemplo com repositórios)
        AlunoRepository alunoRepo = new AlunoRepository();
        EmpresaRepository empresaRepo = new EmpresaRepository();
        EstagioRepository estagioRepo = new EstagioRepository();

        alunoRepo.salvar(alunoExemplo);
        empresaRepo.salvar(empresa);
        estagioRepo.salvar(estagio);

        System.out.println("Carga inicial concluída com sucesso!");
        System.out.println("Aluno: " + alunoExemplo.getNome());
        System.out.println("Empresa: " + empresa.getRazaoSocial());
        System.out.println("Estágio: " + estagio.getAreaEstagio());
    }
}