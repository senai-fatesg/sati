package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.HistoricoLogin;
import br.com.ambientinformatica.sati.entidade.PerfilDoUsuario;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements
		UsuarioDao {

	private static final long serialVersionUID = 1L;

	@Override
	public Usuario buscarUsuariobyTecnico(String usuario) throws SatiException {
		try {

			String sql = "select usu from Usuario usu where usu.login =:user";

			Query query = em.createQuery(sql);
			query.setParameter("user", usuario);
			return (Usuario) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int verficaPrimeiroAcesso(String login) throws SatiException {
		List<HistoricoLogin> historicos = null;
		try {
			String sql = "select h from HistoricoLogin h where h.usuario.login = :login";
			Query query = em.createQuery(sql);
			query.setParameter("login", login);
			historicos = new ArrayList<HistoricoLogin>();
			historicos = query.getResultList();

		} catch (NoResultException e) {
			e.getMessage();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}

		return historicos.size();

	}

	@Override
	public boolean verificaLoginExistente(String login) throws SatiException {
		Usuario usuario = new Usuario();
		boolean exist = false;
		try {
			String sql = "select usu from Usuario usu where usu.login = :login";
			Query query = em.createQuery(sql);
			query.setParameter("login", login);
			usuario = (Usuario) query.getSingleResult();

			if (usuario != null) {
				exist = true;
			} else {
				exist = false;
			}

		} catch (NoResultException e) {
			e.getMessage();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}

		return exist;
	}

	@Override
	public void excluirUsuario(Usuario usuario) throws SatiException {
		try {
			PerfilDoUsuario papel = new PerfilDoUsuario();
			
			for(int i=0; i< usuario.obterListaDePerfis().size() ;i++){
				papel = usuario.obterListaDePerfis().get(i);			
			}
			//PRIMEIRA EXCLUSAO DO PAPEL DO USUARIO
			String sql1 ="delete from perfildousuario papel where papel.id = :idpapel";
			Query query = em.createQuery(sql1);
			query.setParameter("idpapel", papel.getId());
			@SuppressWarnings("unused")
			int pexc = query.executeUpdate();
			
			//SEGUNDA EXCLUSAO DO USUARIO	
			String sql2 = "delete from Usuario usu where usu.login = :login";
			Query query2 = em.createQuery(sql2);
			query2.setParameter("login", usuario.getLogin());
			@SuppressWarnings("unused")
			int result = query.executeUpdate();
			//INICIALIZANDO
			usuario = new Usuario();
			papel = new PerfilDoUsuario();
			
		} catch (NoResultException e) {
			e.getMessage();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
	}


}
//by Silas A.