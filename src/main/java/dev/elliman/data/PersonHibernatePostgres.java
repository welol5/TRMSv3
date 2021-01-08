package dev.elliman.data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dev.elliman.beans.Person;
import dev.elliman.utils.HibernateUtil;

public class PersonHibernatePostgres implements PersonDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();

	@Override
	public Person getPersonByUsername(String username) {
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
		Root<Person> root = criteria.from(Person.class);
		
		Predicate predicateForUsername = cb.equal(root.get("username"), username);
		
		criteria.select(root).where(predicateForUsername);
		
		Person p;
		try {
			p = s.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			p = null;
		}
		s.close();
		return p;
	}

	@Override
	public Boolean update(Person person) {
		Session s = hu.getSession();
		Transaction tx = null;
		Boolean success = true;
		try {
			tx = s.beginTransaction();
			s.update(person);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(tx != null) {
				tx.rollback();
			}
			success = false;
		} finally {
			s.close();
		}
		return success;
	}

}
