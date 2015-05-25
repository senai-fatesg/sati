package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("equipamentoDao")
public class EquipamentoDaoJpa extends PersistenciaJpa<Equipamento> implements EquipamentoDao {
	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Equipamento> listarPorNome(String nome) throws SatiException, PersistenciaException{
		try{
			String sql = "select e from Equipamento e where e.nome like :nome";
			Query query = em.createQuery(sql);
			query.setParameter("nome", "%" + nome + "%");
			return query.getResultList();
		}catch(Exception e){
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Equipamento> listarEquipamento() throws SatiException,PersistenciaException{
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		try{
			String sql = "select eq from Equipamento eq order by eq.nome asc";
			Query query = em.createQuery(sql);
			equipamentos = query.getResultList();
			
		}catch(Exception e ){
			UtilLog.getLog().error(e.getMessage(),e);
			throw new PersistenciaException(e.getMessage());
		}
		return equipamentos;
	}
	

}
//by Silas A.