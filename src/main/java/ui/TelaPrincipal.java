package ui;

import model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class TelaPrincipal extends JFrame {
    private AlunoService alunoService = new AlunoService();
    private DefaultTableModel model;

    public TelaPrincipal() {
        setTitle("Cadastro de Alunos - Centro de Artes e Linguas - C.A.L -- Toledo & Moraes");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] colunas = {"ID", "Nome", "Curso", "Telefone", "Dia da Aula", "Horario", "Professor"};
        model = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JButton btnAdicionar = new JButton("Adicionar Aluno");
        JButton btnRemover = new JButton("Remover Aluno");
        JButton btnExportarAtivos = new JButton("Exportar Ativos");
        JButton btnExportarInativos = new JButton("Exportar Inativos");

        btnAdicionar.addActionListener(e -> adicionarAluno());
        btnRemover.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                Aluno aluno = alunoService.getAlunosAtivos().stream()
                        .filter(a -> a.getId() == id)
                        .findFirst().orElse(null);
                if (aluno != null) {
                    alunoService.removerAluno(aluno);
                    model.removeRow(row);
                }
                alunoService.salvarAlunos();
            }
        });

        btnExportarAtivos.addActionListener(e -> {
            try {
                GeradorExcel.exportar(alunoService.getAlunosAtivos(), "alunos_ativos.xlsx");
                JOptionPane.showMessageDialog(this, "Exportado com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar!");
            }
        });

        btnExportarInativos.addActionListener(e -> {
            try {
                GeradorExcel.exportar(alunoService.getAlunosInativos(), "alunos_inativos.xlsx");
                JOptionPane.showMessageDialog(this, "Exportado com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar!");
            }
        });

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);
        botoes.add(btnRemover);
        botoes.add(btnExportarAtivos);
        botoes.add(btnExportarInativos);

        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        for (Aluno aluno : alunoService.getAlunosAtivos()) {
            model.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getCurso(),
                    aluno.getTelefone(),
                    aluno.getAula().getDia(),
                    aluno.getAula().getHorario(),
                    aluno.getAula().getProfessor()
            });
        }
    }



    private void adicionarAluno() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String curso = JOptionPane.showInputDialog(this, "Curso:");
        String telefone = JOptionPane.showInputDialog(this, "Telefone:");
        String email = JOptionPane.showInputDialog(this, "Email:");
        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        String escolaridade = JOptionPane.showInputDialog(this, "Escolaridade:");
        String experiencia = JOptionPane.showInputDialog(this, "Experiência Musical:");
        String diaAula = JOptionPane.showInputDialog(this, "Dia Aula:");
        String horaAula = JOptionPane.showInputDialog(this, "Hora:");
        String professor = JOptionPane.showInputDialog(this, "Professor:");

        Aluno aluno = new Aluno(nome, curso, telefone, email, cpf, escolaridade, experiencia, diaAula, horaAula, professor);
        alunoService.adicionarAluno(aluno);
        model.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getCurso(), aluno.getTelefone(), aluno.getAula().getDia(), aluno.getAula().getHorario(), aluno.getAula().getProfessor()});
        alunoService.salvarAlunos();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}