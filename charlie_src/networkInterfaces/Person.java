package networkInterfaces;

/**
 * Holds information that represents a person.
 * @author Teat5
 *
 */
public class Person 
{
	
	/**
	 * First name of the person.
	 */
	private String first;
	
	/**
	 * Last name of the person.
	 */
	private String last;
	
	/**
	 * Email of the person.
	 */
	private String email;

	/**
	 * Create a new Person.
	 * @param first first name of the person
	 * @param last last name of the person
	 * @param email email of the person
	 */
	public Person(String _first, String _last, String _email) 
	{
		this.first = _first;
		this.last = _last;
		this.email = _email;
	}

	/**
	 * @return the first name
	 */
	public String getFirst() 
	{
		return first;
	}

	/**
	 * @param first the first name to set
	 */
	public void setFirst(String first) 
	{
		this.first = first;
	}

	/**
	 * @return the last name
	 */
	public String getLast() 
	{
		return last;
	}

	/**
	 * @param last the last name to set
	 */
	public void setLast(String last)
	{
		this.last = last;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}

}
