package View;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Control.CarrosDAO;
import Control.ClientesDAO;
import Control.VendasControl;
import Control.VendasDAO;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import Model.Carros;
import Model.Clientes;
import Model.Vendas;

public class VendasPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable table;
    private int linhaSelecionada = -1;
    private List<Vendas> vendas = new ArrayList<>();
    private List<Carros> carros;
    private List<Clientes> clientes;
    private JComboBox<String> clientesComboBox, carrosComboBox;
    private JButton realizarVenda;
    private JTextField inputData, inputValor;
    private JScrollPane jSPane;

    public VendasPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel
        // ---------------------*

        clientesComboBox = new JComboBox<>();
        carrosComboBox = new JComboBox<>();
        inputData = new JTextField(10);
        inputValor = new JTextField(10);
        realizarVenda = new JButton("Registrar venda");
        // ---------------------*

        carrosComboBox.addItem("Selecione um carro");
        carros = new CarrosDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca() + " " + carro.getModelo() + " - PLACA: " + carro.getPlaca());
        }

        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        // criar um método para atualizar o combobox
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNomeCompleto() + " - CPF: " + cliente.getCpf());
        }
        // -------------------*
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Registrar vendas"));
        add(title);
        // --------------------------*
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 2, 4)); // InputPanel
        inputPanel.add(new JLabel("Cliente: "));
        inputPanel.add(clientesComboBox);
        inputPanel.add(new JLabel("Carro: "));
        inputPanel.add(carrosComboBox);
        inputPanel.add(new JLabel("Digite a data de hoje:"));
        inputPanel.add(inputData);
        inputPanel.add(new JLabel("Digite o valor da venda:"));
        inputPanel.add(inputValor);
        add(inputPanel);

        // --------------------------*
        JPanel buttonPanel = new JPanel(); // Painel de botões
        buttonPanel.add(realizarVenda);
        add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        // --------------------------*
        jSPane = new JScrollPane(); // Criando um scrollPane
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Cliente", "Carro", "Data", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        // Cria o banco de dados caso não tenha sido criado
        new VendasDAO().criaTabela();
        // incluindo elementos do banco na criação do painel
        atualizarTabela();
        // ---------------------*
        // Estilização:
        realizarVenda.setBackground(new Color(46, 128, 32));
        realizarVenda.setForeground(new Color(255, 255, 255));
        // --------------------------*
        // Tratamento de eventos:
        VendasControl control = new VendasControl(vendas, tableModel, table);
        realizarVenda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String carro = String.valueOf(carrosComboBox.getSelectedItem());
                    String cliente = String.valueOf(clientesComboBox.getSelectedItem());
                    if (!carro.equals("Selecione um carro") && !cliente.equals("Selecione um cliente")
                            && !inputData.getText().isEmpty() && !inputValor.getText().isEmpty()) {
                        if (control.validarData(inputData.getText()) && control.validarValor(inputValor.getText())) {
                            control.cadastrarVenda(cliente, carro, inputValor.getText(), inputData.getText());
                            inputData.setText("");
                            inputValor.setText("");
                            carrosComboBox.setSelectedItem("Selecione um carro");
                            clientesComboBox.setSelectedItem("Selecione um cliente");
                        } else {
                            JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "ERRO!",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "ERRO!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    JOptionPane.showMessageDialog(null,
                            "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                            JOptionPane.WARNING_MESSAGE);
                }

            }

        });

        clientesComboBox.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                atualizaComboBoxClientes();
            }

        });
        carrosComboBox.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                atualizaComboBoxCarros();
            }

        });

    }

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos();
        // Obtém os clientes atualizados do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { venda.getCarro(), venda.getCliente(), venda.getData(),
                    venda.getValor() });
        }

    }

    public void atualizaComboBoxCarros() {
        carrosComboBox.removeAllItems();
        carrosComboBox.addItem("Selecione um carro");
        carros = new CarrosDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca() + " " + carro.getModelo() + " - PLACA: " + carro.getPlaca());
        }
    }

    public void atualizaComboBoxClientes() {
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        // criar um método para atualizar o combobox
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNomeCompleto() + " - CPF: " + cliente.getCpf());
        }
    }
}
