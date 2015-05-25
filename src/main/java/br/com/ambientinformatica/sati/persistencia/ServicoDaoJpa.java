package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("servicoDao")
public class ServicoDaoJpa extends PersistenciaJpa<Servico> implements
		ServicoDao {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Servico> listarServico() throws SatiException,
			PersistenciaException {
		List<Servico> servicos = new ArrayList<Servico>();
		try {
			String sql = "select s from Servico s order by s.descricao asc";
			Query query = em.createQuery(sql);
			servicos = query.getResultList();

		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return servicos;
	}

}
//by Silas A.