package dev.elliman.data;

public class RFCDAOFactory {
	public static RFCDAO getRFCDAO() {
		return new RFCHibernatePostgres();
	}
}
