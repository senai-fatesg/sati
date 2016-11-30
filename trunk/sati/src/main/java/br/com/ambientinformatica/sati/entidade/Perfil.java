package br.com.ambientinformatica.sati.entidade;

public enum Perfil {

   ADMIN("Administrador"),
   TECNICO("Técnico"),
   USUARIO("Usuário");
	
	private String descricao;
	
	Perfil(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
   
}
