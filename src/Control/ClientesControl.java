package Control;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Clientes;

public class ClientesControl {
    // CRUD
    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public ClientesControl(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }
    // -------------------*
    // Métodos

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

    public void cadastrarCliente(String cpf, String nomeCompleto, String dataNascimento, String idade) {
        // Adiciona a tabela
        Clientes cliente = new Clientes(cpf.trim().toUpperCase(), nomeCompleto.trim().toUpperCase(),
                dataNascimento.trim(), idade.trim().toUpperCase());
        clientes.add(cliente);
        // -----------------------*
        // Adicionar ao banco de dados
        new ClientesDAO().cadastrar(cpf, nomeCompleto, dataNascimento, idade);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        // Atualiza o banco de dados
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        JOptionPane.showMessageDialog(table, "Cliente removido!!!");
    }

    public void atualizar(String cpf, String nomeCompleto, String dataNascimento, String idade) {
        new ClientesDAO().atualizar(cpf, nomeCompleto, dataNascimento, idade);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

}
