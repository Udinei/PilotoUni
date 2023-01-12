import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ManagedBean
@ViewScoped
public class PerfilUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Date dataNascimento;

	public void atualizar() {
		System.out.println("Data nascimento: " + this.dataNascimento);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Perfil atualizado!"));
	}

	public Date getDataHoje() {
		return new Date();
	}

}