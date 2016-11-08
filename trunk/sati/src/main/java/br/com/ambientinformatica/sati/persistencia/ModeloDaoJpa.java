package br.com.ambientinformatica.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Modelo;

@Repository("modeloDao")
public class ModeloDaoJpa extends PersistenciaJpa<Modelo> implements ModeloDao {
	
}
