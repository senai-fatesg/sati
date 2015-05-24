package br.com.ambientinformatica.sati.sati.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.sati.sati.entidade.Marca;
import br.com.ambientinformatica.sati.sati.persistencia.MarcaDao;

@Controller("MarcaControl")
@Scope("conversation")
public class MarcaControl {

	private Marca marca = new Marca();
	
	@Autowired
	private MarcaDao marcaDao;
	
	private List<Marca> marcas = new ArrayList<Marca>();
	

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			marcaDao.alterar(marca);
         listar(evt);
         marca = new Marca();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt){
		try {
			marcas = marcaDao.listar();
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
	
	public Marca getContato() {
		return marca;
	}

	public void setContato(Marca marca) {
		this.marca = marca;
	}
	
	public List<Marca> getMarcas() {
		return marcas;
	}

}
