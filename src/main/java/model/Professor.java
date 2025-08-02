package model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Professor implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nomeProfessor;
    private List<Aula> aulas;

    public Professor(String nomeProfessor) {
        setNomeProfessor(nomeProfessor);
    }

    public Professor() {
        // Construtor vazio necessário para ORM ou deserialização
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        if (nomeProfessor == null || nomeProfessor.isBlank())
            throw new IllegalArgumentException("Nome do professor não pode ser vazio");
        this.nomeProfessor = nomeProfessor;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        Professor that = (Professor) o;
        return id == that.id && Objects.equals(nomeProfessor, that.nomeProfessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeProfessor);
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nomeProfessor='" + nomeProfessor + '\'' +
                '}';
    }
}
