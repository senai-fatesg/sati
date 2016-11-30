package br.com.ambientinformatica.sati.util;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.sati.entidade.Status;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;

@Path("/ordemservico")
@Controller
public class OrdemServicoRest {

	@Autowired
	private OrdemServicoDao ordemServicoDao;		
	
	private List<OrdemServico> ordensServico;
	
			
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrdemServico> listaOrdemServico(){
		
		return ordemServicoDao.listar();
		//return getOrdens();
	}
		
	private List<OrdemServico> getOrdens() {
		
		List<OrdemServico> listOS = new ArrayList<OrdemServico>();
		OrdemServico Os = new OrdemServico();
		Os.setDataEmissao(new Date(System.currentTimeMillis()));
		Os.setDataFechamento(new Date(System.currentTimeMillis()));
		Os.setDescricaoProblema("teste rest");
		Os.setEstado(Status.ATENDENDO);
		
		listOS.add(Os);		
				
		return listOS;
	}
	
	
	
//	@GET
//	@Path("/{idTecnico}/idTecnico")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<OrdemServico> findByTecnico(@PathParam("idTecnico") int idTecnico) {
//				
//		OrdemServicoControl osControle = new OrdemServicoControl();
//		ordensServico = osControle.listaremAtendimento(null);
//		return ordensServico;
//		
//	}
//		
}
