package br.com.ambientinformatica.sati.rest;

public class OrdemDeServicoNaoEncontradaException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public OrdemDeServicoNaoEncontradaException (String mensagem) {
		super(mensagem);
	}

	public OrdemDeServicoNaoEncontradaException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
