package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Modelo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "modelo_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "modelo_seq", sequenceName = "modelo_seq", allocationSize = 1, initialValue = 1)
	private Integer id;
	//private String nomeEquipamento;
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
	
}
