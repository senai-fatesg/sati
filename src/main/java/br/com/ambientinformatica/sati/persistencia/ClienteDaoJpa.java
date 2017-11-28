package br.com.ambientinformatica.sati.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;
//import org.eclipse.persistence.sessions.factories.SessionFactory;

@Repository("clienteDao")
public class ClienteDaoJpa extends PersistenciaJpa<Cliente> implements ClienteDao {

	private static final long serialVersionUID = 1L;

	private Session session;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listarPorNome(String nome) throws SatiException,
			PersistenciaException {
		try {
			String sql = "select c from Cliente c where c.nomecliente like :nome";
			Query query = em.createQuery(sql);
			query.setParameter("nome", "%" + nome + "%");
			return query.getResultList();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listarCliente() throws SatiException,
			PersistenciaException {
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
//			String sql = "select c from Cliente c order by c.nomecliente asc";
//			Query query = em.createQuery(sql);
//			clientes = query.getResultList();
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
			 
			TypedQuery<Cliente> typedQuery = em.createQuery(
			    query.select(
			       query.from(Cliente.class)
			    )
			);
			
			clientes = typedQuery.getResultList();
			

		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return clientes;
	}

	// VERIFICA SE JA EXISTE O CPF OU CNPJ DO CLIENTE
	
	public boolean verificaCpfCnpjExistente(String cpfCnpj, String cpfCnpjPertence)
			throws SatiException {
		Cliente cliente = new Cliente();
		boolean exist = false;
		try {
			/*String sql = "select cli from Cliente cli where cli.cpfCnpj = :cpfCnpj";
			Query query = em.createQuery(sql);
			query.setParameter("cpfCnpj", cpfCnpj);
			cliente = (Cliente) query.getSingleResult();

			if (cliente != null && (!cliente.getCpfCnpj().equals(cpfCnpjPertence))) {
				exist = true;
			} else {
				exist = false;
			}*/
			
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
			Root<Cliente> root = query.from(Cliente.class);

			Path<String> cnpj = root.<String> get("cpfCnpj");
						
			List<Predicate> predicates = new ArrayList<Predicate>();		
			
			query.where((Predicate[]) predicates.toArray(new Predicate[0]));

			TypedQuery<String> typedQuery = em.createQuery(query);
			typedQuery.setHint("org.hibernate.cacheable", "true");

			if ( typedQuery.getResultList().size() != 0){
				exist = true;
			}else
				exist = false;
			
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
		return exist;
			
			

	}

	@Override
	public Cliente selecionarPorId(Long id) throws SatiException {
		// TODO Auto-generated method stub
		
		Cliente cliente = new Cliente();
		
		try {
			String sql = "select cli form cliente cli where cli.Id = :id";
			Query query = em.createQuery(sql);
			query.setParameter("id", id);
			cliente = (Cliente)query.getSingleResult();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage(), e);
		}
		
		return cliente;
		
	}
	
	

}
