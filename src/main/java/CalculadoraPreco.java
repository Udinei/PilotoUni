import jakarta.inject.Named;

@Named
public class CalculadoraPreco {
    public double calcularPreco(int quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }
}
