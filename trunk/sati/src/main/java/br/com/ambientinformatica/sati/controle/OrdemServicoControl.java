package br.com.ambientinformatica.sati.controle;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.entidade.ItemEquipamento;
import br.com.ambientinformatica.sati.entidade.ItemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;
import br.com.ambientinformatica.sati.persistencia.ServicoDao;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilData;

@Controller("OrdemServicoControl")
@Scope("conversation")
public class OrdemServicoControl {

	@Autowired
	private ClienteDao clienteDao;
	@Autowired
	private OrdemServicoDao ordemServicoDao;
	@Autowired
	private EquipamentoDao equipamentoDao;
	@Autowired
	private ServicoDao servicoDao;
	@Autowired
	private TecnicoDao tecnicoDao;

	private OrdemServico ordemServico = new OrdemServico();
	private Servico servico = new Servico();
	private Tecnico tecnico = new Tecnico();
	private Cliente cliente = new Cliente();
	private Equipamento equipamento = new Equipamento();
	private ItemServico itemServico = new ItemServico();
	private ItemEquipamento itemEquipamento = new ItemEquipamento();
	private OrdemServico osCancelar = new OrdemServico();
	private OrdemServico osFechar = new OrdemServico();
	private BigDecimal totalEquipamentos;
	private BigDecimal totalServicos;
	private BigDecimal totalOrdemServico;

	// LISTAS
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private List<OrdemServico> ordensServico = new ArrayList<OrdemServico>();
	// ENUM ESTADO PRA BUSCA DA OS
	private EnumEstadoOrdemServico estadoOsSelecionado;
	// DATA DA CONSULTA DA OS
	private Date dataHoraInicio = UtilData.getDataInicioMes(new Date());
	private Date dataHoraFim = UtilData.getDataFimMes(new Date());
	// FILTRO DE NOMES
	private String nomedoCliente;
	// ATIVA O CAMPO APOS SELECIONAR CLIENTE
	private Boolean ativabotao = false;
	// Ativa Campos TiPO OS
	private Boolean ativacbTipoos = false;
	private Boolean ativaEquipamentoOs;
	private Boolean ativaServicoOs;
	private String tipoOs;

	@PostConstruct
	public void init() {
		buscarTecnicobyUsuario(null);
		setAtivaEquipamentoOs(true);
	}

	// BUSCA SERVIÇO PELO NOME - AUTOCOMPLETE
	public List<Servico> buscaServicoporNome(String nome)
			throws PersistenciaException {

		List<Servico> allservicos = servicoDao.listar();
		List<Servico> filteredServ = new ArrayList<Servico>();

		for (int i = 0; i < allservicos.size(); i++) {
			Servico serv = allservicos.get(i);
			if (serv.getDescricao().toLowerCase().startsWith(nome)) {
				filteredServ.add(serv);
			}
		}
		return filteredServ;
	}

	// BUSCA EQUIPAMENTO PELO NOME - AUTOCOMPLETE
	public List<Equipamento> buscaEquipamentoporNome(String nome)
			throws PersistenciaException {

		List<Equipamento> allequip = equipamentoDao.listar();
		List<Equipamento> filteredEquip = new ArrayList<Equipamento>();

		for (int i = 0; i < allequip.size(); i++) {
			Equipamento equip = allequip.get(i);
			if (equip.getNome().toLowerCase().startsWith(nome)) {
				filteredEquip.add(equip);
			}
		}
		return filteredEquip;
	}

	// BUSCA CLIENTE PELO NOME - AUTOCOMPLETE
	public List<Cliente> buscaClienteporNome(String nome)
			throws PersistenciaException {

		List<Cliente> allclientes = clienteDao.listar();
		List<Cliente> filteredcli = new ArrayList<Cliente>();
		for (int i = 0; i < allclientes.size(); i++) {
			Cliente cli = allclientes.get(i);
			if (cli.getNomecliente().toLowerCase().startsWith(nome)) {
				filteredcli.add(cli);
			}
		}
		return filteredcli;
	}

