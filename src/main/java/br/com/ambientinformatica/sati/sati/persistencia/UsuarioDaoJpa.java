package br.com.ambientinformatica.sati.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.sati.sati.entidade.Usuario;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;

@Repository("usuarioDao")
public class UsuarioDaoJpa extends PersistenciaJpa<Usuario> implements UsuarioDao{

   private static final long serialVersionUID = 1L;

}
