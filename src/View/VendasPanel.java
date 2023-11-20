package View;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.CarrosDAO;
import Control.ClientesDAO;

import java.awt.BorderLayout;
import java.util.Vector;

public class VendasPanel extends JPanel {
    private JPanel mainPanel;
    private JComboBox clientes, carros;
    private JButton realizarVenda;
    

    public VendasPanel() {
        super();

        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        clientes = new JComboBox<>();
        carros = new JComboBox<>();
        realizarVenda = new JButton("Realizar venda");
        mainPanel.add(new JLabel("Cliente:"));
        mainPanel.add(clientes);
        mainPanel.add(new JLabel("Carro:"));
        mainPanel.add(carros);
        mainPanel.add(realizarVenda);

    }
}
