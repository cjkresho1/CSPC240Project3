package warehouesConcrete;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

/**
 * The LoginAccount for an OfficeManager
 * @author Team5
 *
 */
public class OfficeManager extends LoginAccount 
{
	/**
	 * Create a new OfficeManager
	 * @param first First name
	 * @param last Last name
	 * @param email Email
	 * @param user Username
	 * @param pass Password
	 */
	public OfficeManager(String first, String last, String email, String user, String pass) 
	{
		super(first, last, email, user, pass);
		type = LoginType.OFFICE_MANAGER;
	}
	
	public OfficeManager(String first, String last, String email, String user, byte[] pass, byte[] salt, int iter)
	{
		super (first, last, email, user, pass, salt, iter);
	}
}
