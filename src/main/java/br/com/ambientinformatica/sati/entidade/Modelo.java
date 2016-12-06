package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Modelo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "modelo_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "modelo_seq", sequenceName = "modelo_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	//private String nomeEquipamento;
	
	@OneToOne
	private Marca marcaEquipamento;
	
	private String modeloEquipamento;
	
	public Modelo() {
		super();
	}

	public Modelo(Integer id, Marca marcaEquipamento, String modeloEquipamento) {
		super();
		this.id = id;
		this.marcaEquipamento = marcaEquipamento;
		this.modeloEquipamento = modeloEquipamento;
	}
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Marca getMarcaEquipamento() {
		return marcaEquipamento;
	}
	
	public void setMarcaEquipamento(Marca marcaEquipamento) {
		this.marcaEquipamento = marcaEquipamento;
	}
	
	public String getModeloEquipamento() {
		return modeloEquipamento;
	}
	
	public void setModeloEquipamento(String modeloEquipamento) {
		this.modeloEquipamento = modeloEquipamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((marcaEquipamento == null) ? 0 : marcaEquipamento.hashCode());
		result = prime * result + ((modeloEquipamento == null) ? 0 : modeloEquipamento.hashCode());
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
		Modelo other = (Modelo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (marcaEquipamento == null) {
			if (other.marcaEquipamento != null)
				return false;
		} else if (!marcaEquipamento.equals(other.marcaEquipamento))
			return false;
		if (modeloEquipamento == null) {
			if (other.modeloEquipamento != null)
				return false;
		} else if (!modeloEquipamento.equals(other.modeloEquipamento))
			return false;
		return true;
	}
	
	
	
}
