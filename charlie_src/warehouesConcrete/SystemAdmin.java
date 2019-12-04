package warehouesConcrete;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

/**
 * The LoginAccount for a SystemAdmin
 * @author Charles Kresho
 *
 */
public class SystemAdmin extends LoginAccount 
{
	/**
	 * Create a new SystemAdmin
	 * @param first First name
	 * @param last Last name
	 * @param email Email
	 * @param user Username
	 * @param pass Password
	 */
	public SystemAdmin(String first, String last, String email, String user, String pass) 
	{
		super(first, last, email, user, pass);
		type = LoginType.SYSTEM_ADMIN;
	}
	
	public SystemAdmin(String first, String last, String email, String user, byte[] pass, byte[] salt, int iter)
	{
		super (first, last, email, user, pass, salt, iter);
		type = LoginType.SYSTEM_ADMIN;
	}

}
