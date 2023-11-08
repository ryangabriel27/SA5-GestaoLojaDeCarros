package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Model.Carros;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Loja de Carros");
        setDefaultCloseOperation(2);
        JTabbedPane abas = new JTabbedPane();
        abas.add("Carros", new CarrosPanel());
        add(abas);
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
