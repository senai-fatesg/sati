package br.com.ambienteinformatica.domain;

public class DetalhesDoErro {
	private String titulo;
	private Long status;
	private Long timestamp;
	private String mensagemDoDesenvolvedor;
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMensagemDoDesenvolvedor() {
		return mensagemDoDesenvolvedor;
	}
	
	public void setMensagemDoDesenvolvedor(String mensagemDoDesenvolvedor) {
		this.mensagemDoDesenvolvedor = mensagemDoDesenvolvedor;
	}
	
}
