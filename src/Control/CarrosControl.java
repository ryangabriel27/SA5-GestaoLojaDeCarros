package Control;

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

    // Métodos
    public void cadastrarCarro(String modelo, String marca, String ano, String cor, String placa, String valor) {
        // Adiciona a tabela
        Carros carro = new Carros(modelo.trim().toUpperCase(), marca.trim().toUpperCase(), ano.trim(),
                cor.trim().toUpperCase(), placa.trim().toUpperCase(), "R$ " + valor.trim());
        carros.add(carro);
        // -----------------------*
        // Adicionar ao banco de dados

        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        // Atualiza o banco de dados
    }

    public void apagarCarro(int linhaSelecionada) {
        if (linhaSelecionada != -1) {
            carros.remove(linhaSelecionada);
            atualizarTabela();
        }
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        // carros = new CarrosDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Carros carro : carros) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { carro.getPlaca(), carro.getMarca(),
                    carro.getModelo(), carro.getAno(), carro.getCor(), carro.getValor() });
        }

    }

    public void atualizar(String modelo, String marca, String ano, String cor, String placa, String valor) {
        // new CarrosDAO().atualizar(marca, modelo, ano, placa, valor);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

}
