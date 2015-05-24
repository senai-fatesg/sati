package br.com.ambientinformatica.sati.sati.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Marca {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "geradorEmail")
	@SequenceGenerator(name="geradorEmail", sequenceName = "gerador_email", allocationSize=1, initialValue=1)
	private Integer id;
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}
	
	
	
	
}
