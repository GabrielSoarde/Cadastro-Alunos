# 🎼 Sistema de Cadastro de Alunos - Escola de Música

Este projeto é um sistema desktop em Java para gerenciamento de alunos de uma escola de música. O sistema permite cadastrar, editar, remover e listar alunos, associar aulas com professores e gerar relatórios em Excel.

## 🧰 Tecnologias Utilizadas

- Java 17+
- SQLite (banco de dados local)
- Swing (interface gráfica)
- Apache POI (para geração de relatórios em Excel)
- Gradle (opcional, se estiver usando)

---

## 🧠 Funcionalidades

✅ Cadastrar aluno com:
- Nome, Curso, Telefone, Email, CPF, Escolaridade, Experiência Musical  
- Dia da aula, Horário e Professor  
- Validação de campos obrigatórios

✅ Editar e remover alunos

✅ Formatação automática de:
- Telefone: `(xx) xxxxx-xxxx`  
- CPF: `xxx.xxx.xxx-xx`

✅ Marcar aluno como **Ativo** ou **Inativo**
- Alunos ativos aparecem em **verde** na tabela
- Alunos inativos aparecem em **vermelho**

✅ Listar alunos por professor

✅ Geração de relatórios Excel:
- Lista completa de alunos
- Lista de alunos por professor

---

## 📁 Estrutura do Projeto

```
src/
 ├── dao/                # Acesso ao banco de dados (AlunoDAO, ProfessorDAO)
 ├── model/              # Classes modelo (Aluno, Aula, Matricula, Professor)
 ├── service/            # Lógica de negócio (AlunoService, ProfessorService)
 └── ui/
      ├── component/     # Painéis reutilizáveis da interface
      └── dialog/        # Janelas principais do sistema
```

---

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. Compile e execute a classe `JanelaPrincipal` (ou use seu IDE preferido como IntelliJ ou Eclipse).

3. O banco SQLite (`alunos.db`) será criado automaticamente na raiz do projeto ao rodar a aplicação.

---

## 📊 Capturas de Tela

> (Você pode inserir aqui prints da interface gráfica e dos relatórios em Excel.)

---

## 📌 Requisitos

- JDK 17 ou superior
- (Opcional) IntelliJ IDEA, Eclipse ou NetBeans

---

## 🤝 Contribuição

Pull requests são bem-vindos! Para mudanças maiores, abra uma issue para discutirmos o que você gostaria de modificar.
