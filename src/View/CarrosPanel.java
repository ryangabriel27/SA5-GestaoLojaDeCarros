package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import Control.CarrosControl;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import Model.Carros;

public class CarrosPanel extends JPanel {
    // Componentes
    private JPanel cards, buttonPanel, panelPrincipal, panelCadastro;
    private JButton cadastraCarro, editaCarro, apagaCarro, confirmaCadastro, voltarCadastro;
    private JTextField inputPlaca, inputMarca, inputModelo, inputAno, inputCor, inputValor;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Carros> carros = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane scrollPane;

    // Construtor
    public CarrosPanel() {
        super();

        // Entrada de dados
        // Botões de eventos (Adicionar, Modificar, Excluir, ...)
        // Tabela de carros
        // panel = new JPanel();
        // this.add(panel);
        CardLayout cl = new CardLayout();
        cards = new JPanel();
        this.add(cards);
        cards.setLayout(cl); // Definindo o layout do painel com cardLayout
        // panel.add(cards);

        panelPrincipal = new JPanel(new BorderLayout());
        cards.add(panelPrincipal, "Home"); // Adicionando a telaPrincipal ao painel de cards

        tableModel = new DefaultTableModel(); // Criando o modelo de tabela
        tableModel.addColumn("Placa"); // Adiciona uma coluna 'Placa'
        tableModel.addColumn("Marca"); // Adiciona uma coluna 'Marca'
        tableModel.addColumn("Modelo"); // Adiciona uma coluna 'Modelo'
        tableModel.addColumn("Ano"); // Adiciona uma coluna 'Ano'
        tableModel.addColumn("Cor"); // Adiciona uma coluna 'Cor'
        tableModel.addColumn("Valor");
        atualizarTabela();

        table = new JTable(tableModel);// Cria a tabela model da 'tableModel'
        scrollPane = new JScrollPane(table);// Adiciona a table a um JScrollPane
        panelPrincipal.add(scrollPane, BorderLayout.CENTER); // Adicionando a tabela a tela principal

        // Componentes
        cadastraCarro = new JButton("Cadastrar");
        apagaCarro = new JButton("Excluir");
        editaCarro = new JButton("Editar");
        inputPlaca = new JTextField(7);
        inputAno = new JTextField(4);
        inputCor = new JTextField(5);
        inputMarca = new JTextField(12);
        inputModelo = new JTextField(20);
        inputValor = new JTextField(12);
        voltarCadastro = new JButton("Retornar");
        confirmaCadastro = new JButton("Cadastrar");

        // Adicionar os componentes
        buttonPanel = new JPanel(new FlowLayout(1)); // Painel de botões
        buttonPanel.add(cadastraCarro);
        buttonPanel.add(editaCarro);
        buttonPanel.add(apagaCarro);
        panelPrincipal.add(buttonPanel, BorderLayout.NORTH);// Adicionando o painel De botões a Tela Principal

        panelCadastro = new JPanel(new FlowLayout(1));
        cards.add(panelCadastro, "Cadastro Carros");
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 2, 4));
        inputPanel.add(new JLabel("Digite a placa do carro:"));
        inputPanel.add(inputPlaca);
        inputPanel.add(new JLabel("Digite a marca do carro:"));
        inputPanel.add(inputMarca);
        inputPanel.add(new JLabel("Digite o modelo do carro:"));
        inputPanel.add(inputModelo);
        inputPanel.add(new JLabel("Digite o ano do carro:"));
        inputPanel.add(inputAno);
        inputPanel.add(new JLabel("Digite a cor do carro:"));
        inputPanel.add(inputCor);
        inputPanel.add(new JLabel("Digite o valor do carro:"));
        inputPanel.add(inputValor);
        inputPanel.add(voltarCadastro);
        inputPanel.add(confirmaCadastro);
        panelCadastro.add(inputPanel);

        // Tratamento de Eventos

        cadastraCarro.addActionListener(e -> {
            cl.show(cards, "Cadastro Carros");
        });

        voltarCadastro.addActionListener(e -> {
            cl.show(cards, "Home");
        });

        CarrosControl controllerCarros = new CarrosControl(carros, tableModel, table);
        confirmaCadastro.addActionListener(e -> {
            controllerCarros.cadastrarCarro(inputModelo.getText(), inputMarca.getText(), inputAno.getText(),
                    inputCor.getText(), inputPlaca.getText(), inputValor.getText());
            // Limpa os campos de entrada após a operação de cadastro
            inputMarca.setText("");
            inputAno.setText("");
            inputModelo.setText("");
            inputPlaca.setText("");
            inputValor.setText("");
            inputCor.setText("");
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputMarca.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputModelo.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputAno.setText((String) table.getValueAt(linhaSelecionada, 3));
                    inputPlaca.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputValor.setText((String) table.getValueAt(linhaSelecionada, 5));
                    inputCor.setText((String) table.getValueAt(linhaSelecionada, 4));
                }
            }
        });

        // Configura a ação do botão "editar" para atualizar um registro no banco de
        // dados
        editaCarro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método "atualizar" do objeto operacoes com os valores dos

                // campos de entrada

                controllerCarros.atualizar((String) table.getValueAt(linhaSelecionada, 0),
                        (String) table.getValueAt(linhaSelecionada, 1),
                        (String) table.getValueAt(linhaSelecionada, 2),
                        (String) table.getValueAt(linhaSelecionada, 3),
                        (String) table.getValueAt(linhaSelecionada, 4),
                        (String) table.getValueAt(linhaSelecionada, 5));
                // Limpa os campos de entrada após a operação de atualização
            }
        });

    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Carros carro : carros) {
            tableModel.addRow(new Object[] { carro.getPlaca(), carro.getMarca(), carro.getModelo(), carro.getAno(),
                    carro.getCor(), carro.getValor() });
        }
    }
}
