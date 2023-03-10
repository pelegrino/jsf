package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;

@Named
public class IDaoLancamentosImpl implements IDaoLancamento, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLimit10(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery(" from Lancamento where usuario.id = " + codUser + " order by id desc").setMaxResults(10).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultar(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery(" from Lancamento where usuario.id = " + codUser).getResultList();
		
		transaction.commit();
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> relatorioLancamento(String empresaDestino, Date datainicio, Date datafim) {
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select l from Lancamento l ");
		
		
		if (datainicio == null && datafim == null && empresaDestino != null && !empresaDestino.isEmpty()) {
			sql.append(" where upper(l.empresaDestino) like '%").append(empresaDestino.trim().toUpperCase()).append("%' ");
			
		} else if (empresaDestino == null || (empresaDestino !=null && empresaDestino.isEmpty())
					&& datainicio != null && datafim == null) {
			
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			sql.append(" where l.datainicio >= '").append(datainicioString).append("'");
			
		} else if (empresaDestino == null || (empresaDestino !=null && empresaDestino.isEmpty())
				&& datainicio == null && datafim != null) {
			
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.dataFim <= '").append(datafimString).append("'");
		
		} else if (empresaDestino == null || (empresaDestino !=null && empresaDestino.isEmpty())
				&& datainicio != null && datafim != null) {
		
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.datainicio >= '").append(datainicioString).append("' ");
			sql.append(" and l.dataFim <= '").append(datafimString).append("' ");
			
		} else if (empresaDestino != null && empresaDestino.isEmpty()
				&& datainicio != null && datafim != null) {
		
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.datainicio >= '").append(datainicioString).append("' ");
			sql.append(" and l.dataFim <= '").append(datafimString).append("' ");
			sql.append(" and upper(l.empresaDestino) like '%").append(empresaDestino.trim().toUpperCase()).append("%' ");
		
		}	
	
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();	
		
		lancamentos = entityManager.createQuery(sql.toString()).getResultList();
		transaction.commit();
		
		return lancamentos;
	}

}
