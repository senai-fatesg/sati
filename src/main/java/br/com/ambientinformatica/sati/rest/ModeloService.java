package br.com.ambientinformatica.sati.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.persistencia.ModeloDao;
import br.com.ambientinformatica.sati.persistencia.ModeloDaoJpa;
import br.com.ambientinformatica.sati.util.SatiException;

@Service
public class ModeloService {

	@Autowired
	public ModeloDao modeloDao;;
	
	public List<Modelo> listar(){
		
		List<Modelo> modelos = null;
		try {
		
			modelos = modeloDao.listarModelo();

		} catch (PersistenciaException e) {
			e.printStackTrace();
		} catch (SatiException e) {
			e.printStackTrace();
		}
		
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

}
