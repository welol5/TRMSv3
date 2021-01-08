package dev.elliman.data;

public class ClaimDAOFactory {
	public static ClaimDAO getClaimDAO() {
		return new ClaimHibernatePostgres();
	}
}
