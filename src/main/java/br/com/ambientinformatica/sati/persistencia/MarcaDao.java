package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.util.SatiException;

public interface MarcaDao extends Persistencia<Marca> {

	public List<Marca> listarPorNome(String nome);
	
	public List<Marca> listarMarca() throws SatiException, PersistenciaException;
}
