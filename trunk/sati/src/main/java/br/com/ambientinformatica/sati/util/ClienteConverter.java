package br.com.ambientinformatica.sati.util;

import java.io.Serializable;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;
import br.com.ambientinformatica.util.Entidade;

@FacesConverter("clienteConverter")
public class ClienteConverter extends Entidade implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
				
		return null;
	}  


}  
//by Silas A.
