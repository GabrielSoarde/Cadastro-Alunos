package ui;

import model.Aluno;
import model.Aula;
import model.Professor;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class JanelaAdicionarAluno extends JDialog {

    private JTextField tfNome, tfCurso, tfTelefone, tfEmail, tfCpf, tfEscolaridade, tfExperiencia;
    private JTextField tfDiaAula, tfHorarioAula, tfProfessor;
    private boolean salvo = false;
    private Aluno aluno;

    public JanelaAdicionarAluno(Frame owner) {
        super(owner, "Adicionar Aluno", true);
        setLayout(new GridLayout(11, 2, 5, 5));

        tfNome = new JTextField();
        tfCurso = new JTextField();
        tfTelefone = new JTextField();
        tfEmail = new JTextField();
        tfCpf = new JTextField();
        tfEscolaridade = new JTextField();
        tfExperiencia = new JTextField();

        tfDiaAula = new JTextField();
        tfHorarioAula = new JTextField();
        tfProfessor = new JTextField();

        add(new JLabel("Nome:"));
        add(tfNome);
        add(new JLabel("Curso:"));
        add(tfCurso);
        add(new JLabel("Telefone:"));
        add(tfTelefone);
        add(new JLabel("Email:"));
        add(tfEmail);
        add(new JLabel("CPF:"));
        add(tfCpf);
        add(new JLabel("Escolaridade:"));
        add(tfEscolaridade);
        add(new JLabel("Experiência Musical:"));
        add(tfExperiencia);
        add(new JLabel("Dia da Aula (ex: SEGUNDA):"));
        add(tfDiaAula);
        add(new JLabel("Hora (HH:mm):"));
        add(tfHorarioAula);
        add(new JLabel("Professor:"));
        add(tfProfessor);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> {
            try {
                String nome = tfNome.getText().trim();
                String curso = tfCurso.getText().trim();
                String telefone = tfTelefone.getText().trim();
                String email = tfEmail.getText().trim();
                String cpf = tfCpf.getText().trim();
                String escolaridade = tfEscolaridade.getText().trim();
                String experiencia = tfExperiencia.getText().trim();

                DayOfWeek dia = converterDia(tfDiaAula.getText().trim());
                LocalTime horario = LocalTime.parse(tfHorarioAula.getText().trim());

                Aula aula = new Aula(dia, horario);
                Professor professor = new Professor(0, tfProfessor.getText().trim());
                aula.setProfessor(professor);

                aluno = new Aluno(nome, curso, telefone, email, cpf, escolaridade, experiencia);
                aluno.addAula(aula);

                salvo = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar aluno: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> {
            salvo = false;
            dispose();
        });

        add(btnSalvar);
        add(btnCancelar);

        pack();
        setLocationRelativeTo(owner);
    }

    private DayOfWeek converterDia(String dia) {
        switch (dia.toUpperCase()) {
            case "SEGUNDA":
                return DayOfWeek.MONDAY;
            case "TERCA":
            case "TERÇA":
                return DayOfWeek.TUESDAY;
            case "QUARTA":
                return DayOfWeek.WEDNESDAY;
            case "QUINTA":
                return DayOfWeek.THURSDAY;
            case "SEXTA":
                return DayOfWeek.FRIDAY;
            case "SABADO":
            case "SÁBADO":
                return DayOfWeek.SATURDAY;
            case "DOMINGO":
                return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Dia inválido: " + dia);
        }
    }

    public boolean foiSalvo() {
        return salvo;
    }

    public Aluno getAluno() {
        return aluno;
    }
}
