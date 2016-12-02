package br.com.ambienteinformatica.sati.exceptions;

public class OrdemDeServicoNaoEncontradaException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OrdemDeServicoNaoEncontradaException (String mensagem) {
		super(mensagem);
	}

	public OrdemDeServicoNaoEncontradaException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
