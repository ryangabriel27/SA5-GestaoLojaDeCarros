package View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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

import Control.CarrosControl;
import Control.CarrosDAO;
import Control.ClientesControl;
import Control.ClientesDAO;
import Model.Carros;
import Model.Clientes;

public class ClientesPanel extends JPanel {

    // Componentes
    private JPanel buttonPanel;
    private JButton cadastraCliente, editaCliente, apagaCliente;
    private JTextField inputCpf, inputNome, inputDataNascimento, inputIdade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Clientes> clientes = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    // Construtor
    public ClientesPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel

        // --------------------------*
        // Componentes
        cadastraCliente = new JButton("Cadastrar");
        apagaCliente = new JButton("Excluir");
        editaCliente = new JButton("Editar");
        inputCpf = new JTextField(7);
        inputNome = new JTextField(4);
        inputDataNascimento = new JTextField(5);
        inputIdade = new JTextField(12);

        // --------------------------*
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 2, 4)); // InputPanel
        inputPanel.add(new JLabel("Digite o cpf do cliente:"));
        inputPanel.add(inputCpf);
        inputPanel.add(new JLabel("Digite o nome completo do cliente:"));
        inputPanel.add(inputNome);
        inputPanel.add(new JLabel("Digite a data de nascimento do cliente:"));
        inputPanel.add(inputDataNascimento);
        inputPanel.add(new JLabel("Digite a idade do cliente:"));
        inputPanel.add(inputIdade);
        add(inputPanel);

        // --------------------------*
        buttonPanel = new JPanel(); // Painel de botões
        buttonPanel.add(cadastraCliente);
        buttonPanel.add(editaCliente);
        buttonPanel.add(apagaCliente);
        add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        // --------------------------*
        jSPane = new JScrollPane(); // Criando um scrollPane
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "CPF", "Nome Completo", "Data de Nascimento", "Idade" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        // Cria o banco de dados caso não tenha sido criado
        new ClientesDAO().criaTabela();
        // incluindo elementos do banco na criação do painel
        atualizarTabela();

        // --------------------------*
        // Tratamento de eventos
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputCpf.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputDataNascimento.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputIdade.setText((String) table.getValueAt(linhaSelecionada, 3));
                }

            }

        });

        // --------------------------*
        ClientesControl control = new ClientesControl(clientes, tableModel, table); // Objeto da classe carrosControl

        // Cadastrar um cliente:
        cadastraCliente.addActionListener(e -> {

            if (!inputCpf.getText().isEmpty() && !inputNome.getText().isEmpty()
                    && !inputDataNascimento.getText().isEmpty()
                    && !inputIdade.getText().isEmpty()) {

                control.cadastrarCliente(inputCpf.getText(), inputNome.getText(), inputDataNascimento.getText(),
                        inputIdade.getText());

                // Limpa os campos de entrada após a operação de cadastro
                inputCpf.setText("");
                inputNome.setText("");
                inputDataNascimento.setText("");
                inputIdade.setText("");

            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um cliente!!");
            }

        });
        // --------------------------*

        // Editar um cliente:
        editaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método "atualizar" do objeto operacoes com os valores dos

                // campos de entrada

                control.atualizar(inputCpf.getText(), inputNome.getText(), inputDataNascimento.getText(),
                        inputIdade.getText());
                // Limpa os campos de entrada após a operação de atualização
            }
        });
        // --------------------------*

        // Apagar um cliente:
        apagaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                // "placa"
                control.apagar(inputCpf.getText());
                // Limpa os campos de entrada após a operação de exclusão
                inputCpf.setText("");
                inputNome.setText("");
                inputDataNascimento.setText("");
                inputIdade.setText("");
            }
        });
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNomeCompleto(), cliente.getDataNascimento(),
                    cliente.getIdade() });
        }

    }
}
