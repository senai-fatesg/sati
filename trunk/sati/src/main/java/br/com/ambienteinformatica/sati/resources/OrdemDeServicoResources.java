package br.com.ambienteinformatica.sati.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ambienteinformatica.sati.services.OrdemServicoService;
import br.com.ambientinformatica.sati.entidade.OrdemServico;

@Path(value = "/servico")
public class OrdemDeServicoResources {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usuario = auth.getName();
		
		List<OrdemServico> ordensDeServico = ordemServicoService.listar(usuario);
		
		return Response.status(Status.OK).entity(ordensDeServico).build();
	}
	
}
