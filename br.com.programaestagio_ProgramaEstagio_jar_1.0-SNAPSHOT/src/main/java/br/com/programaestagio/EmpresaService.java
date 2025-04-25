package br.com.programaestagio.service;

import br.com.programaestagio.*;

public class EmpresaService {
    public EmpresaConcedente criarEmpresaExemplo() {
        // Código de exemplo vai aqui
        ResponsavelLegal responsavel1 = new ResponsavelLegal.Builder()
            .comNome("João Silva")
            .comCpf("123.456.789-09")
            .build();
        
        return empresa; // Retorna a empresa criada
    }
}