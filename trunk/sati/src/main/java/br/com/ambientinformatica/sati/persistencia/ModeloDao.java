package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.util.SatiException;

public interface ModeloDao extends Persistencia<Modelo> {
	
	public List<Modelo> listarPorNome(String nome);
	
	public List<Modelo> listarModelo() throws SatiException, PersistenciaException;


}
