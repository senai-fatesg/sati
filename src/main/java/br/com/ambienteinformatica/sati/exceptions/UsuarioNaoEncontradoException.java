package br.com.ambienteinformatica.sati.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
