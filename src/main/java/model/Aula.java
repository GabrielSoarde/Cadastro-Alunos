package model;

import java.time.LocalTime;

public class Aula {

    private int id;
    private DiaDaSemana dia;
    private LocalTime horario;

    private Aluno aluno;
    private Professor professor;

    public enum DiaDaSemana{
        SEGUNDA,
        TERCA,
        QUARTA,
        QUINTA,
        SEXTA,
        SABADO
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public DiaDaSemana getDia() {
        return dia;
    }

    public void setDia(DiaDaSemana dia) {
        this.dia = dia;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
