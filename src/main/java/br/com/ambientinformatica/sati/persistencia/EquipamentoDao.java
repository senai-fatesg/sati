package br.com.ambientinformatica.sati.persistencia;


import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.util.SatiException;

public interface EquipamentoDao extends Persistencia<Equipamento> {
	
	public List<Equipamento> listarPorNome(String nome) throws SatiException, PersistenciaException;
	
	public List<Equipamento> listarEquipamento() throws SatiException,PersistenciaException;

}
