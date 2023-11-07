package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

    public MainFrame() {
        super("Loja de Carros");
        setDefaultCloseOperation(2);
        JTabbedPane abas = new JTabbedPane();
        abas.add("Carros", new CarrosPanel());
        add(abas);
    }

    public void run(){
        pack();
        setVisible(true);
    }
}
