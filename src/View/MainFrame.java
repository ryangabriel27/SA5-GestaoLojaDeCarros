package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    private JLabel img;
    private JPanel panel, cards, menu, app;
    private JButton entrar, sair;

    public MainFrame() {
        super("Loja de Carros");
        setDefaultCloseOperation(2);
        CardLayout cl = new CardLayout();
        panel = new JPanel();
        cards = new JPanel();
        cards.setLayout(cl);
        // ---------------------*
        menu = new JPanel(new BorderLayout());
        img = new JLabel();
        ImageIcon iconMenu = new ImageIcon(getClass().getResource("imgs/menu.jpg"));
        img.setIcon(iconMenu);
        menu.add(img);
        entrar = new JButton("Entrar");
        menu.add(entrar, BorderLayout.SOUTH);
        cards.add(menu, "Menu");

        // ---------------------*
        app = new JPanel(new BorderLayout());
        JTabbedPane abas = new JTabbedPane();
        abas.add("Carros", new CarrosPanel());
        abas.add("Clientes", new ClientesPanel());
        abas.add("Vendas", new VendasPanel());
        sair = new JButton("Sair");
        app.add(abas);
        app.add(sair, BorderLayout.SOUTH);

        cards.add(app, "App");
        // ---------------------*
        panel.add(cards);
        add(panel);

        // ---------------------*
        // Tratamento de eventos:

        entrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bem-Vindo :)", null, JOptionPane.INFORMATION_MESSAGE);
                cl.show(cards, "App");
            }
        });

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                        "Editar", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    cl.show(cards, "Menu");
                }
            }
        });
        // ---------------------*
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub
                super.windowClosed(e);
            }
            
        });
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
