/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.programaestagio;

/**
 *
 * @author fabio
 */
public class Estagio {
    private String AreaEstagio;
    private String Curso;
    private String DataInicio;
    private String DataTermino;

    public Estagio(String AreaEstagio, String Curso, String DataInicio, String DataTermino) {
        this.AreaEstagio = AreaEstagio;
        this.Curso = Curso;
        this.DataInicio = DataInicio;
        this.DataTermino = DataTermino;
    }

    Estagio() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getAreaEstagio() {
        return AreaEstagio;
    }

    public void setAreaEstagio(String AreaEstagio) {
        this.AreaEstagio = AreaEstagio;
    }

    public String getCurso() {
        return Curso;
    }

    public void setCurso(String Curso) {
        this.Curso = Curso;
    }

    public String getDataInicio() {
        return DataInicio;
    }

    public void setDataInicio(String DataInicio) {
        this.DataInicio = DataInicio;
    }

    public String getDataTermino() {
        return DataTermino;
    }

    public void setDataTermino(String DataTermino) {
        this.DataTermino = DataTermino;
    }
    
    
    
}
