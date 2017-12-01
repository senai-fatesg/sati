package br.com.ambientinformatica.sati.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.persistencia.ModeloDao;
import br.com.ambientinformatica.sati.persistencia.ModeloDaoJpa;

@Service
public class ModeloService {

	@Autowired
	private ModeloDao modeloDao;;
	
	
	
	public List<Modelo> listar(){
		
		List<Modelo> modelos = getModeloDao().listar();
		return modelos;

	}

	
	public List<Modelo> buscarPor(String nome ){
		try {
			return modeloDao.listarPorNome(nome);
			
		} catch (Exception e) {
			e.printStackTrace();
			 return null;
		}
	}
	
	public void atualizar(Modelo modelo){
		modeloDao.alterar(modelo);
	}


	public ModeloDao getModeloDao() {
		if(modeloDao == null){
			modeloDao = new ModeloDaoJpa();
		}
		
		return modeloDao;
	}


	public void setModeloDao(ModeloDao modeloDao) {
		this.modeloDao = modeloDao;
	}

	
	
	
	;;
}
