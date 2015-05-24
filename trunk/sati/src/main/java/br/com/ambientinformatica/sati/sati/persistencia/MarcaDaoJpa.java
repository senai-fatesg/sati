package br.com.ambientinformatica.sati.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.sati.entidade.Marca;

@Repository("marcaDao")
public class MarcaDaoJpa extends PersistenciaJpa<Marca> implements MarcaDao{

   private static final long serialVersionUID = 1L;

}
