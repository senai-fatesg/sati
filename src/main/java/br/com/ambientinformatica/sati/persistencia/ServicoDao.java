package br.com.ambientinformatica.sati.persistencia;


import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface ServicoDao extends Persistencia<Servico>{
	
	public List<Servico> listarServico() throws SatiException,PersistenciaException;
}
