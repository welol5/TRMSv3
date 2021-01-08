package dev.elliman.data;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dev.elliman.beans.Claim;
import dev.elliman.beans.Event;
import dev.elliman.beans.Grading;
import dev.elliman.beans.Person;
import dev.elliman.beans.Stage;
import dev.elliman.utils.HibernateUtil;

public class ClaimHibernatePostgres implements ClaimDAO{
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();

	@Override
	public List<Claim> getClaimsByPerson(Person person) {
		
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Claim> criteria = cb.createQuery(Claim.class);
		Root<Claim> root = criteria.from(Claim.class);
		
		Predicate predicateForPerson = cb.equal(root.get("person"), person);
		
		criteria.select(root).where(predicateForPerson);
		
		List<Claim> claims = null;
		claims = s.createQuery(criteria).getResultList();
		s.close();
		
		return claims;
	}

	@Override
	public Claim makeClaim(Claim claim) {
		Session s = hu.getSession();
		
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(claim);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		return claim;
	}

	@Override
	public List<Claim> getDSUnapprovedClaims() {
		Session s = hu.getSession();
		
		String query = "from Claim where dsa = null and denialReason = null";
		Query<Claim> q = s.createQuery(query);
		
		List<Claim> claims = null;
		claims = q.getResultList();
		s.close();
		return claims;
	}

	@Override
	public List<Claim> getApprovedClaims(Person person) {
		Session s = hu.getSession();
		
		String query = "from Claim where stage.id > 1";
		Query<Claim> q = s.createQuery(query);
		
		
		List<Claim> claims = null;
		claims = q.getResultList();
		s.close();
		return claims;
	}

	@Override
	public Boolean update(Claim claim) {
		Session s = hu.getSession();
		Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			s.update(claim);
			tx.commit();
			return true;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			s.close();
		}
		
		
	}

	@Override
	public Claim getClaimByID(Integer id) {
		Session s = hu.getSession();
		Claim claim = s.get(Claim.class, id);
		s.close();
		return claim;
	}

	@Override
	public List<Claim> getDHUnapprovedClaims() {
		Session s = hu.getSession();
		
		String query = "from Claim where dha = null and denialReason = null";
		Query<Claim> q = s.createQuery(query, Claim.class);
		
		List<Claim> claims = null;
		claims = q.getResultList();
		s.close();
		return claims;
	}

	@Override
	public List<Claim> getBCUnapprovedClaims() {
		Session s = hu.getSession();
		
		String query = "from Claim where ((bca = null and dha is not null) or (approvalStage.id = 6)) and denialReason = null";
		Query<Claim> q = s.createQuery(query, Claim.class);
		
		List<Claim> claims = null;
		claims = q.getResultList();
		s.close();
		return claims;
	}

	@Override
	public List<Event> getEventTypes() {
		Session s = hu.getSession();
		String query = "from Event";
		Query<Event> q = s.createQuery(query, Event.class);
		List<Event> events = q.getResultList();
		return events;
	}

	@Override
	public Stage getStageByID(Integer id) {
		Session s = hu.getSession();
		Stage stage = s.get(Stage.class, id);
		s.close();
		return stage;
	}

	@Override
	public Event getEventByID(Integer id) {
		Session s = hu.getSession();
		Event e = s.get(Event.class, id);
		s.close();
		return e;
	}

	@Override
	public Grading getGrading(Grading grading) {
		Session s = hu.getSession();
		String query = "from Grading where hasPresentation = :hasPresentation and passingPercentage = :passingPercentage and passingLetter = :passingLetter";
		Query<Grading> q = s.createQuery(query);
		q.setParameter("hasPresentation", grading.getHasPresentation());
		q.setParameter("passingPercentage", grading.getPassingPercentage());
		q.setParameter("passingLetter", grading.getPassingLetter());
		Grading g;
		try {
			g = q.getSingleResult();
		} catch (NoResultException e) {
			s.save(grading);
			g = grading;
		}
		
		s.close();
		return g;
	}
}
