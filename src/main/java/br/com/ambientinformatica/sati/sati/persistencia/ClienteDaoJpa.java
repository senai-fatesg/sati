package br.com.ambientinformatica.sati.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.sati.entidade.Cliente;

@Repository("contatoDao")
public class ClienteDaoJpa extends PersistenciaJpa<Cliente> implements ClienteDao{

   private static final long serialVersionUID = 1L;

}
