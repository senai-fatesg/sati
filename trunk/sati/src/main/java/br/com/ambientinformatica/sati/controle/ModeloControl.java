package br.com.ambientinformatica.sati.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.persistencia.ModeloDao;

@Controller("ModeloControControl")
@Scope("conversation")
public class ModeloControl {
	
	private List<Modelo> modeloList = new ArrayList<Modelo>();
	
	private Modelo modelo = new Modelo();
	
	@Autowired
	private ModeloDao modeloDao;
	
	@PostConstruct
	public void init() {
		listar(null);
	}
	
	public void confirmar(ActionEvent evt) {
		try {
			if (evt == null) {
				FacesContext.getCurrentInstance().addMessage(
						"Ambient Informatica",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"É preciso digitar o nome!", null));
			} else {
				modeloDao.alterar(modelo);
				listar(evt);
				inicialize(null);
				FacesContext.getCurrentInstance().getExternalContext().redirect("marca.jsf");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void listar(ActionEvent evt) {
		try {
			modeloList = modeloDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void preparaAlterar(ActionEvent evt) {
		try {
			modelo = (Modelo) evt.getComponent().getAttributes().get("modelo");
			modelo = modeloDao.consultar(modelo.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("marcaupdate.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			modelo = (Modelo) evt.getComponent().getAttributes().get("marca");
			modelo = modeloDao.consultar(modelo.getId());
			modeloDao.excluirPorId(modelo.getId());
			listar(evt);
			FacesContext.getCurrentInstance().addMessage(
					"Sati System",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Operação realizada com sucesso", null));
			inicialize(null);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			inicialize(null);
		}
	}
	
	public void inicialize(ActionEvent actionEvent) {
		modelo = new Modelo();
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
}
//by Thayro Rodrigues.
