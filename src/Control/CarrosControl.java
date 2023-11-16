package Control;

import java.awt.JobAttributes;
import java.sql.Connection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.ConnectionFactory;
import Model.Carros;
import Control.CarrosDAO;

public class CarrosControl {
    // CRUD
    private List<Carros> carros;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public CarrosControl(List<Carros> carros, DefaultTableModel tableModel, JTable table) {
        this.carros = carros;
        this.tableModel = tableModel;
        this.table = table;
    }
    // -------------------*
    // Métodos

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

    public void cadastrarCarro(String modelo, String marca, String ano, String cor, String placa, double valor) {
        // Adiciona a tabela
        Carros carro = new Carros(modelo.trim().toUpperCase(), marca.trim().toUpperCase(), ano.trim(),
                cor.trim().toUpperCase(), placa.trim().toUpperCase(), valor);
        carros.add(carro);
        // -----------------------*
        // Adicionar ao banco de dados
        new CarrosDAO().cadastrar(marca, modelo, ano, cor, placa, valor);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        JOptionPane.showMessageDialog(null, "Carro cadastrado com sucesso!");
        // Atualiza o banco de dados
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String placa) {
        new CarrosDAO().apagar(placa);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        JOptionPane.showMessageDialog(table, "Carro removido!!!");
    }

    public void atualizar(String modelo, String marca, String ano, String cor, String placa, double valor) {
        new CarrosDAO().atualizar(marca, modelo, ano, cor, placa, valor);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }
}
