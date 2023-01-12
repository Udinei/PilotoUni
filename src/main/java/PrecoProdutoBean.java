import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class PrecoProdutoBean {


	@Inject 
	private CalculadoraPreco calculadora;
	
	public double getPreco() {

		if(calculadora != null) {
			return calculadora.calcularPreco(12, 44.55);
		}else{
			System.out.println("Não injetou bean calculadora preco....Erro calculadora = null.111");
			return 0.0;
		}
	}
	
}