import helper.BancoHelper;
import ui.dialog.JanelaPrincipal;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
        new BancoHelper().criarTodasTabelas();
    }
}
