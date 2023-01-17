package teste.integra.jsf.cdi;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Named
public class PrecoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

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