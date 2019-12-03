package warehouesConcrete;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

/**
 * The LoginAccount for a WarehouesManager.
 * @author Team5
 *
 */
public class WarehouseManager extends LoginAccount 
{
	/**
	 * Create a new WarehouesManager
	 * @param first First name
	 * @param last Last name
	 * @param email Email
	 * @param user Username
	 * @param pass Password
	 */
	public WarehouseManager(String first, String last, String email, String user, String pass) 
	{
		super(first, last, email, user, pass);
		type = LoginType.WAREHOUSE_MANAGER;
	}
}
