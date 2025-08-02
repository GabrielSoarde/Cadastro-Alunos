package service;

import dao.ProfessorDAO;
import model.Professor;

import java.util.List;

public class ProfessorService {

    private ProfessorDAO professorDAO;

    public ProfessorService() {
        this.professorDAO = new ProfessorDAO();
    }

    public void adicionarProfessor(Professor professor) {
        if (professor.getNomeProfessor() == null || professor.getNomeProfessor().isBlank()) {
            throw new IllegalArgumentException("Nome do professor não pode estar vazio.");
        }

        professorDAO.adicionar(professor);
    }

    public List<Professor> listarTodosProfessores() {
        return professorDAO.listarTodos();
    }

    public Professor buscarPorId(int id) {
        return professorDAO.buscarPorId(id);
    }

    public void atualizarProfessor(Professor professor) {
        if (professor.getNomeProfessor() == null || professor.getNomeProfessor().isBlank()) {
            throw new IllegalArgumentException("Nome do professor não pode estar vazio.");
        }

        professorDAO.atualizar(professor);
    }

    public void removerProfessor(int id) {
        professorDAO.remover(id);
    }

    public Professor buscarOuCriarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do professor não pode estar vazio.");
        }

        Professor professor = professorDAO.buscarPorNome(nome);
        if (professor == null) {
            professor = new Professor();
            professor.setNomeProfessor(nome);
            professorDAO.adicionar(professor);
        }
        return professor;
    }
}