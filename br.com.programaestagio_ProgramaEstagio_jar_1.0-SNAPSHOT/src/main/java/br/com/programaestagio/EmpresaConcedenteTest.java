package br.com.programaestagio.test;

import br.com.programaestagio.*;

public class EmpresaConcedenteTest {
    public static void main(String[] args) {
        // Código de exemplo vai aqui
        ResponsavelLegal responsavel1 = new ResponsavelLegal.Builder()
            .comNome("João Silva")
            .comCpf("123.456.789-09")
            .build();
        
        // Restante do código...
        
        System.out.println("Empresa criada: " + empresa.getRazaoSocial());
    }
}