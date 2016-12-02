package br.com.ambienteinformatica.handler;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.web.bind.annotation.ControllerAdvice;

import br.com.ambienteinformatica.domain.DetalhesDoErro;
import br.com.ambienteinformatica.sati.exceptions.OrdemDeServicoNaoEncontradaException;
import br.com.ambienteinformatica.sati.exceptions.UsuarioNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandler {
	public Response handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e, HttpServletRequest req) {
		DetalhesDoErro erro = new DetalhesDoErro();
		erro.setStatus(404l);
		erro.setTitulo("UsuarioNaoEncontradoException");
		erro.setMensagemDoDesenvolvedor("");
		erro.setTimestamp(System.currentTimeMillis());
		
		return Response.status(Status.NOT_FOUND).entity(erro).build();
	}
	
	public Response handleOrdemDeServicoNaoEncontradaException(OrdemDeServicoNaoEncontradaException e, HttpServletRequest req) {
		DetalhesDoErro erro = new DetalhesDoErro();
		erro.setStatus(404l);
		erro.setTitulo("OrdemDeServicoNaoEncontradaException");
		erro.setMensagemDoDesenvolvedor("");
		erro.setTimestamp(System.currentTimeMillis());
		
		return Response.status(Status.NOT_FOUND).entity(erro).build();
	}
}
