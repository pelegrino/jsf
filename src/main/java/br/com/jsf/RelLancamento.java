package br.com.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.repository.IDaoLancamento;

@ViewScoped
@Named(value = "rellancamento")
public class RelLancamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date datainicio;
	private Date datafim;
	private String empresaDestino;

	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Inject
	private IDaoLancamento daoLancamento;
	
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	
	
	
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

	public String getEmpresaDestino() {
		return empresaDestino;
	}

	public void setEmpresaDestino(String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}

	public void setDaoLancamento(IDaoLancamento daoLancamento) {
		this.daoLancamento = daoLancamento;
	}
	
	public IDaoLancamento getDaoLancamento() {
		return daoLancamento;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public void buscarLancamento() {
		
		if (datainicio == null && datafim == null && empresaDestino == null) {
			lancamentos = daoGeneric.getListEntity(Lancamento.class);
	
		} else {
			lancamentos = daoLancamento.relatorioLancamento(empresaDestino, datainicio, datafim);
			
		}
		
	}
}
