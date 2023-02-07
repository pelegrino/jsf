package br.com.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;

@ViewScoped
@Named(value = "relUsuario")
public class RelUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private IDaoPessoa iDaoPessoa;
	private Date datainicio;
	private Date datafim;
	private String nome;
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	
	
	public void relPessoa() {
		if (datainicio == null && datafim == null && nome == null) {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
	
		} else {
			pessoas = iDaoPessoa.relatorioPessoa(nome, datainicio, datafim);
			
		}
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatafim() {
		return datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
}
