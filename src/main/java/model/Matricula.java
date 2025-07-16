package model;

import service.GeradorMatricula;
import java.io.Serializable;
import java.time.LocalDate;

public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    private String numeroMatricula;
    private LocalDate dataMatricula;

    public Matricula() {
        this.numeroMatricula = GeradorMatricula.gerarMatriculaUnica();
        this.dataMatricula = LocalDate.now();
    }

    public Matricula(String numeroMatricula, LocalDate dataMatricula) {
        this.numeroMatricula = numeroMatricula;
        this.dataMatricula = dataMatricula;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "numeroMatricula='" + numeroMatricula + '\'' +
                ", dataMatricula=" + dataMatricula +
                '}';
    }
}
