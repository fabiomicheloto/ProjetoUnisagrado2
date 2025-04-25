package br.com.programaestagio;

public class Pessoa {
    private String nome;
    private String logradouro;
    private String bairro;
    private String municipio;
    private String uf;
    private String cep;  // Alterado para String
    private String telefone;
    private String email;
    
    // Getters and setters
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getLogradouro() { 
        return logradouro; 
    }
    public void setLogradouro(String logradouro) { 
        this.logradouro = logradouro; 
    }
    
    public String getBairro() { 
        return bairro; 
    }
    public void setBairro(String bairro) { 
        this.bairro = bairro; 
    }
    
    public String getMunicipio() {  // Removido acento
        return municipio; 
    }
    public void setMunicipio(String municipio) {  // Removido acento
        this.municipio = municipio; 
    }
    
    public String getUf() {  // Padronizado para lowercase
        return uf; 
    }
    public void setUf(String uf) {  // Corrigido parâmetro
        this.uf = uf; 
    }
    
    public String getCep() {  // Alterado tipo de retorno
        return cep; 
    }
    public void setCep(String cep) {  // Alterado tipo do parâmetro
        this.cep = cep; 
    }
    
    public String getTelefone() { 
        return telefone; 
    }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }
    
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
}