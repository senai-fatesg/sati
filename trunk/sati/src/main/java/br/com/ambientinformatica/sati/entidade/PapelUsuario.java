package br.com.ambientinformatica.sati.entidade;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class PapelUsuario implements Comparable<PapelUsuario> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "papelusuario_seq")
	@SequenceGenerator(name = "papelusuario_seq", sequenceName = "papelusuario_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Papel perfil;

	public Long getId() {
		return id;
	}

	public Papel getPerfil() {
		return perfil;
	}

	public void setPerfil(Papel papel) {
		this.perfil = papel;
	}

	@Override
	public int compareTo(PapelUsuario papel) {
		try {
			return perfil.toString().compareTo(papel.perfil.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(getClass())) {
			return obj.hashCode() == hashCode();
		} else {
			return false;
		}
	}

}
