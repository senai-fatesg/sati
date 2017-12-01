package br.com.ambientinformatica.sati.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambienteinformatica.sati.exceptions.OrdemDeServicoNaoEncontradaException;
import br.com.ambienteinformatica.sati.exceptions.UsuarioNaoEncontradoException;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDaoJpa;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;
import br.com.ambientinformatica.sati.persistencia.TecnicoDaoJpa;
import br.com.ambientinformatica.sati.util.SatiException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoDao ordemDeServicoDao;
	
	@Autowired
	private TecnicoDao tecnicoDao ;
	 
	public List<OrdemServico> listar(String usuario) {
		
		Tecnico tecnico = new Tecnico();
		
		try {
			tecnico = getTecnicoDao().buscarTecnicoByUsuario(usuario);
		} catch (SatiException e) {
			throw new UsuarioNaoEncontradoException("O usuário não está cadastrado");
		}
		
		List<OrdemServico> ordensDeServico = getOrdemDeServicoDao().listar(tecnico.getId());
		
		return ordensDeServico;
	}
	
	public OrdemServico buscarPor(Long id) {
		try {
			return getOrdemDeServicoDao().consultarPorId(id);
		} catch (SatiException e) {
			throw new OrdemDeServicoNaoEncontradaException("A ordem de serviço não foi encontrada");
		}
	}
	
	public void atualizar(OrdemServico ordemDeServico) {
		getOrdemDeServicoDao().alterar(ordemDeServico);
	}

	public OrdemServicoDao getOrdemDeServicoDao() {
		if(ordemDeServicoDao == null){
			ordemDeServicoDao = new OrdemServicoDaoJpa();
		}
		
		return ordemDeServicoDao;
	}

	public TecnicoDao getTecnicoDao() {
		
		if(tecnicoDao == null){
			tecnicoDao = new TecnicoDaoJpa();
		}
		
		return tecnicoDao;
	}
}
