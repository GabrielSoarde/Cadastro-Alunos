package dao;

import model.Aluno;
import model.Aula;
import model.Professor;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private final String url = "jdbc:sqlite:alunos.db";

    public AlunoDAO() {
        criarTabela();
    }

    private void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS aluno (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT, curso TEXT, telefone TEXT, email TEXT," +
                "cpf TEXT, escolaridade TEXT, experiencia_musical TEXT," +
                "ativo INTEGER DEFAULT 1)";  // 1 = ativo, 0 = inativo
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Aluno aluno) {
        String sql = "INSERT INTO aluno(nome, curso, telefone, email, cpf, escolaridade, experiencia_musical, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getCurso());
            pstmt.setString(3, aluno.getTelefone());
            pstmt.setString(4, aluno.getEmail());
            pstmt.setString(5, aluno.getCpf());
            pstmt.setString(6, aluno.getEscolaridade());
            pstmt.setString(7, aluno.getExperienciaMusical());
            pstmt.setInt(8, Boolean.TRUE.equals(aluno.isAtivo()) ? 1 : 0);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                }
            }

            AulaDAO aulaDAO = new AulaDAO();
            for (Aula aula : aluno.getAulas()) {
                aula.setAluno(aluno);
                aulaDAO.insert(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> findAll() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            AulaDAO aulaDAO = new AulaDAO();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEmail(rs.getString("email"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEscolaridade(rs.getString("escolaridade"));
                aluno.setExperienciaMusical(rs.getString("experiencia_musical"));
                aluno.setAtivo(rs.getInt("ativo") == 1);

                List<Aula> aulas = aulaDAO.findByAlunoId(aluno.getId());
                aluno.setAulas(aulas);  // Aqui associa as aulas!

                alunos.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    public Aluno findById(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEmail(rs.getString("email"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEscolaridade(rs.getString("escolaridade"));
                aluno.setExperienciaMusical(rs.getString("experiencia_musical"));
                aluno.setAtivo(rs.getInt("ativo") == 1);
                return aluno;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, curso = ?, telefone = ?, email = ?, cpf = ?, escolaridade = ?, experiencia_musical = ?, ativo = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getCurso());
            pstmt.setString(3, aluno.getTelefone());
            pstmt.setString(4, aluno.getEmail());
            pstmt.setString(5, aluno.getCpf());
            pstmt.setString(6, aluno.getEscolaridade());
            pstmt.setString(7, aluno.getExperienciaMusical());
            pstmt.setInt(8, Boolean.TRUE.equals(aluno.isAtivo()) ? 1 : 0);
            pstmt.setInt(9, aluno.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> findAlunosAtivos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno WHERE ativo = 1";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEmail(rs.getString("email"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEscolaridade(rs.getString("escolaridade"));
                aluno.setExperienciaMusical(rs.getString("experiencia_musical"));
                aluno.setAtivo(rs.getInt("ativo") == 1);
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }

    public List<Aluno> listarPorProfessor(String nomeProfessor) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = """
        SELECT DISTINCT al.id, al.nome, al.curso, al.telefone, al.email, al.cpf, al.escolaridade, al.experiencia_musical, al.ativo
        FROM aluno al
        JOIN aula au ON au.aluno_id = al.id
        JOIN professor p ON p.id = au.professor_id
        WHERE p.nomeProfessor = ?
        ORDER BY al.nome
    """;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeProfessor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEmail(rs.getString("email"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEscolaridade(rs.getString("escolaridade"));
                aluno.setExperienciaMusical(rs.getString("experiencia_musical"));
                aluno.setAtivo(rs.getInt("ativo") == 1);
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }
}
