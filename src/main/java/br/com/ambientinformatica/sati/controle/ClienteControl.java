package br.com.ambientinformatica.sati.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.entidade.Endereco;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;
import br.com.ambientinformatica.sati.persistencia.EnderecoDao;
import br.com.ambientinformatica.sati.util.CepWebService;
import br.com.ambientinformatica.sati.util.SatiException;

@Controller("ClienteControl")
@Scope("conversation")
public class ClienteControl {

	private Cliente cliente = new Cliente();
	private Boolean tipocliente;
	private Endereco endereco = new Endereco();
	private String cep = null;
	private String nomedoCliente; // filtro mobile
	private String cpfCnpjPertence;
	private List<Cliente> clientes = new ArrayList<Cliente>();
	@Autowired
	private ClienteDao clienteDao;
	@Autowired
	private EnderecoDao enderecoDao;

	@PostConstruct
	public void init() {
		listar(null);
	}

	// INCLUI UM NOVO CLIENTE OU ALTERA
	public void confirmar(ActionEvent evt) {
		try {
			if (!verificaCampos()) {

				if (cliente.getId() == 0) {
					enderecoDao.incluir(cliente.getEndereco());
					clienteDao.incluir(cliente);
					listar(evt);
				} else {
					if (tipocliente == false) {
						cliente.setRazaoSocial(" ");
					}
					enderecoDao.alterar(cliente.getEndereco());
					clienteDao.alterar(cliente);
					listar(evt);
				}
				inicialize(null);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("cliente.jsf");
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			clientes = clienteDao.listarCliente();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			cliente = (Cliente) evt.getComponent().getAttributes()
					.get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			clienteDao.excluirPorId(cliente.getId());
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
		cliente = new Cliente();
		endereco = new Endereco();
		cep = null;
		tipocliente = false;
		cpfCnpjPertence=null;
	}

	public String proximaTela(FlowEvent event) {
		return event.getNewStep();
	}

	// Encontrar Cep
	public void encontraCEP() {
		CepWebService cepWebService = new CepWebService(getCep());

		if (cepWebService.getResultado() == 1) {
			endereco.setId(cliente.getEndereco().getId());
			endereco.setLogradouro(cepWebService.getLogradouro());
			endereco.setUf(cepWebService.getEstado());
			endereco.setCidade(cepWebService.getCidade());
			endereco.setSetor(cepWebService.getBairro());
			endereco.setCep(cep);
			cliente.setEndereco(endereco);

		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Servidor não está respondendo",
							"Servidor não está respondendo"));
		}
	}

	// PREPARA PRA ALTERAR O CLIENTE
	public void preparaAlterar(ActionEvent evt) {
		tipocliente = false;
		try {
			cliente = (Cliente) evt.getComponent().getAttributes()
					.get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			cpfCnpjPertence = cliente.getCpfCnpj();
			if (cliente.getCpfCnpj().contains("/")) {
				tipocliente = true;
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("clienteupdate.jsf");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void buscarbyid(ActionEvent evt) {
		try {
			cliente = clienteDao.consultar(cliente.getId());
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// /MOBILE
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

	// VERIFICA CAMPOS DA TELA DE CADASTRO DE CLIENTE VAZIOS
	public boolean verificaCampos() {
		boolean exist = false;
		//VERIFICA CAMPOS DADOS PESSOAS CLIENTE
		if(cliente.getNomecliente().isEmpty()){
			UtilFaces.addMensagemFaces("Campo Nome do Cliente está Vazio!");
			exist = true;
		}
		if(tipocliente==true){
			//IGUAL A TRUE = TIPO CLIENTE CNPJ
			if(cliente.getRazaoSocial().isEmpty()){
				UtilFaces.addMensagemFaces("Campo Razão Social está Vazio!");
				exist=true;
			}			if(cliente.getCpfCnpj().isEmpty()){
				UtilFaces.addMensagemFaces("Campo CNPJ está Vazio!");
				exist=true;
			}
			
			//VERIFICA SE JA EXISTE O MESMO CNPJ
			try {
				exist = clienteDao.verificaCpfCnpjExistente(cliente.getCpfCnpj(),cpfCnpjPertence);
				if(exist==true){
				UtilFaces.addMensagemFaces("Já Existe este CNPJ no Sistema!");
				}
			} catch (SatiException e) {
				e.printStackTrace();
			}
		}
		if(tipocliente==false){
			if(cliente.getCpfCnpj().isEmpty()){
				UtilFaces.addMensagemFaces("Campo CPF está Vazio!");
				exist=true;
			}
			
			//VERIFICA SE JA EXISTE O MESMO CPF
			try {
				exist = clienteDao.verificaCpfCnpjExistente(cliente.getCpfCnpj(),cpfCnpjPertence);
				if(exist==true){
				UtilFaces.addMensagemFaces("Já Existe este CPF no Sistema!");
				}
			} catch (SatiException e) {
				e.printStackTrace();
			}
		}
		if(cliente.getTelefone().isEmpty()){
			UtilFaces.addMensagemFaces("Campo telefone está Vazio!");
			exist=true;
		}		
		
		//VERIFICA CAMPOS DE ENDERECO
		if (cliente.getEndereco().getLogradouro().isEmpty()) {
			UtilFaces.addMensagemFaces("Campo logradouro está vazio!");
			exist = true;
		}
		if (cliente.getEndereco().getComplemento().isEmpty()) {
			 UtilFaces.addMensagemFaces("Campo complemento está vazio!");
			 exist = true;
		}
		if (cliente.getEndereco().getSetor().isEmpty()) {
			UtilFaces.addMensagemFaces("Campo Bairro está vazio!");
			exist = true;
		}
		if (cliente.getEndereco().getCidade().isEmpty()) {
			UtilFaces.addMensagemFaces("Campo Cidade está vazio!");
			exist = true;
		}
		if (cliente.getEndereco().getUf().isEmpty()) {
			UtilFaces.addMensagemFaces("Campo Estado está vazio!");
			exist = true;
		}
		
		return exist;
	}

	
	
	// GTT E STT
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Boolean getTipocliente() {
		return tipocliente;
	}

	public void setTipocliente(Boolean tipocliente) {
		this.tipocliente = tipocliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNomedoCliente() {
		return nomedoCliente;
	}

	public void setNomedoCliente(String nomedoCliente) {
		this.nomedoCliente = nomedoCliente;
	}

	public String getCpfCnpjPertence() {
		return cpfCnpjPertence;
	}

	public void setCpfCnpjPertence(String cpfCnpjPertence) {
		this.cpfCnpjPertence = cpfCnpjPertence;
	}

}

//by Silas A.