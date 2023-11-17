package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import Control.CarrosControl;
import Control.CarrosDAO;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;

import Model.Carros;

public class CarrosPanel extends JPanel {
    // Componentes
    private JPanel buttonPanel;
    private JButton cadastraCarro, editaCarro, apagaCarro;
    private JTextField inputPlaca, inputMarca, inputModelo, inputAno, inputCor, inputValor;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Carros> carros = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;
    private boolean isAnoNumeric;
    private boolean isValorNumeric;

    // Construtor
    public CarrosPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel

        // --------------------------*
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

        // --------------------------*
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 2, 4)); // InputPanel
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
        add(inputPanel);

        // --------------------------*
        buttonPanel = new JPanel(); // Painel de botões
        buttonPanel.add(cadastraCarro);
        buttonPanel.add(editaCarro);
        buttonPanel.add(apagaCarro);
        add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        // --------------------------*
        jSPane = new JScrollPane(); // Criando um scrollPane
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Placa", "Marca", "Modelo", "Ano", "Cor", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        // Cria o banco de dados caso não tenha sido criado
        new CarrosDAO().criaTabela();
        // incluindo elementos do banco na criação do painel
        atualizarTabela();

        // --------------------------*
        // Tratamento de eventos
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputPlaca.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputMarca.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputModelo.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputAno.setText((String) table.getValueAt(linhaSelecionada, 3));
                    inputCor.setText((String) table.getValueAt(linhaSelecionada, 4));
                    inputValor.setText(String.valueOf(table.getValueAt(linhaSelecionada, 5)));
                }

            }

        });
        // --------------------------*
        CarrosControl controllerCarros = new CarrosControl(carros, tableModel, table); // Objeto da classe carrosControl

        // Cadastrar um carro:
        cadastraCarro.addActionListener(e -> {

            isAnoNumeric = inputAno.getText().chars().allMatch(Character::isDigit); // Verifica se o há somente números
                                                                                    // no inputAno
            isValorNumeric = inputValor.getText().chars().allMatch(Character::isDigit); // Verifica se o há somente
                                                                                        // números no inputValor
            if (!inputMarca.getText().isEmpty() && !inputModelo.getText().isEmpty() && !inputPlaca.getText().isEmpty()
                    && !inputAno.getText().isEmpty() && !inputCor.getText().isEmpty()
                    && !inputValor.getText().isEmpty()) {

                if (!isAnoNumeric) {
                    JOptionPane.showMessageDialog(null, "Digite o ano corretamente (Apenas números)", "Erro",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!isValorNumeric) {
                    JOptionPane.showMessageDialog(null, "Digite o valor corretamente (Apenas números)", "Erro",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if (controllerCarros.placaJaCadastrada(inputPlaca.getText())) {
                        JOptionPane.showMessageDialog(null,
                                "Placa já cadastrada. Por favor, insira uma placa diferente.", "Erro",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Se a placa não estiver cadastrada, realiza o cadastro
                        controllerCarros.cadastrarCarro(inputModelo.getText(), inputMarca.getText(), inputAno.getText(),
                                inputCor.getText(), inputPlaca.getText(), Double.parseDouble(inputValor.getText()));

                        // Limpa os campos de entrada após a operação de cadastro
                        inputMarca.setText("");
                        inputAno.setText("");
                        inputModelo.setText("");
                        inputPlaca.setText("");
                        inputValor.setText("");
                        inputCor.setText("");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(inputPanel, "Preencha os campos corretamente para cadastrar um carro!!");
            }

        });
        // --------------------------*

        // Editar um carro:
        editaCarro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isAnoNumeric = inputAno.getText().chars().allMatch(Character::isDigit); // Verifica se o há somente
                                                                                        // números no inputAno
                isValorNumeric = inputValor.getText().chars().allMatch(Character::isDigit); // Verifica se o há somente
                                                                                            // números no inputValor
                if (!isAnoNumeric) {
                    JOptionPane.showMessageDialog(null, "Digite o ano corretamente (Apenas números)", "Erro",
                            JOptionPane.WARNING_MESSAGE);
                } else if (!isValorNumeric) {
                    JOptionPane.showMessageDialog(null, "Digite o valor corretamente (Apenas números)", "Erro",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if (controllerCarros.placaJaCadastrada(inputPlaca.getText())) { // Verifica se a placa digitada já está cadastrada
                        JOptionPane.showMessageDialog(null,
                                "Placa já cadastrada. Por favor, insira uma placa diferente.", "Erro",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Chama o método "atualizar" do objeto operacoes com os valores dos campos de entrada
                        controllerCarros.atualizar(inputModelo.getText(), inputMarca.getText(), inputAno.getText(),
                                inputCor.getText(), inputPlaca.getText(), Double.parseDouble(inputValor.getText()));
                        // Limpa os campos de entrada após a operação de atualização
                    }
                }

            }
        });
        // --------------------------*

        // Apagar um carro:
        apagaCarro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                // "placa"
                if (linhaSelecionada != -1) {
                    controllerCarros.apagar(inputPlaca.getText());
                    // Limpa os campos de entrada após a operação de exclusão
                    inputMarca.setText("");
                    inputAno.setText("");
                    inputModelo.setText("");
                    inputPlaca.setText("");
                    inputValor.setText("");
                    inputCor.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um carro para ser excluído");
                }

            }
        });

    }

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        carros = new CarrosDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Carros carro : carros) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { carro.getPlaca(), carro.getMarca(),
                    carro.getModelo(), carro.getAno(), carro.getCor(), carro.getValor() });
        }

    }
}
