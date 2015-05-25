package br.com.ambientinformatica.sati.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;

@FacesConverter("equipamentoConverter")
public class EquipamentoConverter implements Converter {

	private EquipamentoDao equipamentoDao = (EquipamentoDao) FabricaAbstrata
			.criarObjeto("equipamentoDao");

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Equipamento) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Equipamento equipamento = new Equipamento();
			try {
				int id = Integer.parseInt(value);

				try {
					equipamento = equipamentoDao.consultar(id);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				// throw new ConverterException(new
				// FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error",
				// "Cliente escolhido não é válido"));
				return null;
			}
			return equipamento;
		} else {
			return null;
		}
	}
}
