package br.com.ambientinformatica.sati.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.sati.entidade.Papel;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

	@Autowired
	private UsuarioDao usuarioDao;
	
	@PostConstruct
	public void iniciar(){
		inicializarUsuarioAdmin();
	}
	
	private void inicializarUsuarioAdmin(){
		try {
			List<Usuario> usuarios = usuarioDao.listar();
			if(usuarios.isEmpty()){
				Usuario usu = new Usuario();
				usu.setNome("admin");
				usu.setLogin("admin");
				usu.setSenha("123456");
				usu.adicionar(Papel.ADMIN);
				usu.adicionar(Papel.USUARIO);
				usuarioDao.incluir(usu);
				UtilLog.getLog().info("*** USUARIO admin CRIADO com a senha 123456 ***");
			}
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
		}
	}
	
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		invalidateSession();
	}
	
	private void invalidateSession() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
	}
}
