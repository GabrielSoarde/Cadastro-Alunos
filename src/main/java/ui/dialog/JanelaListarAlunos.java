package ui.dialog;

import dao.AlunoDAO;
import model.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JanelaListarAlunos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private AlunoDAO alunoDAO = new AlunoDAO();

    public JanelaListarAlunos() {
        setTitle("Lista de Alunos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Curso", "CPF", "Ativo"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnToggleAtivo = new JButton("Ativar/Desativar");

        btnAtualizar.addActionListener(e -> carregarAlunos());
        btnToggleAtivo.addActionListener(e -> alternarStatusAluno());

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnToggleAtivo);

        add(painelBotoes, BorderLayout.SOUTH);

        carregarAlunos();
    }

    private void carregarAlunos() {
        modelo.setRowCount(0);
        List<Aluno> alunos = alunoDAO.findAll();
        for (Aluno aluno : alunos) {
            modelo.addRow(new Object[]{
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getCurso(),
                    aluno.getCpf(),
                    aluno.isAtivo() ? "Sim" : "Não"
            });
        }
    }

    private void alternarStatusAluno() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) modelo.getValueAt(linhaSelecionada, 0);
            Aluno aluno = alunoDAO.findById(id);

            if (aluno != null) {
                boolean novoStatus = !aluno.isAtivo();
                aluno.setAtivo(novoStatus);
                alunoDAO.update(aluno);
                carregarAlunos();

                JOptionPane.showMessageDialog(this,
                        "Aluno " + (novoStatus ? "ativado" : "desativado") + " com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para alterar o status.");
        }
    }
}
