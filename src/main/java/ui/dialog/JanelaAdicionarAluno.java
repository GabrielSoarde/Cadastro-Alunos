package ui.dialog;

import model.Aluno;
import model.Aula;
import model.Professor;
import service.AlunoService;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class JanelaAdicionarAluno extends JDialog {

    private final AlunoService alunoService = new AlunoService();

    private JTextField tfNome, tfCurso, tfTelefone, tfEmail, tfCpf, tfEscolaridade, tfExperiencia;
    private JTextField tfDiaAula, tfHorarioAula, tfProfessor;

    private boolean salvo = false;
    private Aluno aluno;

    public JanelaAdicionarAluno(Frame owner) {
        super(owner, "Adicionar Aluno", true);
        setLayout(new GridLayout(11, 2, 5, 5));

        inicializarCampos();
        adicionarComponentes();
        configurarBotoes(owner);

        pack();
        setLocationRelativeTo(owner);
    }

    private void inicializarCampos() {
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
    }

    private void adicionarComponentes() {
        add(new JLabel("Nome:"));            add(tfNome);
        add(new JLabel("Curso:"));           add(tfCurso);
        add(new JLabel("Telefone:"));        add(tfTelefone);
        add(new JLabel("Email:"));           add(tfEmail);
        add(new JLabel("CPF:"));             add(tfCpf);
        add(new JLabel("Escolaridade:"));    add(tfEscolaridade);
        add(new JLabel("Experiência Musical:")); add(tfExperiencia);
        add(new JLabel("Dia da Aula (ex: SEGUNDA):")); add(tfDiaAula);
        add(new JLabel("Hora (HH:mm):"));    add(tfHorarioAula);
        add(new JLabel("Professor:"));       add(tfProfessor);
    }

    private void configurarBotoes(Frame owner) {
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvarAluno());
        btnCancelar.addActionListener(e -> {
            salvo = false;
            dispose();
        });

        add(btnSalvar);
        add(btnCancelar);
    }

    private void salvarAluno() {
        try {
            String nome = tfNome.getText().trim();
            String curso = tfCurso.getText().trim();
            String telefone = formatarTelefone(tfTelefone.getText().trim());
            String email = tfEmail.getText().trim();
            String cpf = formatarCpf(tfCpf.getText().trim());
            String escolaridade = tfEscolaridade.getText().trim();
            String experiencia = tfExperiencia.getText().trim();
            String diaTexto = tfDiaAula.getText().trim();
            String horarioTexto = tfHorarioAula.getText().trim();
            String nomeProfessor = tfProfessor.getText().trim();

            if (nome.isEmpty() || curso.isEmpty() || telefone.isEmpty() || email.isEmpty() || cpf.isEmpty() ||
                    diaTexto.isEmpty() || horarioTexto.isEmpty() || nomeProfessor.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
            }

            DayOfWeek dia = converterDia(diaTexto);
            LocalTime horario = LocalTime.parse(horarioTexto);

            Professor professor = new Professor(nomeProfessor);
            Aula aula = new Aula(dia, horario);
            aula.setProfessor(professor);

            aluno = new Aluno(nome, curso, telefone, email, cpf, escolaridade, experiencia, true);
            aluno.addAula(aula);

            salvo = true;
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatarTelefone(String input) {
        String digits = input.replaceAll("\\D", "");
        if (digits.length() != 11) throw new IllegalArgumentException("Telefone inválido.");
        return String.format("(%s) %s-%s", digits.substring(0, 2), digits.substring(2, 7), digits.substring(7));
    }

    private String formatarCpf(String input) {
        String digits = input.replaceAll("\\D", "");
        if (digits.length() != 11) throw new IllegalArgumentException("CPF inválido.");
        return String.format("%s.%s.%s-%s", digits.substring(0, 3), digits.substring(3, 6),
                digits.substring(6, 9), digits.substring(9));
    }

    private DayOfWeek converterDia(String dia) {
        switch (dia.toUpperCase()) {
            case "SEGUNDA": return DayOfWeek.MONDAY;
            case "TERCA": case "TERÇA": return DayOfWeek.TUESDAY;
            case "QUARTA": return DayOfWeek.WEDNESDAY;
            case "QUINTA": return DayOfWeek.THURSDAY;
            case "SEXTA": return DayOfWeek.FRIDAY;
            case "SABADO": case "SÁBADO": return DayOfWeek.SATURDAY;
            case "DOMINGO": return DayOfWeek.SUNDAY;
            default: throw new IllegalArgumentException("Dia inválido: " + dia);
        }
    }

    public boolean foiSalvo() {
        return salvo;
    }

    public Aluno getAluno() {
        return aluno;
    }
}
