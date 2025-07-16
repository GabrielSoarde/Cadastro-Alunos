package service;

import model.Aluno;
import model.Aula;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeradorExcel {
    public static void exportar(List<Aluno> alunos, String caminhoArquivo) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(caminhoArquivo)) {

            Sheet sheet = workbook.createSheet("Alunos");

            Row header = sheet.createRow(0);
            String[] colunas = {"ID", "Matrícula", "Nome", "Curso", "Telefone", "Email", "CPF", "Escolaridade",
                    "Experiência", "Professor", "Dia Aula", "Horario"};

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(colunas[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            int rowNum = 1;
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

            for (Aluno aluno : alunos) {
                List<Aula> aulas = aluno.getAulas();

                if (aulas == null || aulas.isEmpty()) {
                    Row row = sheet.createRow(rowNum++);
                    preencherLinha(row, aluno, null, null, null);
                } else {
                    for (Aula aula : aulas) {
                        Row row = sheet.createRow(rowNum++);
                        String professorNome = (aula.getProfessor() != null) ? aula.getProfessor().getNomeProfessor() : "";
                        String dia = (aula.getDia() != null) ? aula.getDia().toString() : "";
                        String horario = (aula.getHorario() != null) ? aula.getHorario().format(horaFormatter) : "";

                        preencherLinha(row, aluno, professorNome, dia, horario);
                    }
                }
            }

            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
        }
    }

    private static void preencherLinha(Row row, Aluno aluno, String professor, String dia, String horario) {
        row.createCell(0).setCellValue(aluno.getId());
        row.createCell(1).setCellValue(aluno.getMatricula().getNumeroMatricula());
        row.createCell(2).setCellValue(aluno.getNome());
        row.createCell(3).setCellValue(aluno.getCurso());
        row.createCell(4).setCellValue(aluno.getTelefone());
        row.createCell(5).setCellValue(aluno.getEmail());
        row.createCell(6).setCellValue(aluno.getCpf());
        row.createCell(7).setCellValue(aluno.getEscolaridade());
        row.createCell(8).setCellValue(aluno.getExperienciaMusical());
        row.createCell(9).setCellValue(professor != null ? professor : "");
        row.createCell(10).setCellValue(dia != null ? dia : "");
        row.createCell(11).setCellValue(horario != null ? horario : "");
    }
}
