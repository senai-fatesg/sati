package br.com.ambientinformatica.sati.controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;
import br.com.ambientinformatica.sati.persistencia.UsuarioDao;
import br.com.ambientinformatica.sati.util.SatiException;

@Controller("TecnicoControl")
@Scope("conversation")
public class TecnicoControl {

	private Tecnico tecnico = new Tecnico();
	private Usuario usuario = new Usuario();

	@Autowired
	private TecnicoDao tecnicoDao;

	@Autowired
	private UsuarioDao usuarioDao;

	private List<Tecnico> tecnicos = new ArrayList<Tecnico>();

	@PostConstruct
	public void init() {
		listar(null);
	}

	// /INCLUI UM NOVO TECNICO
	public void confirmar(ActionEvent evt) {
		try {
			if (!verificaLogin()) {
				// INCLUIR TECNICO
				usuario.setSenhaNaoCriptografada(usuario.getSenha());
				usuario.setNome(usuario.getLogin());
				usuario.addPapel(EnumPapelUsuario.USUARIO);
				usuarioDao.incluir(usuario);
				tecnico.setUsuario(usuario);
				tecnicoDao.incluir(tecnico);
				listar(evt);
				inicialize(null);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("tecnico.jsf");
			} else {
				FacesContext.getCurrentInstance().addMessage(
						"Sati System",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Já existe esse usuário no Sistema", null));
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// ALTERA DADOS DO TECNICO - COM USUARIO
	public void alterar(ActionEvent evt) {
		try {
			if (!usuario.getSenha().isEmpty()) {
				usuario.setSenhaNaoCriptografada(usuario.getSenha());
				usuarioDao.alterar(usuario);
			}
			tecnicoDao.alterar(tecnico);
			listar(evt);

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("tecnico.jsf");

			inicialize(null);

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			tecnicos = tecnicoDao.listarTecnico();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);

		}

	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			tecnico = (Tecnico) evt.getComponent().getAttributes()
					.get("tecnico");
			tecnico = tecnicoDao.consultar(tecnico.getId());
			tecnicoDao.excluirPorId(tecnico.getId());
			usuarioDao.excluirUsuario(tecnico.getUsuario());
			
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

	public void preparaAlterar(ActionEvent evt) {
		try {
			tecnico = (Tecnico) evt.getComponent().getAttributes()
					.get("tecnico");
			tecnico = tecnicoDao.consultar(tecnico.getId());
			usuario = usuarioDao.buscarUsuariobyTecnico(tecnico.getUsuario()
					.getLogin());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("tecnicoupdate.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// LIMPA OBJET TECNICO E USUARIO
	public void inicialize(ActionEvent actionEvent) {
		tecnico = new Tecnico();
		usuario = new Usuario();
	}

	// AVANÇAR TELA DO WIZARD CADASTRO
	public String proximaTela(FlowEvent event) {
		return event.getNewStep();
	}

	// /ATUALIZAR SENHA AO PRIMEIRO ACESSO
	public void atualizarSenha(ActionEvent evt) throws Exception {
		try {
			// PEGA O USUARIO DA SESSAO
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			String login = external.getRemoteUser();

			usuario.setLogin(login);
			usuario.setSenhaNaoCriptografada(usuario.getSenha());
			usuarioDao.alterar(usuario);
			usuario = new Usuario();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("loginmobileepiaviso.jsf");

		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//VERIFICA SE JA EXISTE O LOGIN NA BASE DE DADOS
	public boolean verificaLogin() {
		boolean exist = false;
		try {
			exist = usuarioDao.verificaLoginExistente(usuario.getLogin());
		} catch (SatiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exist;
	}

	// GTT E STT
	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public List<Tecnico> getTecnicos() {
		return tecnicos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
//by Silas A.