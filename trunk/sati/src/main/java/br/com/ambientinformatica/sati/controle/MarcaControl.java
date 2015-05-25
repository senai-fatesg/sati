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
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.persistencia.MarcaDao;

@Controller("MarcaControl")
@Scope("conversation")
public class MarcaControl {

	private List<Marca> marcas = new ArrayList<Marca>();
	private Marca marca = new Marca();

	@Autowired
	private MarcaDao marcaDao;

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			if (evt == null) {
				FacesContext.getCurrentInstance().addMessage(
						"Sati Tecnologia em Informática",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"É preciso digitar o nome!", null));
			} else {
				marcaDao.alterar(marca);
				listar(evt);
				inicialize(null);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("marca.jsf");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			marcas = marcaDao.listarMarca();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			marca = (Marca) evt.getComponent().getAttributes().get("marca");
			marca = marcaDao.consultar(marca.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("marcaupdate.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			marca = (Marca) evt.getComponent().getAttributes().get("marca");
			marca = marcaDao.consultar(marca.getId());
			marcaDao.excluirPorId(marca.getId());
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
		marca = new Marca();
	}

	// GTT E STTS
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Marca> getMarcas() {
		return marcas;

	}

}
//by Silas A.
