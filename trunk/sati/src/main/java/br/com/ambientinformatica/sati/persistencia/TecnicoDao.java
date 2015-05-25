package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface TecnicoDao extends Persistencia<Tecnico> {

	public Tecnico buscarTecnicoByUsuario(String usuario) throws SatiException;
	
	public List<Tecnico> listarTecnico();
}
