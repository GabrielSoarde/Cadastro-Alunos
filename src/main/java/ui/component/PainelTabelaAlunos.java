package ui.component;

import model.Aluno;
import model.Aula;
import service.AlunoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PainelTabelaAlunos extends JPanel {
    private JTable tabela;
    private DefaultTableModel modelo;
    private AlunoService alunoService;

    public PainelTabelaAlunos() {
        setLayout(new BorderLayout());

        alunoService = new AlunoService();

        String[] colunas = {"ID", "Nome", "Curso", "Telefone", "Dia da Aula", "Horario", "Professor"};

        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        for (Aluno aluno : alunoService.getAlunosAtivos()) {
            Aula aula = null;
            if (aluno.getAulas() != null && !aluno.getAulas().isEmpty()) {
                aula = aluno.getAulas().get(0);
            }

            modelo.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getCurso(),
                    aluno.getTelefone(),
                    aula != null ? aula.getDia() : "",
                    aula != null ? aula.getHorario() : "",
                    aula != null && aula.getProfessor() != null ? aula.getProfessor().getNomeProfessor() : ""
            });
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
