package br.com.ambientinformatica.sati.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDaoJpa;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;
import br.com.ambientinformatica.sati.persistencia.TecnicoDaoJpa;
import br.com.ambientinformatica.sati.util.SatiException;
@Component
@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoDao ordemDeServicoDao;
	
	@Autowired
	private TecnicoDao tecnicoDao ;
	 
	public List<OrdemServico> listar(String usuario) {
		
		Tecnico tecnico = new Tecnico();
		
		try {
			tecnico = tecnicoDao.buscarTecnicoByUsuario("luiz");
		} catch (SatiException e) {
			throw new UsuarioNaoEncontradoException("O usuário não está cadastrado");
		}
		
		List<OrdemServico> ordensDeServico = ordemDeServicoDao.listar(tecnico.getId());
		
		return ordensDeServico;
	}
	
	public OrdemServico buscarPor(Long id) {
		try {
			return ordemDeServicoDao.consultarPorId(id);
		} catch (SatiException e) {
			throw new OrdemDeServicoNaoEncontradaException("A ordem de serviço não foi encontrada");
		}
	}
	
	public void atualizar(OrdemServico ordemDeServico) {
		ordemDeServicoDao.alterar(ordemDeServico);
	}

}
