package ui.component;

import model.Aluno;
import model.Aula;
import service.AlunoService;
import service.GeradorExcel;

import ui.component.PainelTabelaAlunos;
import ui.dialog.JanelaAdicionarAluno;
import ui.dialog.JanelaEditarAluno;
import ui.dialog.JanelaSelecionarProfessor;
import ui.dialog.JanelaListarAlunos;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PainelBotoes extends JPanel {

    public PainelBotoes(PainelTabelaAlunos painelTabela) {
        setLayout(new FlowLayout());

        JButton btnAdicionar = new JButton("Adicionar Aluno");
        JButton btnRemover = new JButton("Remover Aluno");
        JButton btnEditar = new JButton("Editar Aluno");
        JButton btnExportarAtivos = new JButton("Exportar Ativos");
        JButton btnRelatorioPorProfessor = new JButton("Relatório por Professor");
        JButton btnListarAlunos = new JButton("Listar Alunos");

        add(btnAdicionar);
        add(btnRemover);
        add(btnEditar);
        add(btnExportarAtivos);
        add(btnRelatorioPorProfessor);
        add(btnListarAlunos);

        AlunoService alunoService = painelTabela.getAlunoService();

        // Adicionar Aluno
        btnAdicionar.addActionListener(e -> {
            JanelaAdicionarAluno janela = new JanelaAdicionarAluno((Frame) SwingUtilities.getWindowAncestor(this));
            janela.setVisible(true);
            if (janela.foiSalvo()) {
                Aluno aluno = janela.getAluno();
                alunoService.cadastrarAluno(aluno);

                painelTabela.carregarDadosDoBanco();
            }
        });

        // Editar Aluno
        btnEditar.addActionListener(e -> {
            int row = painelTabela.getTabela().getSelectedRow();
            if (row >= 0) {
                int id = (int) painelTabela.getModelo().getValueAt(row, 0);
                Aluno aluno = alunoService.buscarPorId(id);

                if (aluno != null) {
                    JanelaEditarAluno janela = new JanelaEditarAluno((JFrame) SwingUtilities.getWindowAncestor(this), aluno);
                    janela.setVisible(true);

                    if (janela.foiSalvo()) {
                        alunoService.atualizarAluno(aluno);

                        painelTabela.getModelo().setValueAt(aluno.getNome(), row, 1);
                        painelTabela.getModelo().setValueAt(aluno.getCurso(), row, 2);
                        painelTabela.getModelo().setValueAt(aluno.getTelefone(), row, 3);

                        if (!aluno.getAulas().isEmpty()) {
                            var aula = aluno.getAulas().get(0);
                            painelTabela.getModelo().setValueAt(aula.getDia(), row, 4);
                            painelTabela.getModelo().setValueAt(aula.getHorario(), row, 5);
                            painelTabela.getModelo().setValueAt(aula.getProfessor().getNomeProfessor(), row, 6);
                        } else {
                            painelTabela.getModelo().setValueAt("", row, 4);
                            painelTabela.getModelo().setValueAt("", row, 5);
                            painelTabela.getModelo().setValueAt("", row, 6);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um aluno para editar.");
            }
        });

        // Remover Aluno
        btnRemover.addActionListener(e -> {
            int row = painelTabela.getTabela().getSelectedRow();
            if (row >= 0) {
                int id = (int) painelTabela.getModelo().getValueAt(row, 0);
                alunoService.removerAluno(id);
                painelTabela.carregarDadosDoBanco();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um aluno para remover.");
            }
        });

        btnExportarAtivos.addActionListener(e -> {
            try {
                String caminho = gerarCaminho("alunos_ativos.xlsx");

                List<Aluno> todosAlunos = alunoService.listarTodosAlunos();
                List<Aluno> alunosAtivos = todosAlunos.stream()
                        .filter(Aluno::isAtivo) // ou .filter(a -> a.getAtivo())
                        .toList();

                GeradorExcel.exportar(alunosAtivos, caminho);
                JOptionPane.showMessageDialog(null, "Exportado com sucesso na Área de Trabalho!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao exportar!");
            }
        });

        // Relatório por Professor
        btnRelatorioPorProfessor.addActionListener(e -> {
            List<Aluno> alunos = alunoService.listarTodosAlunos();
            JanelaSelecionarProfessor janela = new JanelaSelecionarProfessor(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    alunos
            );
            janela.setVisible(true);
        });

        // Listar Alunos - NOVO botão
        btnListarAlunos.addActionListener(e -> {
            JanelaListarAlunos janelaListar = new JanelaListarAlunos();
            janelaListar.setVisible(true);
        });
    }

    private String gerarCaminho(String nomeArquivo) {
        String userHome = System.getProperty("user.home");
        File desktop = new File(userHome + File.separator + "Desktop");
        if (!desktop.exists()) {
            desktop = new File(userHome + File.separator + "Área de Trabalho");
        }
        return desktop.getAbsolutePath() + File.separator + nomeArquivo;
    }
}
