package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Aluno;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {

    private List<Aluno> alunosAtivos;
    private List<Aluno> alunosInativos;

    private static final String PASTA_DADOS = "data/";
    private static final String ARQUIVO_ATIVOS = PASTA_DADOS + "alunos_ativos.json";
    private static final String ARQUIVO_INATIVOS = PASTA_DADOS + "alunos_inativos.json";

    private static int proximoId = 1;

    private final ObjectMapper mapper;

    public AlunoService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.alunosAtivos = carregarLista(ARQUIVO_ATIVOS);
        this.alunosInativos = carregarLista(ARQUIVO_INATIVOS);
        atualizarProximoId();
    }

    public void adicionarAluno(Aluno aluno) {
        aluno.setId(proximoId++);
        alunosAtivos.add(aluno);
        salvarAlunos();
    }

    public void removerAluno(Aluno aluno) {
        alunosAtivos.remove(aluno);
        alunosInativos.add(aluno);
        salvarAlunos();
    }

    public List<Aluno> getAlunosAtivos() {
        return alunosAtivos;
    }

    public List<Aluno> getAlunosInativos() {
        return alunosInativos;
    }

    public void salvarAlunos() {
        salvarLista(alunosAtivos, ARQUIVO_ATIVOS);
        salvarLista(alunosInativos, ARQUIVO_INATIVOS);
    }

    private void salvarLista(List<Aluno> lista, String caminho) {
        try {
            File arquivo = new File(caminho);
            File diretorio = arquivo.getParentFile();

            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs(); // Cria o diretório 'data/' se não existir
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar JSON: " + caminho);
            e.printStackTrace();
        }
    }

    private List<Aluno> carregarLista(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(arquivo, new TypeReference<List<Aluno>>() {});
        } catch (IOException e) {
            System.err.println("Erro ao carregar JSON: " + caminho);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void atualizarProximoId() {
        int maiorId = 0;
        for (Aluno a : alunosAtivos) {
            if (a.getId() > maiorId) maiorId = a.getId();
        }
        for (Aluno a : alunosInativos) {
            if (a.getId() > maiorId) maiorId = a.getId();
        }
        proximoId = maiorId + 1;
    }
}
