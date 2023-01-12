import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ManagedBean
@ViewScoped
public class PrecoProdutoBean {

	@Inject
	private CalculadoraPreco calculadora;
	
	public double getPreco() {

		return calculadora.calcularPreco(12, 44.55);
	}
	
}