	// ADICIONA UM ITEM A UMA LISTA DE SERVIÇOS DA O.S.
	public void addServico(ActionEvent evt) {
		if (!verificaItemServico()) {
			itemServico.setServico(servico);
			ordemServico.addItemServico(itemServico);
			totalServicos = getValorTotalServicos();
			totalOrdemServico = getValorTatalOrdemServico();
			servico = new Servico();
			itemServico = new ItemServico();
		}
	}

	// ADICIONA UM ITEM A UMA LISTA DE EQUIPAMENTOS DA O.S.
	public void adicionarItem(ActionEvent evt) {
		if (!verificaItemEquipamento()) {
			itemEquipamento.setEquipamento(equipamento);
			ordemServico.addItemEquipamento(itemEquipamento);
			totalEquipamentos = getValorTotalEquipamentos();
			totalOrdemServico = getValorTatalOrdemServico();
			equipamento = new Equipamento();
			itemEquipamento = new ItemEquipamento();
		}
	}

	// VERIFICA OS CAMPOS DOS ITEM EQUIPAMENTO
	public boolean verificaItemEquipamento() {
		boolean exist = false;
		if (equipamento == null) {
			UtilFaces.addMensagemFaces("Selecione um equipamento!");
			exist = true;
		}
		if (itemEquipamento.getQuantidade() < 1) {
			UtilFaces
					.addMensagemFaces("Quantidade inválida! valor é inferior a 1");
			exist = true;
		}
		return exist;
	}

	// VERIFICA OS CAMPOS DOS ITEM SERVIÇO
	public boolean verificaItemServico() {
		boolean exist = false;
		if (servico == null) {
			UtilFaces.addMensagemFaces("Selecione um serviço");
			exist = true;
		}
		if (itemServico.getQuantidade() < 1) {
			UtilFaces
					.addMensagemFaces("Quantidade inválida! valor é inferior a 1");
			exist = true;
		}
		return exist;
	}

	// EXCLUI UM ITEM SELECIONADO DA LISTA DE EQUIPAMENTOS
	public void excluirItemEquipamento(ActionEvent evt) {
		itemEquipamento = (ItemEquipamento) evt.getComponent().getAttributes()
				.get("itemEquipamentoExcluir");
		ordemServico.removeItemEquipamento(itemEquipamento);
		totalEquipamentos = getValorTotalEquipamentos();
		totalOrdemServico = getValorTatalOrdemServico();
	}

	// EXCLUI UM ITEM SELECIONADO DA LISTA DE SERVICOS
	public void excluirItemServico(ActionEvent evt) {
		itemServico = (ItemServico) evt.getComponent().getAttributes()
				.get("itemServicoExcluir");
		ordemServico.removeItemServico(itemServico);
		totalServicos = getValorTotalServicos();
		totalOrdemServico = getValorTatalOrdemServico();
	}

