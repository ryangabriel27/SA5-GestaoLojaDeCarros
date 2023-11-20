package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Model.Carros;

public class MainFrame extends JFrame {
    BufferedImage img = null;
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

        // try {
        //     img = ImageIO.read(new File("menu.jpg"));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // JLabel label = new JLabel();
        // label.setText("teste");
        // label.setForeground(Color.white);
        // label.setHorizontalTextPosition(JLabel.CENTER);

        // Image dimg = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);

        // ImageIcon imageIcon = new ImageIcon(dimg);
        // label.setIcon(imageIcon);

        // menu.add(label, BorderLayout.CENTER);
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
                cl.show(cards, "App");
            }
        });

        sair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "Menu");
            }
        });
        // ---------------------*
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
