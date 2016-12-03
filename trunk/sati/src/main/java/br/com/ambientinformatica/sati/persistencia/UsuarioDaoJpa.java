package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.HistoricoLogin;
import br.com.ambientinformatica.sati.entidade.PapelUsuario;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements
		UsuarioDao {

	private static final long serialVersionUID = 1L;

	@Override
	public Usuario buscarUsuariobyTecnico(String login) throws SatiException {
		try {

			String sql = "select u from Usuario u where u.login =:login";

			Query query = em.createQuery(sql);
			query.setParameter("login", login);
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
			String sql = "select u from Usuario u where u.login =:login";
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
			PapelUsuario perfilDoUsuario = new PapelUsuario();
			
			for(int i = 0; i < usuario.obterListaDePerfis().size(); i++){
				perfilDoUsuario = usuario.obterListaDePerfis().get(i);
				
				//PRIMEIRA EXCLUSAO DO PAPEL DO USUARIO
				String sql1 ="delete from PapelUsuario p where p.id = :idPerfilDoUsuario";
				Query query = em.createQuery(sql1);
				query.setParameter("idPerfilDoUsuario", perfilDoUsuario.getId());
				@SuppressWarnings("unused")
				int pexc = query.executeUpdate();
			}
			
			//SEGUNDA EXCLUSAO DO USUARIO	
			String sql2 = "delete from Usuario u where u.login = :login";
			Query query2 = em.createQuery(sql2);
			query2.setParameter("login", usuario.getLogin());
			@SuppressWarnings("unused")
			int result = query2.executeUpdate();
			
			//INICIALIZANDO
			usuario = new Usuario();
			perfilDoUsuario = new PapelUsuario();
			
		} catch (NoResultException e) {
			e.getMessage();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
	}


}
//by Silas A.