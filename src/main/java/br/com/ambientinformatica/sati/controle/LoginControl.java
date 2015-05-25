package br.com.ambientinformatica.sati.controle;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;
import br.com.ambientinformatica.sati.persistencia.UsuarioDao;
import br.com.ambientinformatica.sati.util.InicializadorSistema;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.entidade.Usuario;

@Controller("LoginControl")
@Scope("conversation")
public class LoginControl {

	InicializadorSistema ini = new InicializadorSistema();
	private Tecnico tecnico = new Tecnico();
	private String primeiroNome;
	private String login;
	@Autowired
	private TecnicoDao tecnicoDao;
	@Autowired
	private UsuarioDao usuarioDao;

	@PostConstruct
	public void init() {
		buscarTecnicobyUsuario(null);
	}

	//// BUSCA O TECNICO LOGADO NO SISTEMA APARTIR DO SEU USUARIO
	public void buscarTecnicobyUsuario(ActionEvent evt) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			login = external.getRemoteUser();

			tecnico = tecnicoDao.buscarTecnicoByUsuario(login);
			if (tecnico == null) {
				primeiroNome = login;
			} else {
				//PEGA O PRIMEIRO NOME DO TECNICO
				String nomeCompleto = tecnico.getNome();
				primeiroNome = nomeCompleto.split(" ")[0];
				verificaPrimeiroAcesso(login);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}

	}

	// FAZ O LOGOUT DO SISTEMA
	public void logout() throws IOException {
		ini.logout();
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("login.jsp");
	}
	
	// VERIFICA PRIMEIRO ACESSO
	public void verificaPrimeiroAcesso(String login) {
		try {
			int nAcesso = usuarioDao.verficaPrimeiroAcesso(login);
			if (nAcesso == 1) {
				// REDIRECIONA PARA A PAGINA DE ALTERACAO DE SENHA
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("loginmobilealterar.jsf");
			} else {
				
				// REDIRECIONA PARA A PAGINA INCIAL-MOBILE PARA TECNICO
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("iniciomobile.jsf");
			}
		} catch (SatiException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// REDIRECIONA A TELA DE INICIO APOS MENSAGEN DE EPI
	public void redirecionaInicio(ActionEvent evt) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("iniciomobile.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	


	// GTT E STT
	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


}
//by Silas A.