package ui.dialog;

import model.Aluno;
import model.Aula;

import javax.swing.*;
import java.awt.*;

public class JanelaEditarAluno extends JDialog {

    private JTextField txtNome, txtCurso, txtTelefone, txtEmail, txtCpf, txtEscolaridade, txtExperiencia, txtNomeProfessor;
    private boolean salvo = false;

    public JanelaEditarAluno(JFrame parent, Aluno aluno) {
        super(parent, "Editar Aluno", true);
        setLayout(new GridLayout(9, 2, 5, 5));

        txtNome = new JTextField(aluno.getNome());
        txtCurso = new JTextField(aluno.getCurso());
        txtTelefone = new JTextField(aluno.getTelefone());
        txtEmail = new JTextField(aluno.getEmail());
        txtCpf = new JTextField(aluno.getCpf());
        txtEscolaridade = new JTextField(aluno.getEscolaridade());
        txtExperiencia = new JTextField(aluno.getExperienciaMusical());
        txtNomeProfessor = new JTextField(
                aluno.getAulas().isEmpty() ? "" : aluno.getAulas().get(0).getProfessor().getNomeProfessor()
        );

        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("Curso:"));
        add(txtCurso);
        add(new JLabel("Telefone:"));
        add(txtTelefone);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("CPF:"));
        add(txtCpf);
        add(new JLabel("Escolaridade:"));
        add(txtEscolaridade);
        add(new JLabel("Experiência Musical:"));
        add(txtExperiencia);
        add(new JLabel("Professor:"));
        add(txtNomeProfessor);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            aluno.setNome(txtNome.getText());
            aluno.setCurso(txtCurso.getText());
            aluno.setTelefone(txtTelefone.getText());
            aluno.setEmail(txtEmail.getText());
            aluno.setCpf(txtCpf.getText());
            aluno.setEscolaridade(txtEscolaridade.getText());
            aluno.setExperienciaMusical(txtExperiencia.getText());

            if (!aluno.getAulas().isEmpty()) {
                Aula aula = aluno.getAulas().get(0);
                aula.getProfessor().setNomeProfessor(txtNomeProfessor.getText());
            }

            salvo = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        add(btnSalvar);
        add(btnCancelar);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean foiSalvo() {
        return salvo;
    }
}
