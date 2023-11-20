package Model;

public class Vendas {
    // Atributos
    String cliente;
    String carro;

    // Construtor
    public Vendas(String cliente, String carro) {
        this.cliente = cliente;
        this.carro = carro;
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

}
