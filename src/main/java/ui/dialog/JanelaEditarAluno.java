package ui.dialog;

import model.Aluno;
import model.Aula;

import javax.swing.*;
import java.awt.*;

public class JanelaEditarAluno extends JDialog {

    private JTextField txtNome, txtCurso, txtTelefone, txtEmail, txtCpf, txtEscolaridade, txtExperiencia, txtNomeProfessor;
    private JCheckBox chkAtivo;
    private boolean salvo = false;

    public JanelaEditarAluno(JFrame parent, Aluno aluno) {
        super(parent, "Editar Aluno", true);
        setLayout(new GridLayout(10, 2, 5, 5));

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
        chkAtivo = new JCheckBox("Ativo", aluno.isAtivo());

        add(new JLabel("Nome:"));               add(txtNome);
        add(new JLabel("Curso:"));              add(txtCurso);
        add(new JLabel("Telefone:"));           add(txtTelefone);
        add(new JLabel("Email:"));              add(txtEmail);
        add(new JLabel("CPF:"));                add(txtCpf);
        add(new JLabel("Escolaridade:"));       add(txtEscolaridade);
        add(new JLabel("Experiência Musical:")); add(txtExperiencia);
        add(new JLabel("Professor:"));          add(txtNomeProfessor);
        add(new JLabel("Status:"));             add(chkAtivo);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String curso = txtCurso.getText().trim();
                String telefone = formatarTelefone(txtTelefone.getText().trim());
                String email = txtEmail.getText().trim();
                String cpf = formatarCpf(txtCpf.getText().trim());
                String nomeProfessor = txtNomeProfessor.getText().trim();

                if (nome.isEmpty() || curso.isEmpty() || telefone.isEmpty() ||
                        email.isEmpty() || cpf.isEmpty() || nomeProfessor.isEmpty()) {
                    throw new IllegalArgumentException("Campos obrigatórios não preenchidos.");
                }

                aluno.setNome(nome);
                aluno.setCurso(curso);
                aluno.setTelefone(telefone);
                aluno.setEmail(email);
                aluno.setCpf(cpf);
                aluno.setEscolaridade(txtEscolaridade.getText().trim());
                aluno.setExperienciaMusical(txtExperiencia.getText().trim());
                aluno.setAtivo(chkAtivo.isSelected());

                if (!aluno.getAulas().isEmpty()) {
                    Aula aula = aluno.getAulas().get(0);
                    aula.getProfessor().setNomeProfessor(nomeProfessor);
                }

                salvo = true;
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
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

    private String formatarTelefone(String input) {
        String digits = input.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new IllegalArgumentException("Telefone deve conter 11 dígitos.");
        }
        return String.format("(%s) %s-%s", digits.substring(0, 2), digits.substring(2, 7), digits.substring(7));
    }

    private String formatarCpf(String input) {
        String digits = input.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos.");
        }
        return String.format("%s.%s.%s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 9),
                digits.substring(9));
    }
}
