package ui.dialog;

import model.Aluno;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JanelaSelecionarProfessor extends JDialog {
    private JComboBox<String> comboProfessores;
    private JButton btnGerar;
    private List<Aluno> alunos;

    public JanelaSelecionarProfessor(JFrame owner, List<Aluno> alunos) {
        super(owner, "Selecionar Professor", true);
        this.alunos = alunos;

        setSize(400, 150);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);

        Set<String> nomesProfessores = alunos.stream()
                .flatMap(aluno -> aluno.getAulas().stream())
                .map(aula -> aula.getProfessor().getNomeProfessor())
                .collect(Collectors.toSet());

        comboProfessores = new JComboBox<>(nomesProfessores.toArray(new String[0]));
        btnGerar = new JButton("Gerar Relatório");

        JPanel painelCentro = new JPanel(new GridLayout(2, 1));
        painelCentro.add(new JLabel("Selecione o professor:"));
        painelCentro.add(comboProfessores);

        add(painelCentro, BorderLayout.CENTER);
        add(btnGerar, BorderLayout.SOUTH);

        btnGerar.addActionListener(e -> {
            String nomeSelecionado = (String) comboProfessores.getSelectedItem();
            if (nomeSelecionado != null) {
                List<Aluno> doProfessor = alunos.stream()
                        .filter(a -> a.getAulas().stream()
                                .anyMatch(aula -> aula.getProfessor().getNomeProfessor().equals(nomeSelecionado)))
                        .collect(Collectors.toList());

                new JanelaRelatorioProfessor((JFrame) getOwner(), nomeSelecionado, doProfessor).setVisible(true);

            }
        });
    }
}
