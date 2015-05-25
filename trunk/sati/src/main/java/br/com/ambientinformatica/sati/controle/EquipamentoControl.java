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
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;
import br.com.ambientinformatica.sati.persistencia.MarcaDao;

@Controller("EquipamentoControl")
@Scope("conversation")
public class EquipamentoControl {

	private Equipamento equipamento = new Equipamento();
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();
	private List<Marca> marcas = new ArrayList<Marca>();

	@Autowired
	private EquipamentoDao equipamentoDao;
	@Autowired
	private MarcaDao marcaDao;

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			if (equipamento.getId() == null) {
				equipamento.setMarca(equipamento.getMarca());
				equipamentoDao.incluir(equipamento);
			} else {
				equipamento.setMarca(equipamento.getMarca());
				equipamentoDao.alterar(equipamento);
			}
			listar(evt);
			inicialize(null);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("equipamento.jsf");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			equipamentos = equipamentoDao.listarEquipamento();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		try {
			equipamento = (Equipamento) evt.getComponent().getAttributes()
					.get("equipamento");
			equipamento = equipamentoDao.consultar(equipamento.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("equipamentoupdate.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			equipamento = (Equipamento) evt.getComponent().getAttributes()
					.get("equipamento");
			equipamento = equipamentoDao.consultar(equipamento.getId());
			equipamentoDao.excluirPorId(equipamento.getId());
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

	// BUSCA MARCA PELO NOME - AUTOCOMPLETE
	public List<Marca> buscaMarcaporNome(String nome)
			throws PersistenciaException {

		List<Marca> allmarcas = marcaDao.listar();
		List<Marca> filteredMarca = new ArrayList<Marca>();

		for (int i = 0; i < allmarcas.size(); i++) {
			Marca marc = allmarcas.get(i);
			if (marc.getNome().toLowerCase().startsWith(nome)) {
				filteredMarca.add(marc);
			}
		}
		return filteredMarca;
	}

	public void inicialize(ActionEvent actionEvent) {
		equipamento = new Equipamento();
		marcas = null;
	}

	// GTT E STT
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;

	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

}
//by Silas A.