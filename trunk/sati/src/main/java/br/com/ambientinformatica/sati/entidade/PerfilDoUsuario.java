package br.com.ambientinformatica.sati.entidade;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class PerfilDoUsuario implements Comparable<PerfilDoUsuario> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_usuario_seq")
	@SequenceGenerator(name = "perfil_usuario_seq", sequenceName = "perfil_usuario_seq", allocationSize = 1, initialValue = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Perfil perfil;

	public Long getId() {
		return id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil papel) {
		this.perfil = papel;
	}

	@Override
	public int compareTo(PerfilDoUsuario perfilDoUsuario) {
		try {
			return perfil.toString().compareTo(perfilDoUsuario.perfil.toString());
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
