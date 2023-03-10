package teste.integra.micro.conta.jsf;

import teste.integra.micro.conta.to.ContaTO;
import teste.integra.micro.conta.to.NumeroTO;
import teste.integra.micro.conta.to.TransferenciaTO;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;

// Adaptador JSF
@Getter
@Setter
@Named
@ViewScoped
public class TransferenciaFrm implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer conta1;
    private String descricao1;
    private Integer conta2;
    private String descricao2;
    private BigDecimal valor;

    // Url do sistema rest
    private static final String URL = "http://localhost:8080/";
    // Consumidor rest com json
    private RestTemplate rest;

    public TransferenciaFrm() {
        // configuração do rest para processar json.
        rest = new RestTemplate();
        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    }

    // operações privadas de apoio

    private void limpar1() {
        conta1 = null;
        descricao1 = null;
    }

    private void limpar2() {
        conta2 = null;
        descricao2 = null;
    }

    private void limpar() {
        limpar1();
        limpar2();
    }

    private void erro(String mensagem) {
        var fc = FacesContext.getCurrentInstance();
        var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
        fc.addMessage(null, fm);
    }

    private void aviso(String mensagem) {
        var fc = FacesContext.getCurrentInstance();
        var fm = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
        fc.addMessage(null, fm);
    }

    // operações publicas eventos jsf

    public void pesquisaConta1() {
        try {
            // Novo get via rest ----------------
            var to = new NumeroTO(conta1);
            var resp = rest.postForEntity(URL + "/transferencia/getconta", to, ContaTO.class);
            if (resp.getStatusCode() == OK) {
                ContaTO conta = resp.getBody();
                if (conta.correntista == null) {
                    limpar1();
                } else {
                    descricao1 = toIso(conta.getCorrentista()) + " - Saldo R$ " + conta.getSaldo();
                }
            }
            // --------------------
        } catch (Exception e) {
            tratarErroRest(e.getMessage());
        }
    }

    public void pesquisaConta2() {
        try {
            // Novo get via rest ----------------
            var to = new NumeroTO(conta2);
            var resp = rest.postForEntity(URL + "/transferencia/getconta", to, ContaTO.class);
            if (resp.getStatusCode() == OK) {
                ContaTO conta = resp.getBody();
                if (conta.correntista == null) {
                    limpar2();
                } else {
                    descricao2 = toIso(conta.getCorrentista()) + " - Saldo R$ " + conta.getSaldo();
                }
            }
            // --------------------
        } catch (Exception e) {
            tratarErroRest(e.getMessage());
        }
    }

    public void transferir() {
        try {
            try {
                // Nova transfencia via rest ----------------
                var to = new TransferenciaTO(conta1, conta2, valor);
                var req = new HttpEntity<TransferenciaTO>(to, new HttpHeaders());
                var resp = rest.exchange(new URI(URL + "/transferencia/transferir"), PUT, req, String.class);
                if (resp.getStatusCode() == NO_CONTENT) {
                    limpar1();
                    limpar2();
                    valor = null;
                    aviso("Transferência feita com sucesso!");
                }
            } catch (HttpClientErrorException e) {
                var erro = new ResponseEntity<>(e.getResponseBodyAsString(), BAD_REQUEST);
                if (e.getStatusCode().value() == 400) {
                    erro(toIso(erro.getBody()));
                } else {
                    tratarErroRest(toIso(erro.getBody()));
                }
            } catch (Exception e) {
                tratarErroRest(e.getMessage());
            }
            //-----------------------------------------------
        } catch (Exception e) {
            erro(e.getMessage());
        }
    }

    // Novo trata mensgem da comunicação http ----------------
    public void tratarErroRest(String erro) {
        if (erro.contains("Connection refused") || erro.contains("404") || erro.contains("405")) {
            erro("Sistema fora de ar, tenta mais tarde.");
        } else {
            erro("Erro não tratado:" + erro);
        }
    }
    // ---------------------------------------

    // Novo trata string utf8 ----------------
    public static String toIso(String str) {
        var utf8 = Charset.forName("UTF-8");
        var iso88591 = Charset.forName("ISO-8859-1");
        var ib = ByteBuffer.wrap(str.getBytes());
        var data = utf8.decode(ib);
        var outputBuffer = iso88591.encode(data);
        var outputData = outputBuffer.array();
        return new String(outputData);
    }
    // ---------------------------------------


    public Integer getConta1() {

        System.out.println("context root....." + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        return conta1;
    }

}
