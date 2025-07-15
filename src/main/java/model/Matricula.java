package model;

import service.GeradorMatricula;

import java.time.LocalDate;

public class Matricula {

    private String numeroMatricula;
    private LocalDate dataMatricula;

    public Matricula(){
        this.numeroMatricula = GeradorMatricula.gerarMatriculaUnica();
        this.dataMatricula = LocalDate.now();
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

}
