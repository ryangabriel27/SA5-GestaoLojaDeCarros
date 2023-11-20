package Model;

public class Vendas {
    // Atributos
    String cliente;
    String carro;
    String valor;
    String data;


    // Construtor
    public Vendas(String cliente, String carro, String valor, String data) {
        this.cliente = cliente;
        this.carro = carro;
        this.valor = valor;
        this.data = data;
    }

    // Getters and Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    

}
