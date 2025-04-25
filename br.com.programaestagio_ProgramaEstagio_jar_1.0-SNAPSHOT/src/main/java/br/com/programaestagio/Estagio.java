/public class Estagio {
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private boolean ativo;
    
    public boolean estaAtivo() {
        return ativo && LocalDate.now().isBefore(dataTermino);
    }
    // outros métodos...
}

public class SupervisorAcademico extends Supervisor {
    // implementação específica do supervisor acadêmico
}