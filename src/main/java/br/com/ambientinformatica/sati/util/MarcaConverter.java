package br.com.ambientinformatica.sati.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.persistencia.MarcaDao;


@FacesConverter("marcaConverter")
public class MarcaConverter implements Converter {
	
	private MarcaDao marcaDao = (MarcaDao) FabricaAbstrata
			.criarObjeto("marcaDao");

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component,
			Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			return String.valueOf(((Marca) value).getId());
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value != null && !value.trim().equals("")) {
			Marca marca = new Marca();
			try {
				int id = Integer.parseInt(value);

				try {
					marca = marcaDao.consultar(id);
				} catch (PersistenciaException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException exception) {
				// throw new ConverterException(new
				// FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error",
				// "Message"));
				return null;
			}
			return marca;
		} else {
			return null;
		}
	}

}
//by Silas A.
