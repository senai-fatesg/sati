package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("marcaDao")
public class MarcaDaoJpa extends PersistenciaJpa<Marca> implements MarcaDao {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> listarPorNome(String nome) {
		Query q = this.em
				.createQuery("from Marca as a where a.nome like :nome");
		q.setParameter("nome", "%" + nome + "%");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> listarMarca() throws SatiException,
			PersistenciaException {
		List<Marca> marcas = new ArrayList<Marca>();
		try {
			String sql = "select m from Marca m order by m.nome asc";
			Query query = em.createQuery(sql);
			marcas = query.getResultList();
			
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return marcas;
	}

}
