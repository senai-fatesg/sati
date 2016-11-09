package br.com.ambientinformatica.sati.util;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDaoJpa;
import br.com.ambientinformatica.util.Entidade;

@FacesConverter("equipamentoConverter")
public class EquipamentoConverter extends Entidade implements Serializable {

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
