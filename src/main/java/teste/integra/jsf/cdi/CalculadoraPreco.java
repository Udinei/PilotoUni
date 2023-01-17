package teste.integra.jsf.cdi;

public class CalculadoraPreco {
    public double calcularPreco(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }
}
