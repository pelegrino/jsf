package br.com.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentosImpl;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private Lancamento lancamento = new Lancamento();
	DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	private IDaoLancamento daoLancamento = new IDaoLancamentosImpl();
	
	
	public String salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		lancamento.setUsuario(pessoaUser);
		lancamento = daoGeneric.merge(lancamento);
		
		carregarLancamentos();
		
		return "";
	}
	
	@PostConstruct
	private void carregarLancamentos() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamentos = daoLancamento.consultar(pessoaUser.getId());
	}

	public String novo() {
		lancamento = new Lancamento();
		return "";
		
	}
	
	public String remove() {
		daoGeneric.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		return "";
	}
	
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
}
