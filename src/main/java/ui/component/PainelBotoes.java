package ui.component;

import model.Aluno;
import model.Aula;
import service.GeradorExcel;
import ui.JanelaAdicionarAluno;
import ui.dialog.JanelaEditarAluno;
import ui.dialog.JanelaSelecionarProfessor;
//import util.GeradorExcel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class PainelBotoes extends JPanel {

    private java.time.DayOfWeek converterDia(String dia) {
        switch(dia.toUpperCase()) {
            case "SEGUNDA": return java.time.DayOfWeek.MONDAY;
            case "TERCA": case "TERÇA": return java.time.DayOfWeek.TUESDAY;
            case "QUARTA": return java.time.DayOfWeek.WEDNESDAY;
            case "QUINTA": return java.time.DayOfWeek.THURSDAY;
            case "SEXTA": return java.time.DayOfWeek.FRIDAY;
            case "SABADO": case "SÁBADO": return java.time.DayOfWeek.SATURDAY;
            case "DOMINGO": return java.time.DayOfWeek.SUNDAY;
            default: throw new IllegalArgumentException("Dia inválido: " + dia);
        }
    }

    public PainelBotoes(PainelTabelaAlunos painelTabela) {
        setLayout(new FlowLayout());

        JButton btnAdicionar = new JButton("Adicionar Aluno");
        JButton btnRemover = new JButton("Remover Aluno");
        JButton btnExportarAtivos = new JButton("Exportar Ativos");
        JButton btnExportarInativos = new JButton("Exportar Inativos");

        add(btnAdicionar);
        add(btnRemover);
        add(btnExportarAtivos);
        add(btnExportarInativos);

        btnAdicionar.addActionListener(e -> {
            JanelaAdicionarAluno janela = new JanelaAdicionarAluno((Frame) SwingUtilities.getWindowAncestor(this));
            janela.setVisible(true);
            if (janela.foiSalvo()) {
                Aluno aluno = janela.getAluno();
                painelTabela.getAlunoService().adicionarAluno(aluno);

                Aula primeiraAula = aluno.getAulas().get(0);

                painelTabela.getModelo().addRow(new Object[]{
                        aluno.getId(),
                        aluno.getNome(),
                        aluno.getCurso(),
                        aluno.getTelefone(),
                        primeiraAula.getDia(),
                        primeiraAula.getHorario(),
                        primeiraAula.getProfessor().getNomeProfessor()
                });

                painelTabela.getAlunoService().salvarAlunos();
            }
        });


        JButton btnEditar = new JButton("Editar Aluno");
        add(btnEditar);

        btnEditar.addActionListener(e -> {
            int row = painelTabela.getTabela().getSelectedRow();
            if (row >= 0) {
                int id = (int) painelTabela.getModelo().getValueAt(row, 0);
                Aluno aluno = painelTabela.getAlunoService().getAlunosAtivos().stream()
                        .filter(a -> a.getId() == id)
                        .findFirst().orElse(null);

                if (aluno != null) {
                    JanelaEditarAluno janela = new JanelaEditarAluno((JFrame) SwingUtilities.getWindowAncestor(this), aluno);
                    janela.setVisible(true);

                    if (janela.foiSalvo()) {

                        painelTabela.getModelo().setValueAt(aluno.getNome(), row, 1);
                        painelTabela.getModelo().setValueAt(aluno.getCurso(), row, 2);
                        painelTabela.getModelo().setValueAt(aluno.getTelefone(), row, 3);
                        painelTabela.getAlunoService().salvarAlunos();

                        if (!aluno.getAulas().isEmpty()) {
                            painelTabela.getModelo().setValueAt(
                                    aluno.getAulas().get(0).getProfessor().getNomeProfessor(), row, 6
                            );
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um aluno para editar.");
            }
        });



        btnRemover.addActionListener((ActionEvent e) -> {
            int row = painelTabela.getTabela().getSelectedRow();
            if (row >= 0) {
                int id = (int) painelTabela.getModelo().getValueAt(row, 0);
                Aluno aluno = painelTabela.getAlunoService().getAlunosAtivos().stream()
                        .filter(a -> a.getId() == id)
                        .findFirst().orElse(null);
                if (aluno != null) {
                    painelTabela.getAlunoService().removerAluno(aluno);
                    painelTabela.getModelo().removeRow(row);
                }
                painelTabela.getAlunoService().salvarAlunos();
            }
        });

        btnExportarAtivos.addActionListener(e -> {
            try {
                String userHome = System.getProperty("user.home");
                File desktop = new File(userHome + File.separator + "Desktop");
                if (!desktop.exists()) {
                    desktop = new File(userHome + File.separator + "Área de Trabalho"); // fallback em PT
                }

                String caminho = desktop.getAbsolutePath() + File.separator + "alunos_ativos.xlsx";
                GeradorExcel.exportar(painelTabela.getAlunoService().getAlunosAtivos(), caminho);
                JOptionPane.showMessageDialog(null, "Exportado com sucesso na Área de Trabalho!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao exportar!");
            }
        });

        btnExportarInativos.addActionListener(e -> {
            try {
                String userHome = System.getProperty("user.home");
                File desktop = new File(userHome + File.separator + "Desktop");
                if (!desktop.exists()) {
                    desktop = new File(userHome + File.separator + "Área de Trabalho"); // fallback em PT
                }

                String caminho = desktop.getAbsolutePath() + File.separator + "alunos_inativos.xlsx";
                GeradorExcel.exportar(painelTabela.getAlunoService().getAlunosAtivos(), caminho);
                JOptionPane.showMessageDialog(null, "Exportado com sucesso na Área de Trabalho!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao exportar!");
            }
        });

        JButton btnRelatorioPorProfessor = new JButton("Relatório por Professor");
        add(btnRelatorioPorProfessor);

        btnRelatorioPorProfessor.addActionListener(e -> {
            JanelaSelecionarProfessor janela = new JanelaSelecionarProfessor(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    painelTabela.getAlunoService().getAlunosAtivos()
            );
            janela.setVisible(true);
        });
    }
}
