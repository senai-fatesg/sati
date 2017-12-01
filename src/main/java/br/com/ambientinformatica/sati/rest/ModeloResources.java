package br.com.ambientinformatica.sati.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.SpringModelMBean;
import org.springframework.stereotype.Component;

import br.com.ambientinformatica.sati.entidade.Modelo;

@Path("/modelo")
@Component
public class ModeloResources {

	
	@Autowired
	private ModeloService modeloService;
	
	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response listar() {
		
		List<Modelo> modelo = getModeloService().listar();
		
		return Response.status(Status.OK).entity(modelo).build();
	}

	
	
	@PUT
	@Path("/{id}")
    @Consumes("application/json")
    public void atualizar(@PathParam("id") Integer id, String mo) { 
		
		Modelo modelo =(Modelo) modeloService.buscarPor(mo);
		modeloService.atualizar(modelo);	
	
	}



	public ModeloService getModeloService() {
		
		if(modeloService == null){
			try {
				
				modeloService  = new ModeloService();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		return modeloService;
	}




	
	
	
	
	
	
	
	
}
