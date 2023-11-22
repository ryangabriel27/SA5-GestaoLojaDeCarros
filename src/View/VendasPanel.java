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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
    private JPanel mainPanel;
    private JComboBox<String> clientesComboBox, carrosComboBox;
    private JButton realizarVenda, apagarVenda;
    private JTextField inputData, inputValor;
    private JScrollPane jSPane;
    private String clienteVenda, carroVenda;

    public VendasPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel
        // ---------------------*

        clientesComboBox = new JComboBox<>();
        carrosComboBox = new JComboBox<>();
        inputData = new JTextField(10);
        inputValor = new JTextField(10);
        realizarVenda = new JButton("Realizar venda");
        apagarVenda = new JButton("Cancela venda");
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
        buttonPanel.add(apagarVenda);
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
        // Tratamento de eventos:
        VendasControl control = new VendasControl(vendas, tableModel, table);
        realizarVenda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String carro = String.valueOf(carrosComboBox.getSelectedItem());
                String cliente = String.valueOf(clientesComboBox.getSelectedItem());
                if (!carro.equals("Selecione um carro") && !cliente.equals("Selecione um cliente")
                        && !inputData.getText().isEmpty() && !inputValor.getText().isEmpty()) {
                    if (control.validarData(inputData.getText()) && control.validarValor(inputValor.getText())) {
                        control.cadastrarVenda(cliente, carro, inputValor.getText(), inputData.getText());
                        inputData.setText("");
                        inputValor.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "ERRO!",
                                JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "ERRO!",
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

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    carroVenda = (String) table.getValueAt(linhaSelecionada, 2);
                    clienteVenda = (String) table.getValueAt(linhaSelecionada, 1);
                }

            }

        });

        apagarVenda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja cancelar esta venda?",
                        "Cancelar venda", JOptionPane.YES_NO_OPTION);

                if (res == JOptionPane.YES_OPTION) {
                    control.apagar(carroVenda);
                }
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
        carrosComboBox.addItem("Selecione um Carro");
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
