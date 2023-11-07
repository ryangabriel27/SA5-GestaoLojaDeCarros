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
        // new
        // CarrosDAO(ConnectionFactory.getConnection()).adicionarCarro(modelo.trim(),
        // marca.trim(), ano, cor.trim(), placa.trim(), valor);
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

    public void atualizaCarro(int linhaSelecionada, String modelo, String marca, String ano, String cor, String placa,
            String valor) {
        if (linhaSelecionada != -1) {
            Carros carro = new Carros(modelo.trim().toUpperCase(), marca.trim().toUpperCase(), ano.trim(),
                    cor.trim().toUpperCase(), placa.trim().toUpperCase(), "R$ " + valor.trim());
            carros.add(carro);
            atualizarTabela();
        }
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        // carros = new CarrosDAO().read();
        Object linha[] = new Object[6];
        for (int i = 0; i < carros.size(); i++) {
            linha[0] = carros.get(i).placa;
            linha[1] = carros.get(i).marca;
            linha[2] = carros.get(i).modelo;
            linha[3] = carros.get(i).ano;
            linha[4] = carros.get(i).cor;
            linha[5] = carros.get(i).valor;
            tableModel.addRow(linha);
        }

    }
}
