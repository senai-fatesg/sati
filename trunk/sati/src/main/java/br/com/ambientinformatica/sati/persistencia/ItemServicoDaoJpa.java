package br.com.ambientinformatica.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.ItemServico;

@Repository("ItemServicoDao")
public class ItemServicoDaoJpa extends PersistenciaJpa<ItemServico> implements ItemServicoDao {


	private static final long serialVersionUID = 1L;

}
