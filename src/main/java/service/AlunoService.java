package service;

import dao.AlunoDAO;
import model.Aluno;
import model.Aula;
import model.Professor;

import java.util.ArrayList;
import java.util.List;

public class AlunoService {

    private final AlunoDAO alunoDAO;

    public AlunoService() {
        this.alunoDAO = new AlunoDAO();
    }

    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) throw new IllegalArgumentException("Aluno não pode ser nulo");
        if (aluno.getNome() == null || aluno.getNome().isEmpty()) throw new IllegalArgumentException("Nome é obrigatório");
        if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) throw new IllegalArgumentException("CPF é obrigatório");

        ProfessorService professorService = new ProfessorService();

        // Para cada aula, garante que o professor exista no banco e tenha ID correto
        for (Aula aula : aluno.getAulas()) {
            String nomeProf = aula.getProfessor() != null ? aula.getProfessor().getNomeProfessor() : null;
            if (nomeProf != null && !nomeProf.isBlank()) {
                Professor prof = professorService.buscarOuCriarPorNome(nomeProf);
                aula.setProfessor(prof);
                aula.setAluno(aluno);
            }
        }

        alunoDAO.insert(aluno);
    }

    public List<Aluno> listarAlunosAtivos() {
        return alunoDAO.findAlunosAtivos();
    }

    public List<Aluno> listarTodosAlunos() {
        return alunoDAO.findAll();
    }

    public Aluno buscarPorId(int id) {
        return alunoDAO.findById(id);
    }

    public void atualizarAluno(Aluno aluno) {
        if (aluno == null || aluno.getId() <= 0) throw new IllegalArgumentException("Aluno inválido para atualização");
        alunoDAO.update(aluno);
    }

    public void removerAluno(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido para remoção");
        alunoDAO.delete(id);
    }

    public List<Aluno> listarAlunosPorProfessor(String nomeProfessor) {
        List<Aluno> todosAlunos = alunoDAO.findAll(); // já carrega os alunos
        List<Aluno> filtrados = new ArrayList<>();

        for (Aluno aluno : todosAlunos) {
            for (Aula aula : aluno.getAulas()) {
                if (aula.getProfessor() != null &&
                        aula.getProfessor().getNomeProfessor().equalsIgnoreCase(nomeProfessor)) {
                    filtrados.add(aluno);
                    break;
                }
            }
        }

        return filtrados;
    }

}
