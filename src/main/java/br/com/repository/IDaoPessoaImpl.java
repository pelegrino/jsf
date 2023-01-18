package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha= '" + senha + "'").getSingleResult();
		
		} catch (javax.persistence.NoResultException e) {
		}
		
		entityTransaction.commit();
		return pessoa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		List<Estados> estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		
		return selectItems;
	}

	@Override
	public List<Pessoa> relatorioPessoa(String nome, Date datainicio, Date datafim) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select l from Pessoa l ");
		
		
		if (datainicio == null && datafim == null && nome != null && !nome.isEmpty()) {
			sql.append(" where upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%'");
			
		} else if (nome == null || (nome !=null && nome.isEmpty())
					&& datainicio != null && datafim == null) {
			
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			sql.append(" where l.dataNascimento >= '").append(datainicioString).append("'");
			
		} else if (nome == null || (nome !=null && nome.isEmpty())
				&& datainicio == null && datafim != null) {
			
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.dataNascimento <= '").append(datafimString).append("'");
		
		} else if (nome == null || (nome !=null && nome.isEmpty())
				&& datainicio != null && datafim != null) {
		
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.dataNascimento >= '").append(datainicioString).append("' ");
			sql.append(" and l.dataNascimento <= '").append(datafimString).append("' ");
			
		} else if (nome != null && nome.isEmpty()
				&& datainicio != null && datafim != null) {
		
			String datainicioString = new SimpleDateFormat("yyyy-MM-dd").format(datainicio);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(datafim);
			sql.append(" where l.dataNascimento >= '").append(datainicioString).append("' ");
			sql.append(" and l.dataNascimento <= '").append(datafimString).append("' ");
			sql.append(" and upper(l.nome) like '%").append(nome.trim().toUpperCase()).append("%' ");
		
		}
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();	
		
		pessoas = entityManager.createQuery(sql.toString()).getResultList();
		transaction.commit();
		
		return pessoas;
	}

}
