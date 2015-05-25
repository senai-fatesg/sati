package br.com.ambientinformatica.sati.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.sati.entidade.EnumPapelUsuario;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.persistencia.UsuarioDao;
import br.com.ambientinformatica.util.UtilLog;

@Service("inicializadorSistema")
public class InicializadorSistema {

	@Autowired
	private UsuarioDao usuarioDao;

	@PostConstruct
	public void iniciar() {
		inicializarUsuarioAdmin();
	}

	private void inicializarUsuarioAdmin() {
		try {
			List<Usuario> usuarios = usuarioDao.listar();
			if (usuarios.isEmpty()) {
				Usuario usu = new Usuario();
				usu.setNome("admin");
				usu.setLogin("admin");
				usu.setSenhaNaoCriptografada("123456");
				usu.addPapel(EnumPapelUsuario.ADMIN);
				usu.addPapel(EnumPapelUsuario.USUARIO);
				usuarioDao.incluir(usu);
				UtilLog.getLog().info(
						"*** USU�?RIO admin CRIADO com a senha 123456 ***");
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
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(
				false);
		session.invalidate();
	}

}
