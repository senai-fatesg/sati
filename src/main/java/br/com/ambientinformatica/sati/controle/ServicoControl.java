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
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.persistencia.ServicoDao;

@Controller("ServicoControl")
@Scope("conversation")
public class ServicoControl {

	private Servico servico = new Servico();
	private List<Servico> servicos = new ArrayList<Servico>();

	@Autowired
	private ServicoDao servicoDao;

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			servicoDao.alterar(servico);
			listar(evt);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("servico.jsf");
			inicialize(null);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			servicos = servicoDao.listarServico();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			servico = (Servico) evt.getComponent().getAttributes()
					.get("servico");
			servico = servicoDao.consultar(servico.getId());
			servicoDao.excluirPorId(servico.getId());
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
		servico = new Servico();
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			servico = (Servico) evt.getComponent().getAttributes()
					.get("servico");
			servico = servicoDao.consultar(servico.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("servicoupdate.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// GTT E STT
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

}
//by Silas A.
