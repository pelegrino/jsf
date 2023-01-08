package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Cidades;
import br.com.jpautil.JPAUtil;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadesConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override  //Retorna objeto inteiro
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));
		
		return cidade;
	}

	@Override  //Retorna apenas o código em String
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		
		if (cidade == null) {
			return null;
			
		}
		
		if (cidade instanceof Cidades) {
			return ((Cidades) cidade).getId().toString();
			
		} else {
			return cidade.toString();
		}
		
	}
	
	
	
}
