package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int id;
    private DayOfWeek dia;
    private LocalTime horario;

    @JsonIgnore
    private Aluno aluno;

    private Professor professor;

    public Aula(DayOfWeek dia, LocalTime horario) {
        setDia(dia);
        setHorario(horario);
        this.id = proximoId++;
    }

    public Aula() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        if (dia == null) throw new IllegalArgumentException("Dia não pode ser nulo");
        this.dia = dia;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        if (horario == null) throw new IllegalArgumentException("Horário não pode ser nulo");
        this.horario = horario;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aula)) return false;
        Aula aula = (Aula) o;
        return id == aula.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return dia + " às " + horario;
    }
}
