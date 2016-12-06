package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ObservacaoOrdemServico implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String observacao;
	private Date data;
	
	@ManyToOne
	private OrdemServico ordemServico;
	
	public ObservacaoOrdemServico(){
		
	}
	
	public ObservacaoOrdemServico(final String observacao,final Date data) {
		this.observacao = observacao;
		this.data = data;
	}



	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(final String observacao) {
		this.observacao = observacao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
