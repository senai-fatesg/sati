package br.com.ambientinformatica.sati.entidade;

import java.util.Date;

public class ObservacaoOrdemServico {

	private Integer id;
	private String observacao;
	private Date data;
	
	
	public ObservacaoOrdemServico(){
		
	}
	
	public ObservacaoOrdemServico(String observacao, Date data) {
		this.observacao = observacao;
		this.data = data;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
