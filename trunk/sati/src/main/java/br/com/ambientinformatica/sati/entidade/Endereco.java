package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Endereco implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "endreeducando_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "endreeducando_seq", sequenceName = "endreeducando_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	
	private String logradouro;
	private String complemento;
	private String setor;
	private String cidade;
	private String uf;
	private String cep;
	private String numero;
	
	//Const
	public Endereco() {

	}
	public Endereco(Integer id, String logradouro, String complemento,
			String setor, String cidade, String uf, String cep, String numero) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.setor = setor;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
		this.numero = numero;
	}

	// GTT E STT
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	// EQUALS E HASCODE
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
//by Silas A.