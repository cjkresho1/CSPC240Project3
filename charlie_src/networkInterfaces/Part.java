package networkInterfaces;

/**
 * Holds the common features of every part. All parts are extended from this class.
 * The name/part combination should be unique in a system.
 * @author Team5
 *
 */
public class Part 
{
	/**
	 * The name of the part.
	 */
	protected String name;
	
	/**
	 * The number identifier of the part.
	 */
	protected int partNum;
	
	public Part(String _name, int _num)
	{
		this.name = _name;
		this.partNum = _num;
	}

	/**
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * @return the partNum
	 */
	public int getPartNum() 
	{
		return partNum;
	}

	/**
	 * @param partNum the partNum to set
	 */
	public void setPartNum(int partNum) 
	{
		this.partNum = partNum;
	}
	
	
}