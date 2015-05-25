package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("tecnicoDao")
public class TecnicoDaoJpa extends PersistenciaJpa<Tecnico> implements
		TecnicoDao {

	private static final long serialVersionUID = 1L;

	// LISTA TECNICO POR ORDEM DESC
	@SuppressWarnings("unchecked")
	@Override
	public List<Tecnico> listarTecnico() {
		List<Tecnico> tecnicos = new ArrayList<Tecnico>();
		try {
			String sql = "select tec from Tecnico tec order by tec.id desc";
			Query query = em.createQuery(sql);
			tecnicos = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
		}
		return tecnicos;
	}

	// BUSCA TECNICO PELO USUARIO LOGADO
	@Override
	public Tecnico buscarTecnicoByUsuario(String usuario) throws SatiException {
		try {

			String sql = "select tec from Tecnico tec where tec.usuario.login =:user";

			Query query = em.createQuery(sql);
			query.setParameter("user", usuario);
			return (Tecnico) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
	}

}
//by Silas A.