	// PEGA O TOTAL DOS ITENS DE EQUIPAMENTO
	public BigDecimal getValorTotalEquipamentos() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		if (ordemServico.getItensEquipamentos() != null) {
			for (ItemEquipamento ie : ordemServico.getItensEquipamentos()) {
				valorTotal = valorTotal.add(ie.getValorTotal() != null ? ie
						.getValorTotal() : BigDecimal.ZERO);
			}
		}
		return valorTotal;
	}

	// PEGA O TOTAL DOS ITENS DE SERVICO
	public BigDecimal getValorTotalServicos() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		if (ordemServico.getItensServicos() != null) {
			for (ItemServico is : ordemServico.getItensServicos()) {
				valorTotal = valorTotal.add(is.getValorTotal() != null ? is
						.getValorTotal() : BigDecimal.ZERO);
			}
		}
		return valorTotal;
	}

	// SOMA O TOTAL DE EQUIPAMENTOS COM SERVIÇOS
	public BigDecimal getValorTatalOrdemServico() {
		BigDecimal valorTotal = BigDecimal.ZERO;

		if (totalEquipamentos == null && totalServicos != null) {
			valorTotal = totalServicos;
		}
		if (totalEquipamentos != null && totalServicos == null) {
			valorTotal = totalEquipamentos;
		}
		if (totalEquipamentos != null && totalServicos != null) {
			valorTotal = totalEquipamentos.add(totalServicos);
		}
		return valorTotal;
	}

	// ENUM DA LISTAGEM DE OS
	public List<SelectItem> getStatusOrdemServicos() {
		return UtilFaces.getListEnum(EnumEstadoOrdemServico.values());
	}

	// ENUM DE TIPOS DE ESTADO DE ORDEM DE SERVIÇO
	public List<SelectItem> getTiposOs() {
		return new ArrayList<SelectItem>(
				UtilFaces.getListEnum(EnumEstadoOrdemServico.values()));
	}

	// INCLUI UMA O.S
	public void confirmar() {

		try {
			if (!verificaCamposOs()) {
				ordemServico.emitirOs();
				ordemServico.setTecnico(tecnico);
				ordemServico.setCliente(ordemServico.getCliente());
				ordemServicoDao.incluir(ordemServico);
				// verifica se tem tecnico
				if (tecnico != null) {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemdeservicomobilelista.jsf");
				} else {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemServicolista.jsf");
				}
				// limpar objeto
				inicialize(null);
				listaremAtendimento(null);

				FacesContext.getCurrentInstance().addMessage(
						"Sati System",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Ordem de Serviço incluída com sucesso", null));

			}
		} catch (PersistenciaException e) {
			UtilFaces
					.addMensagemFaces("Houve um erro ao Emitir Ordem de Serviço");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// / ALTERAR DADOS OS
	public void alterar() {
		try {
			if (!verificaCamposOs()) {
				ordemServico.setCliente(ordemServico.getCliente());
				ordemServicoDao.alterar(ordemServico);
				// verifica se tem tecnico
				if (tecnico != null) {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemdeservicomobilelista.jsf");
				} else {
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemServicolista.jsf");
				}
				// limpar objeto
				inicialize(null);
				listaremAtendimento(null);
				FacesContext.getCurrentInstance().addMessage(
						"Sati System",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Ordem de Serviço alterada com sucesso", null));

			}

		} catch (PersistenciaException e) {
			UtilFaces
					.addMensagemFaces("Houve um erro ao Salvar a Ordem de Serviço");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// /VERIFICA SE HA CAMPOS VAZIOS
	public boolean verificaCamposOs() {
		boolean exist = false;
		if (ordemServico.getItensEquipamentoList().size() == 0
				&& ativaEquipamentoOs == true) {
			UtilFaces.addMensagemFaces("É necessário incluir um Equipamento!");
			exist = true;
		}

		if (ordemServico.getItensServicoList().size() == 0
				&& ativaServicoOs == true) {
			UtilFaces.addMensagemFaces("É necessário incluir um Serviço!");
			exist = true;
		}

		if (ordemServico.getCliente().getId() == 0) {
			UtilFaces
					.addMensagemFaces("É necessário adicionar um Cliente na O.S!");
			exist = true;
		}

		if (ordemServico.getDescricaoProblema().length() > 255) {
			UtilFaces
					.addMensagemFaces("Campo Observação suporta Apenas 255 caracteres!");
			exist = true;
		}
		return exist;
	}

	// Prepara a ordem de Serviço pra alteraçao
	public void preparaAlterar(ActionEvent evt) {
		try {
			ordemServico = (OrdemServico) evt.getComponent().getAttributes()
					.get("ordemservico");
			ordemServico = ordemServicoDao.consultarPorId(ordemServico.getId());
			totalEquipamentos = getValorTotalEquipamentos();
			totalServicos = getValorTotalServicos();
			totalOrdemServico = getValorTatalOrdemServico();
			// /VERIFICAÇAO DE CONTEUDO DA O.S
			if (ordemServico.getItensEquipamentoList().size() > 0) {
				setAtivaEquipamentoOs(true);
			}
			if (ordemServico.getItensServicoList().size() > 0) {
				setAtivaServicoOs(true);
				setAtivaEquipamentoOs(false);
			}
			if (ordemServico.getItensEquipamentoList().size() > 0
					&& ordemServico.getItensServicoList().size() > 0) {
				setAtivaServicoOs(true);
				setAtivaEquipamentoOs(true);
			}
			if (tecnico == null) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("ordemServicoupdate.jsf");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("ordemdeservicomobileupdate.jsf");
			}

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// / LIMPAR CAMPOS
	public void inicialize(ActionEvent actionEvent) {
		ordemServico = new OrdemServico();
		itemServico = new ItemServico();
		itemEquipamento = new ItemEquipamento();
		equipamento = new Equipamento();
		servico = new Servico();
		totalEquipamentos = null;
		totalOrdemServico = null;
		totalServicos = null;
		nomedoCliente = null;
		setAtivabotao(false);
		setAtivaEquipamentoOs(true);
		setAtivaServicoOs(false);
		setAtivacbTipoos(false);
		tipoOs = null;
		dataHoraInicio = UtilData.getDataInicioMes(new Date());
		dataHoraFim = UtilData.getDataFimMes(new Date());
	}

	// / LISTA A O.S DE ACORDO COM STATUS
	public void listar(ActionEvent evt) {
		ordensServico.clear();
		try {
			if (!verificaListagem()) {
				if (tecnico == null) {
					ordensServico = ordemServicoDao
							.listarPorDataEmissaoSemTecnico(0L, dataHoraInicio,
									dataHoraFim, estadoOsSelecionado);
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemServicolista.jsf");
				} else {
					ordensServico = ordemServicoDao.listarPorDataEmissao(0L,
							dataHoraInicio, dataHoraFim, estadoOsSelecionado,
							tecnico.getId());
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("ordemdeservicomobilelista.jsf");
				}
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// VERIFICA CAMPOS PARA LISTAGEM DE O.S
	public boolean verificaListagem() {
		boolean exist = false;
		if (dataHoraInicio == null) {
			UtilFaces.addMensagemFaces("Data de Inicio Inválida!");
			exist = true;
		}
		if (dataHoraFim == null) {
			UtilFaces.addMensagemFaces("Data Fim Inválida!");
			exist = true;
		}

		if (dataHoraInicio != null && dataHoraInicio != null) {
			if (dataHoraFim.getTime() - dataHoraInicio.getTime() > 2678400000L) {
				UtilFaces
						.addMensagemFaces("O intervalo entre a data final e a data inicial deve ser de no máximo 31 dias");
				exist = true;
			}
		}

		return exist;
	}

	// / LISTA SOMENTE AS O.S EM ATENDIMENTO
	public void listaremAtendimento(ActionEvent evt) {
		try {
			if (tecnico == null) {
				ordensServico = ordemServicoDao
						.listarPorOsAtendimentoAdmin(EnumEstadoOrdemServico.ATENDENDO);
			} else {
				ordensServico = ordemServicoDao.listarPorOsAtendimento(
						EnumEstadoOrdemServico.ATENDENDO, tecnico.getId());
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);

		}
	}

	// / BUSCA O TECNICO LOGADO NO SISTEMA APARTIR DO SEU USUARIO
	public void buscarTecnicobyUsuario(ActionEvent evt) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			String login = external.getRemoteUser();

			tecnico = tecnicoDao.buscarTecnicoByUsuario(login);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}

	}

	// /IMPRIMI O.S
	public void imprimir(ActionEvent evt) {
		try {
			// OrdemServico osimprimir = new OrdemServico();
			ordemServico = (OrdemServico) evt.getComponent().getAttributes()
					.get("osimprimir");
			ordemServico = ordemServicoDao.consultarPorId(ordemServico.getId());
			totalEquipamentos = getValorTotalEquipamentos();
			totalServicos = getValorTotalServicos();
			totalOrdemServico = getValorTatalOrdemServico();
			// /VERIFICAÇAO DE CONTEUDO DA O.S
			setAtivaEquipamentoOs(false);
			setAtivaServicoOs(false);
			if (ordemServico.getItensEquipamentoList().size() > 0) {
				setAtivaEquipamentoOs(true);
			}
			if (ordemServico.getItensServicoList().size() > 0) {
				setAtivaServicoOs(true);
				setAtivaEquipamentoOs(false);
			}
			if (ordemServico.getItensEquipamentoList().size() > 0
					&& ordemServico.getItensServicoList().size() > 0) {
				setAtivaServicoOs(true);
				setAtivaEquipamentoOs(true);
			}
			// if (osimprimir != null) {
			// Map<String, Object> parametros = new HashMap<String, Object>();
			// osimprimir.setItensEquipamentos(null);
			// parametros.put("os", osimprimir);
			// UtilFacesRelatorio.gerarRelatorioFaces(
			// "jasper/ordemServico.jasper", osimprimir.getItensEquipamentos(),
			// parametros);

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// / CANCELAR ORDEM DE SERVIÇO
	public void cancelar(ActionEvent evt) {
		try {
			osCancelar = ordemServicoDao.consultar(osCancelar.getId());
			osCancelar.cancelarOs();
			ordemServicoDao.alterar(osCancelar);
			listar(null);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// / FECHAR ORDEM DE SERVICO
	public void fechar(ActionEvent evt) {
		try {
			osFechar = ordemServicoDao.consultar(osFechar.getId());
			osFechar.fecharOrdemServico();
			ordemServicoDao.alterar(osFechar);
			listar(null);

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// LISTA TODOS OS CLIENTES PARA TABLE DE CONSULTA
	public void listarCliente() {
		try {
			clientes = clienteDao.listar();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// FILTRA OS CLIENTES E ATUALIZA A TABELA
	public void filtrarCliente(AjaxBehaviorEvent event) {
		try {
			if (nomedoCliente != null && !nomedoCliente.isEmpty()) {
				clientes = clienteDao.listarPorNome(nomedoCliente);
			} else {
				clientes = clienteDao.listar();
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// Pega o Cliente Selecionado e atualiza a pagina
	public void selecionarCliente(ActionEvent evt) {
		try {
			ordemServico.setCliente((Cliente) evt.getComponent()
					.getAttributes().get("cliente"));
			setAtivabotao(true);
			if (ordemServico.getId() == 0) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("ordemServico.jsf");
			} else {
				// ordemServico.setCliente(null);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("ordemServicoupdate.jsf");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ///MOBILE
	// DETALHAR OS PARA ACOES
	public void prepararDetalhesOs(ActionEvent evt) {
		try {
			// PREPARA O OBJETO A SER APRESENTADO NA OUTRA TELA
			ordemServico = (OrdemServico) evt.getComponent().getAttributes()
					.get("ordeserv");
			ordemServico = ordemServicoDao.consultarPorId(ordemServico.getId());
			// PEGA OS VALORES TOTAIS DA OS
			totalEquipamentos = getValorTotalEquipamentos();
			totalServicos = getValorTotalServicos();
			totalOrdemServico = getValorTatalOrdemServico();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("ordemdeservicomobiledetalhes.jsf");

		} catch (SatiException e) {
			UtilFaces
					.addMensagemFaces("Houve um erro ao redirecionar para Essa página");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// REDIRECIONA A TELA DE AVISO PARA ORDEM DE SERVICO
	public void redirecionaOs(ActionEvent evt) {
		try {
			inicialize(null);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("ordemdeservicomobile.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ATIVA O TIPO DE ORDEM DE SERVIÇO
	public void ativarTipoOs(AjaxBehaviorEvent event) {
		if (tipoOs.contains("Equipamentos")) {
			System.out.println("Tipo de Ativação: " + tipoOs);
			setAtivaEquipamentoOs(true);
			setAtivaServicoOs(false);
			setAtivacbTipoos(true);
		}
		if (tipoOs.contains("Serviços")) {
			System.out.println("Tipo de Ativação: " + tipoOs);
			setAtivaServicoOs(true);
			setAtivaEquipamentoOs(false);
			setAtivacbTipoos(true);
			removeItensEquipamentoTipoOs();
		}
		if (tipoOs.contains("Ambos")) {
			System.out.println("Tipo de Ativação: " + tipoOs);
			setAtivaServicoOs(true);
			setAtivaEquipamentoOs(true);
			setAtivacbTipoos(true);
		}
	}

	// REMOVE ITENS EQUIPAMENTO - SELECAO TIPO DE OS
	public void removeItensEquipamentoTipoOs() {
		if (ordemServico.getItensEquipamentoList().size() > 0) {
			int i = ordemServico.getItensEquipamentoList().size() - 1;
			while (ordemServico.getItensEquipamentoList().size() > 0) {
				itemEquipamento = ordemServico.getItensEquipamentoList().get(i);
				ordemServico.removeItemEquipamento(itemEquipamento);
				i--;
			}
			totalEquipamentos = getValorTotalEquipamentos();
			totalOrdemServico = getValorTatalOrdemServico();
		}
	}

	// GTT E STT
	public List<OrdemServico> getOrdensServico() {
		return ordensServico;
	}

	public void setOrdensServico(List<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public EnumEstadoOrdemServico getEstadoOsSelecionado() {
		return estadoOsSelecionado;
	}

	public void setEstadoOsSelecionado(
			EnumEstadoOrdemServico estadoOsSelecionado) {
		this.estadoOsSelecionado = estadoOsSelecionado;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public OrdemServico getOsCancelar() {
		return osCancelar;
	}

	public void setOsCancelar(OrdemServico osCancelar) {
		this.osCancelar = osCancelar;
	}

	public ItemServico getItemServico() {
		return itemServico;
	}

	public void setItemServico(ItemServico itemServico) {
		this.itemServico = itemServico;
	}

	public ItemEquipamento getItemEquipamento() {
		return itemEquipamento;
	}

	public void setItemEquipamento(ItemEquipamento itemEquipamento) {
		this.itemEquipamento = itemEquipamento;
	}

	public BigDecimal getTotalEquipamentos() {
		return totalEquipamentos;
	}

	public void setTotalEquipamentos(BigDecimal totalEquipamentos) {
		this.totalEquipamentos = totalEquipamentos;
	}

	public BigDecimal getTotalServicos() {
		return totalServicos;
	}

	public void setTotalServicos(BigDecimal totalServicos) {
		this.totalServicos = totalServicos;
	}

	public BigDecimal getTotalOrdemServico() {
		return totalOrdemServico;
	}

	public void setTotalOrdemServico(BigDecimal totalOrdemServico) {
		this.totalOrdemServico = totalOrdemServico;
	}

	public OrdemServico getOsFechar() {
		return osFechar;
	}

	public void setOsFechar(OrdemServico osFechar) {
		this.osFechar = osFechar;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getNomedoCliente() {
		return nomedoCliente;
	}

	public void setNomedoCliente(String nomedoCliente) {
		this.nomedoCliente = nomedoCliente;
	}

	public Boolean getAtivabotao() {
		return ativabotao;
	}

	public void setAtivabotao(Boolean ativabotao) {
		this.ativabotao = ativabotao;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getTipoOs() {
		return tipoOs;
	}

	public void setTipoOs(String tipoOs) {
		this.tipoOs = tipoOs;
	}

	public Boolean getAtivaServicoOs() {
		return ativaServicoOs;
	}

	public void setAtivaServicoOs(Boolean ativaServicoOs) {
		this.ativaServicoOs = ativaServicoOs;
	}

	public Boolean getAtivaEquipamentoOs() {
		return ativaEquipamentoOs;
	}

	public void setAtivaEquipamentoOs(Boolean ativaEquipamentoOs) {
		this.ativaEquipamentoOs = ativaEquipamentoOs;
	}

	public Boolean getAtivacbTipoos() {
		return ativacbTipoos;
	}

	public void setAtivacbTipoos(Boolean ativacbTipoos) {
		this.ativacbTipoos = ativacbTipoos;
	}

}
//by Silas A.