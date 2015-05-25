package br.com.ambientinformatica.sati.persistencia;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Usuario;
import br.com.ambientinformatica.sati.util.SatiException;

public interface UsuarioDao extends Persistencia<Usuario> {

	public Usuario buscarUsuariobyTecnico(String usuario) throws SatiException;
	
	public int verficaPrimeiroAcesso(String login) throws SatiException;
	
	public boolean verificaLoginExistente(String login) throws SatiException;
	
	public void excluirUsuario(Usuario usuario) throws SatiException;
}
