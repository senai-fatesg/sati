package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.util.SatiException;

@Repository("modeloDao")
public class ModeloDaoJpa extends PersistenciaJpa<Modelo> implements ModeloDao {




	@Override
	public List<Modelo> listarModelo() throws SatiException, PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Modelo> listarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
