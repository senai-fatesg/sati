package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.util.SatiException;

public interface ClienteDao extends Persistencia<Cliente>{

	public List<Cliente> listarPorNome(String nome) throws SatiException, PersistenciaException;
	
	public List<Cliente> listarCliente() throws SatiException, PersistenciaException;
	
	public boolean verificaCpfCnpjExistente(String cpfCnpj, String cpfCnpjPertence) throws SatiException;

}
