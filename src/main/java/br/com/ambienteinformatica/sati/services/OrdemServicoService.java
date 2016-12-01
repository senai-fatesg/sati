package br.com.ambienteinformatica.sati.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambienteinformatica.sati.exceptions.UsuarioNaoEncontradoException;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;
import br.com.ambientinformatica.sati.persistencia.TecnicoDaoJpa;
import br.com.ambientinformatica.sati.util.SatiException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoDao ordemDeServicoDao;
	
	@Autowired
	private TecnicoDaoJpa tecnicoDao;
	
	public List<OrdemServico> listar(String usuario) {

		Tecnico tecnico;
		
		try {
			tecnico = tecnicoDao.buscarTecnicoByUsuario(usuario);
		} catch (SatiException e) {
			throw new UsuarioNaoEncontradoException("Esse usuário não está cadastrado");
		}
		
		List<OrdemServico> ordensDeServico = ordemDeServicoDao.listar(tecnico.getId());
		
		return ordensDeServico;
	}
	
}
