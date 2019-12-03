package warehouesConcrete;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

public class PersonFactory 
{
	public static LoginAccount createPerson(LoginType type, String first, String last, String email, String user, String pas)
	{
		switch (type)
		{
		case OFFICE_MANAGER:
			return new OfficeManager(first, last, email, user, pas);
		case SALES_ASSOCIATE:
			return new SalesAssociate(first, last, email, user, pas);
		case SYSTEM_ADMIN:
			return new SystemAdmin(first, last, email, user, pas);
		case WAREHOUSE_MANAGER:
			return new WarehouseManager(first, last, email, user, pas);
		default:
			System.out.println("ERROR: THIS SHOULD NEVER HAVE HAPPENED");
			throw(new RuntimeException("Unexpected LoginType at createPerson()"));
		}
	}
	
	
}
