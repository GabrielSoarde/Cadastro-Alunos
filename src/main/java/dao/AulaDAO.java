package dao;

import model.Aula;
import model.Professor;
import model.Aluno;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    private final String url = "jdbc:sqlite:alunos.db";

    public AulaDAO() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS aula (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dia TEXT NOT NULL,
                horario TEXT NOT NULL,
                aluno_id INTEGER NOT NULL,
                professor_id INTEGER NOT NULL,
                FOREIGN KEY(aluno_id) REFERENCES aluno(id),
                FOREIGN KEY(professor_id) REFERENCES professor(id)
            );
        """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Aula aula) {
        String sql = "INSERT INTO aula(dia, horario, aluno_id, professor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, aula.getDia().name());
            pstmt.setString(2, aula.getHorario().toString());
            pstmt.setInt(3, aula.getAluno().getId());
            pstmt.setInt(4, aula.getProfessor().getId());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aula.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aula> findByAlunoId(int alunoId) {
        List<Aula> aulas = new ArrayList<>();
        String sql = """
            SELECT a.id, a.dia, a.horario, a.aluno_id, a.professor_id,
                   p.nomeProfessor
            FROM aula a
            JOIN professor p ON a.professor_id = p.id
            WHERE a.aluno_id = ?
            ORDER BY a.dia, a.horario
        """;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alunoId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getInt("id"));
                aula.setDia(DayOfWeek.valueOf(rs.getString("dia")));
                aula.setHorario(LocalTime.parse(rs.getString("horario")));

                Professor professor = new Professor();
                professor.setId(rs.getInt("professor_id"));
                professor.setNomeProfessor(rs.getString("nomeProfessor"));
                aula.setProfessor(professor);

                // Só setar aluno com id aqui, para referência (pode setar objeto Aluno completo se quiser)
                Aluno aluno = new Aluno();
                aluno.setId(alunoId);
                aula.setAluno(aluno);

                aulas.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aulas;
    }
}
