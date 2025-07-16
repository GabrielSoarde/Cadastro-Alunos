package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa um aluno da escola de música.
 */
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int proximoId = 1;

    private int id;
    private String nome;
    private String curso;
    private String telefone;
    private String email;
    private String cpf;
    private String escolaridade;
    private String experienciaMusical;

    private Matricula matricula;
    private List<Aula> aulas;

    public Aluno() {
        this.aulas = new ArrayList<>();
        this.matricula = new Matricula();
    }

    public Aluno(String nome, String curso, String telefone, String email,
                 String cpf, String escolaridade, String experienciaMusical) {
        this.id = proximoId++;
        this.nome = nome;
        this.curso = curso;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.escolaridade = escolaridade;
        this.experienciaMusical = experienciaMusical;

        this.id = proximoId;

        this.matricula = new Matricula();
        this.aulas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getExperienciaMusical() {
        return experienciaMusical;
    }

    public void setExperienciaMusical(String experienciaMusical) {
        this.experienciaMusical = experienciaMusical;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public void addAula(Aula aula) {
        if (this.aulas == null) {
            this.aulas = new ArrayList<>();
        }
        this.aulas.add(aula);
        aula.setAluno(this);
    }

    public void removerAula(Aula aula) {
        if (aula == null) return;
        this.aulas.remove(aula);
        aula.setAluno(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno aluno = (Aluno) o;
        return id == aluno.id && Objects.equals(cpf, aluno.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", escolaridade='" + escolaridade + '\'' +
                ", experienciaMusical='" + experienciaMusical + '\'' +
                ", matricula=" + matricula +
                ", aulas=" + aulas +
                '}';
    }
}
