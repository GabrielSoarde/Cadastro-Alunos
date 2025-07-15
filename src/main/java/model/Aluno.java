package model;

public class Aluno {

    private int id;
    private String nome;
    private String curso;
    private String telefone;
    private String email;
    private String cpf;
    private String escolaridade;
    private String experienciaMusical;

    private Matricula matricula;
    private Aula aula;


    public Aluno(int id, String nome, String curso, String telefone, String email, String cpf, String escolaridade, String experienciaMusical) {
        this.id = id;
        this.nome = nome;
        this.curso = curso;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.escolaridade = escolaridade;
        this.experienciaMusical = experienciaMusical;

        this.matricula = new Matricula();
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
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
}
