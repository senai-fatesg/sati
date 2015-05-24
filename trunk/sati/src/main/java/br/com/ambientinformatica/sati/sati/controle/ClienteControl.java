package br.com.ambientinformatica.sati.sati.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;

import br.com.ambientinformatica.sati.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.sati.persistencia.ClienteDao;

@Controller("ContatoControl")
@Scope("conversation")
public class ClienteControl {

	private Cliente contato = new Cliente();
	
	@Autowired
	private ClienteDao contatoDao;
	
	private List<Cliente> contatos = new ArrayList<Cliente>();
	

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			contatoDao.alterar(contato);
         listar(evt);
         contato = new Cliente();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt){
		try {
			contatos = contatoDao.listar();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
	
	public Cliente getContato() {
		return contato;
	}

	public void setContato(Cliente contato) {
		this.contato = contato;
	}
	
	public List<Cliente> getContatos() {
		return contatos;
	}

}
