package warehouesConcrete;

import java.util.LinkedList;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

/**
 * The LoginAccount for a SalesAssociate
 * @author Team5
 *
 */
public class SalesAssociate extends LoginAccount 
{
	/**
	 * The inventory for the Sale Associate's van
	 */
	private VanWarehouse van;
	
	/**
	 * A list of all sale invoices for the associate.
	 */
	private LinkedList<Invoice> invoices;
	                           
	/**
	 * Create a new SalesAssociate
	 * @param first First name
	 * @param last Last name
	 * @param email Email
	 * @param user Username
	 * @param pass Password
	 */
	public SalesAssociate(String first, String last, String email, String user, String pass) 
	{
		super(first, last, email, user, pass);
		type = LoginType.SALES_ASSOCIATE;
		
		van = new VanWarehouse();
		invoices = new LinkedList<Invoice>();
	}
	
	public SalesAssociate(String first, String last, String email, String user, byte[] pass, byte[] salt, int iter)
	{
		super (first, last, email, user, pass, salt, iter);
		type = LoginType.SALES_ASSOCIATE;
		
		van = new VanWarehouse();
		invoices = new LinkedList<Invoice>();
	}

	/**
	 * @return the van
	 */
	public VanWarehouse getVan() 
	{
		return van;
	}

	/**
	 * @param van the van to set
	 */
	public void setVan(VanWarehouse van) {
		this.van = van;
	}

	/**
	 * @return the invoices
	 */
	public LinkedList<Invoice> getInvoices() 
	{
		return invoices;
	}
	
	public void addInvoice(Invoice voice)
	{
		invoices.add(voice);
	}
}
