package ui.dialog;

import model.Aluno;
import model.Aula;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JanelaRelatorioProfessor extends JDialog {

    public JanelaRelatorioProfessor(JFrame owner, String nomeProfessor, List<Aluno> alunos) {
        super(owner, "Relatório", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("ALUNOS DO PROFESSOR ").append(nomeProfessor.toUpperCase()).append(":\n\n");

        for (Aluno aluno : alunos) {
            sb.append("ID: ").append(aluno.getId()).append("\n");
            sb.append("Nome: ").append(aluno.getNome()).append("\n");
            sb.append("Curso: ").append(aluno.getCurso()).append("\n");
            sb.append("Telefone: ").append(aluno.getTelefone()).append("\n");
            sb.append("Email: ").append(aluno.getEmail()).append("\n");

            if (!aluno.getAulas().isEmpty()) {
                Aula aula = aluno.getAulas().get(0);
                sb.append("Dia da Aula: ").append(aula.getDia()).append("\n");
                sb.append("Horário: ").append(aula.getHorario()).append("\n");
            }
            sb.append("---------------\n");
        }

        areaTexto.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(areaTexto);
        add(scroll, BorderLayout.CENTER);
    }
}
