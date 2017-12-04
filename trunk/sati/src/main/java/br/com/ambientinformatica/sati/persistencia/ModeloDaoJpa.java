package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository
public class ModeloDaoJpa extends PersistenciaJpa<Modelo> implements ModeloDao {

	private static final long serialVersionUID = 1L;

	/**
	 * retonar todos os modelos !
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Modelo> listarModelo() throws SatiException, PersistenciaException {
		
		
		List<Modelo> modelos= new ArrayList<Modelo>();
		try {
			String sql = "select * from Modelo ";
			Query query = em.createQuery(sql);
			modelos = query.getResultList();
			
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return modelos;
		
		
		
		
	}

	@Override
	public List<Modelo> listarPorNome(String nome) {

		return null;
	}
	
}
