package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoHelper {

    private final String url = "jdbc:sqlite:alunos.db";

    public void criarTodasTabelas() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS professor (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nomeProfessor TEXT NOT NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS aluno (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT,
                    curso TEXT,
                    telefone TEXT,
                    email TEXT,
                    cpf TEXT,
                    escolaridade TEXT,
                    experiencia_musical TEXT,
                    ativo INTEGER DEFAULT 1
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS aula (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    dia TEXT NOT NULL,
                    horario TEXT NOT NULL,
                    aluno_id INTEGER NOT NULL,
                    professor_id INTEGER NOT NULL,
                    FOREIGN KEY(aluno_id) REFERENCES aluno(id),
                    FOREIGN KEY(professor_id) REFERENCES professor(id)
                );
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
