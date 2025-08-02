package ui.component;

import model.Aluno;
import service.AlunoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelTabelaAlunos extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private AlunoService alunoService;

    public PainelTabelaAlunos() {
        this.alunoService = new AlunoService();

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{
                "ID", "Nome", "Curso", "Telefone", "Dia", "Horário", "Professor"
        }, 0);

        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);

        carregarDadosDoBanco();
    }

    public void carregarDadosDoBanco() {
        modelo.setRowCount(0); // Limpa a tabela
        List<Aluno> alunos = alunoService.listarTodosAlunos(); // Busca no banco

        // Debug para verificar se as aulas e professores estão vindo
        for (Aluno aluno : alunos) {
            System.out.println("Aluno: " + aluno.getNome());
            if (aluno.getAulas().isEmpty()) {
                System.out.println("  Sem aulas");
            } else {
                for (var aula : aluno.getAulas()) {
                    System.out.println("  Aula: dia=" + aula.getDia() + ", horário=" + aula.getHorario()
                            + ", professor=" + (aula.getProfessor() != null ? aula.getProfessor().getNomeProfessor() : "null"));
                }
            }
        }

        // Depois continue carregando a tabela normalmente
        for (Aluno aluno : alunos) {
            if (!aluno.getAulas().isEmpty()) {
                var aula = aluno.getAulas().get(0); // Pega a primeira aula
                modelo.addRow(new Object[]{
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getCurso(),
                        aluno.getTelefone(),
                        aula.getDia(),
                        aula.getHorario(),
                        aula.getProfessor().getNomeProfessor()
                });
            } else {
                modelo.addRow(new Object[]{
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getCurso(),
                        aluno.getTelefone(),
                        "", "", ""
                });
            }
        }
    }

    public JTable getTabela() {
        return tabela;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public AlunoService getAlunoService() {
        return alunoService;
    }
}
