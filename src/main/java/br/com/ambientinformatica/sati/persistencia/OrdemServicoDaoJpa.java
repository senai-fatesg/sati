package br.com.ambientinformatica.sati.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("OrdemServicoDao")
public class OrdemServicoDaoJpa extends PersistenciaJpa<OrdemServico> implements
		OrdemServicoDao {

	private static final long serialVersionUID = 1L;

	// CONSUTA OS POR ID
	public OrdemServico consultarPorId(Long id) throws SatiException {
		try {
			Query query = em
					.createQuery("select os from OrdemServico os where os.id = :id");
			query.setParameter("id", id);
			return (OrdemServico) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
	}

	// LISTA ORDEM DE SERVICO DE ACORDO COM A DATA INICIO E FIM E O STATUS
	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarPorDataEmissao(long id,
			Date dataHoraInicio, Date dataHoraFim,
			EnumEstadoOrdemServico estadoOsSelecionado, int idTecnico)
			throws SatiException {
		try {
			String sql = "select distinct os from OrdemServico os " +
			// "left join fetch os.cliente c " +
			// "left join fetch os.itensServicos is " +
					"where 1 = 1 ";
			sql += " and os.tecnico.id = :idTecnico";
			if (id != 0) {
				sql += " and os.id =:id";
			}
			if (dataHoraInicio != null && dataHoraFim != null) {
				sql += " and os.dataEmissao >= :dataHoraInicio and os.dataEmissao <= :dataHoraFim";
			}

			if (estadoOsSelecionado != null) {
				sql += " and os.estado = :estado";
			} else {
				sql += " and os.estado != :estado";
			}
			sql += " order by os.id desc";
			Query query = em.createQuery(sql);
			if (id != 0) {
				query.setParameter("id", id);
			}
			if (dataHoraInicio != null) {
				query.setParameter("dataHoraInicio", dataHoraInicio);
			}
			if (dataHoraFim != null) {
				query.setParameter("dataHoraFim", dataHoraFim);
			}
			if (idTecnico != 0) {
				query.setParameter("idTecnico", idTecnico);
			}
			if (estadoOsSelecionado != null) {
				query.setParameter("estado", estadoOsSelecionado);
			} else {
				query.setParameter("estado", EnumEstadoOrdemServico.CANCELADA);
			}
			return query.getResultList();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage());
		}
	}

	// LISTA AS ORDENS DE SERVICO EM ATENDIMENTO DO TECNICO
	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarPorOsAtendimento(
			EnumEstadoOrdemServico enumEstadoOrdemServico, int idTecnico)
			throws SatiException {
		try {
			String sql = "select distinct os from OrdemServico os "
					+ "where 1 = 1";
			if (enumEstadoOrdemServico != null) {
				sql += " and os.estado = :estado";
			}
			if (idTecnico != 0) {
				sql += " and os.tecnico.id = :idTecnico";
			}
			sql += " order by os.id desc";
			Query query = em.createQuery(sql);
			if (enumEstadoOrdemServico != null) {
				query.setParameter("estado", enumEstadoOrdemServico);
			}
			if (idTecnico != 0) {
				query.setParameter("idTecnico", idTecnico);
			}
			return query.getResultList();

		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage());
		}
	}

	// // ADMINISTRADOR
	// LISTA AS ORDENS DE SERVICO EM ATENDIMENTO DO TECNICO
	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarPorOsAtendimentoAdmin(
			EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException {
		try {
			String sql = "select distinct os from OrdemServico os "
					+ "where 1 = 1";
			if (estadoOsSelecionado != null) {
				sql += " and os.estado = :estado";
			}
			sql += " order by os.id desc";
			Query query = em.createQuery(sql);
			if (estadoOsSelecionado != null) {
				query.setParameter("estado", estadoOsSelecionado);
			}
			return query.getResultList();

		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage());
		}
	}

	// LISTA ORDEM DE SERVICO SEM TECNICO - ADMIN -DE ACORDO COM A DATA INICIO E
	// FIM E O STATUS
	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarPorDataEmissaoSemTecnico(long id,
			Date dataHoraInicio, Date dataHoraFim,
			EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException {
		try {
			String sql = "select distinct os from OrdemServico os " +
			// "left join fetch os.cliente c " +
			// "left join fetch os.itensServicos is " +
					"where 1 = 1 ";
			if (id != 0) {
				sql += " and os.id =:id";
			}
			if (dataHoraInicio != null && dataHoraFim != null) {
				sql += " and os.dataEmissao >= :dataHoraInicio and os.dataEmissao <= :dataHoraFim";
			}

			if (estadoOsSelecionado != null) {
				sql += " and os.estado = :estado";
			} else {
				sql += " and os.estado != :estado";
			}
			sql += " order by os.id desc";
			Query query = em.createQuery(sql);
			if (id != 0) {
				query.setParameter("id", id);
			}
			if (dataHoraInicio != null) {
				query.setParameter("dataHoraInicio", dataHoraInicio);
			}
			if (dataHoraFim != null) {
				query.setParameter("dataHoraFim", dataHoraFim);
			}
			if (estadoOsSelecionado != null) {
				query.setParameter("estado", estadoOsSelecionado);
			} else {
				query.setParameter("estado", EnumEstadoOrdemServico.CANCELADA);
			}
			return query.getResultList();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage());
		}
	}

}
//by Silas A.