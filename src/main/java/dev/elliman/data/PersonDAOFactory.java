package dev.elliman.data;

public class PersonDAOFactory {
	public static PersonDAO getPersonDAO() {
		return new PersonHibernatePostgres();
	}
}
