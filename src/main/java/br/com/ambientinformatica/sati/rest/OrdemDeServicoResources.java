package br.com.ambientinformatica.sati.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.ambientinformatica.sati.entidade.OrdemServico;

@Path("/ordem")
@Component
public class OrdemDeServicoResources {
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrdemServico> listar() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usuario = auth.getName();
		
		List<OrdemServico> ordensDeServico = new ArrayList<>();
		try {
			ordensDeServico = ordemServicoService.listar(usuario);
		} catch (Exception e) {
			throw e;
		}
		return ordensDeServico;
	}
	
	@PUT
	@Path("/{id}")
    @Consumes("application/json")
    public void atualizar(@PathParam("id") Long id, OrdemServico os) { 
		
		OrdemServico ordemDeServico = ordemServicoService.buscarPor(id);
		
		if (br.com.ambientinformatica.sati.entidade.Status.ATENDENDO.equals(os.getEstado())) {
			ordemDeServico.setEstado(br.com.ambientinformatica.sati.entidade.Status.ATENDENDO);
		} else {
			ordemDeServico.setEstado(br.com.ambientinformatica.sati.entidade.Status.ATENDIDA);
			ordemDeServico.setDataFechamento(new Date(System.currentTimeMillis()));
		}
		
		ordemServicoService.atualizar(ordemDeServico);	
	}


	
	
}
