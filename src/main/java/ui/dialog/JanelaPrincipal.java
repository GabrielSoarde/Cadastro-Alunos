package ui.dialog;

import ui.component.PainelBotoes;
import ui.component.PainelTabelaAlunos;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private PainelTabelaAlunos painelTabela;
    private PainelBotoes painelBotoes;

    public JanelaPrincipal() {
        setTitle("Cadastro de Alunos - Centro de Artes e Linguas - C.A.L -- Toledo & Moraes");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        painelTabela = new PainelTabelaAlunos();
        painelBotoes = new PainelBotoes(painelTabela);

        add(painelTabela, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
}
