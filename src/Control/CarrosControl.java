package Control;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.ConnectionFactory;
import Model.Carros;

public class CarrosControl {
    // CRUD
    private List<Carros> carros;
    private DefaultTableModel tableModel;
    private JTable table;
    CarrosDAO carrosDAO;

    // Construtor
    public CarrosControl(List<Carros> carros, DefaultTableModel tableModel, JTable table) {
        this.carros = carros;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Métodos
    public void cadastrarCarro(String modelo, String marca, int ano, String cor, String placa, double valor) {
        // Adiciona a tabela
        Carros carro = new Carros(modelo.trim(), marca.trim(), ano, cor.trim(), placa.trim(), valor);
        carros.add(carro);
        // -----------------------*
        // Adicionar ao banco de dados
        carrosDAO.adicionarCarro(modelo.trim(), marca.trim(), ano, cor.trim(), placa.trim(), valor);
        // -----------------------*
        atualizarTabela();// Atualiza a tabela
        // Atualiza o banco de dados
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Carros carro : carros) {
            tableModel.addRow(new Object[] { carro.getPlaca(), carro.getMarca(), carro.getModelo(), carro.getAno(),
                    carro.getCor(), carro.getValor() });
        }
    }

}
