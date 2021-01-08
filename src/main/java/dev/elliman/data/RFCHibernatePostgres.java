package dev.elliman.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dev.elliman.beans.Claim;
import dev.elliman.beans.RFC;
import dev.elliman.beans.Stage;
import dev.elliman.utils.HibernateUtil;

public class RFCHibernatePostgres implements RFCDAO {
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();

	@Override
	public RFC makeRequest(RFC rfc) {
		Session s = hu.getSession();
		
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(rfc);
			updateClaimStage(s, rfc.getClaim(), false);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			s.close();
		}
		
		
		return rfc;
	}

	@Override
	public List<RFC> getRFC(Integer claimID) {
		Session  s = hu.getSession();
		
		String query = "from RFC where claim.id = :id";
		Query<RFC> q = s.createQuery(query, RFC.class);
		q.setParameter("id", claimID);
		
		List<RFC> rfcList = q.getResultList();
		s.close();
		return rfcList;
	}

	@Override
	public Boolean answer(Integer id, String answer) {
		Session s = hu.getSession();
		
		RFC rfc = s.get(RFC.class, id);
		rfc.setAnswer(answer);
		
		Boolean success = false;
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(rfc);
			updateClaimStage(s, rfc.getClaim(), true);
			tx.commit();
			success = true;
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			success = false;
		} finally {
			s.close();
		}
		
		return success;
	}

	public void updateClaimStage(Session s, Claim claim, Boolean answered) throws Exception{
		Stage stage = null;
		if(!answered) {
			stage = s.get(Stage.class, 5);
		} else {
			List<RFC> remainingUnansweredComments = null;
			String query = "from RFC where claim.id = :claimID and answer = null";
			Query<RFC> q = s.createQuery(query, RFC.class);
			q.setParameter("claimID", claim.getId());
			remainingUnansweredComments = q.getResultList();
			if(remainingUnansweredComments.size() > 0) {
				//do nothing
			} else if(claim.getDsa() == null) {
				stage = s.get(Stage.class, 1);
			} else if(claim.getDha() == null) {
				stage = s.get(Stage.class, 2);
			} else if(claim.getBca() == null) {
				stage = s.get(Stage.class, 3);
			}
		}
		
		if(stage != null) {
			claim.setApprovalStage(stage);
			s.update(claim);
		}
	}
}
