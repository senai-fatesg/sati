package br.com.ambientinformatica.sati.entidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.ambientinformatica.jpa.util.AlfaNumerico;
import br.com.ambientinformatica.util.AmbientValidator;
import br.com.ambientinformatica.util.Entidade;
import br.com.ambientinformatica.util.UtilHash;
import br.com.ambientinformatica.util.UtilHash.Algoritimo;

@Entity
public class Usuario extends Entidade {

	@Id
	@Column(nullable = false, unique = true)
	@NotNull(message = "Login do usuário é obrigatório", groups = AmbientValidator.class)
	@NotEmpty(message = "Login do usuário é obrigatório", groups = AmbientValidator.class)
	@AlfaNumerico(message = "O login do usuário não pode conter caracteres especiais, acentos ou espaços", semAcentos = true, semEspacos = true, groups = AmbientValidator.class)
	private String login;

	private String senha;

	private String nome;

	@Temporal(TemporalType.DATE)
	private Date dataAlteracaoSenha = new Date();

	@Temporal(TemporalType.DATE)
	private Date dataCriacao = new Date();

	@Temporal(TemporalType.DATE)
	private Date dataUltimoAcesso = new Date();

	@SuppressWarnings("deprecation")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<PerfilDoUsuario> perfis = new HashSet<PerfilDoUsuario>();

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public Object getId() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenhaNaoCriptografada(String senha) {
		this.senha = UtilHash.gerarStringHash(senha, Algoritimo.MD5);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataAlteracaoSenha() {
		return dataAlteracaoSenha;
	}

	public void setDataAlteracaoSenha(Date dataAlteracaoSenha) {
		this.dataAlteracaoSenha = dataAlteracaoSenha;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public Set<PerfilDoUsuario> getPerfis() {
		return perfis;
	}

	public List<PerfilDoUsuario> obterListaDePerfis() {
		List<PerfilDoUsuario> result = new ArrayList<PerfilDoUsuario>(perfis);
		Collections.sort(result);
		return result;
	}
	
	public void adicionar(Perfil pefil) {
		if (!existe(pefil)) {
			PerfilDoUsuario perfilDoUsuario = new PerfilDoUsuario();
			perfilDoUsuario.setPerfil(pefil);
			perfis.add(perfilDoUsuario);
		}
	}

	public boolean existe(Perfil perfil) {
		if (perfis.contains(perfil)) {
			return true;
		}
		
//		for (PerfilDoUsuario perfilDoUsuario : perfis) {
//			if (perfilDoUsuario.getPerfil() == perfil) {
//				return true;
//			}
//		}
		
		return false;
	}
	
}
