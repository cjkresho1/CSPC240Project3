package networkInterfaces;

import java.util.LinkedList;

import warehouesConcrete.WarehousePart;

/**
 * Defines the structure for how a Warehouse holds and operates on a list of WarehouseParts
 * @author Team5
 *
 */
public class Warehouse 
{
	/**
	 * List of WarehouseParts
	 */
	private LinkedList<WarehousePart> inv;
	
	/**
	 * Creates a new, empty Warehouse
	 */
	public Warehouse()
	{
		inv = new LinkedList<WarehousePart>();
	}
	
	/**
	 * Add a part to the warehouse. If the part already exists (identified by part name
	 * and part number), update the costs and sale status, and add the quantity to the 
	 * existing quantity.
	 * @param part Part to be added.
	 */
	public void add(WarehousePart part)
	{
		boolean found = false;
		for (int i = 0; i < inv.size(); i++)
		{
			if (part.getPartNum() == inv.get(i).getPartNum())
			{
				WarehousePart foundPart = inv.get(i);
				foundPart.setPrice(part.getPrice());
				foundPart.setSalePrice(part.getSalePrice());
				foundPart.setOnSale(part.isOnSale());
				foundPart.setQuantity(foundPart.getQuantity() + part.getQuantity());
				found = true;
				break;
			}
		}
		
		if (!found)
		{
			inv.add(part);
		}
	}
	/**
	 * Add multiple parts to the warehouse. If the part already exists (identified by part name
	 * and part number), update the costs and sale status, and add the quantity to the 
	 * existing quantity.
	 * @param parts Array of parts to be added.
	 */
	public void add(WarehousePart[] parts)
	{
		for (int i = 0; i < parts.length; i++)
		{
			boolean found = false;
			for (int j = 0; j < inv.size(); j++)
			{
				if (parts[i].getPartNum() == inv.get(j).getPartNum())
				{
					WarehousePart foundPart = inv.get(j);
					foundPart.setPrice(parts[i].getPrice());
					foundPart.setSalePrice(parts[i].getSalePrice());
					foundPart.setOnSale(parts[i].isOnSale());
					foundPart.setQuantity(foundPart.getQuantity() + parts[i].getQuantity());
					found = true;
					break;
				}
			}
			
			if (!found)
			{
				inv.add(parts[i]);
			}
		}
	}
	
	/**
	 * Sell a part from the warehouse. If the part exists and has a non-zero quantity,
	 * decrement the quantity, and return a new WarehousePart that contains the same
	 * name, number, prices, and sale status as the sold part.
	 * If the part doesn't exist, or has a zero quantity, return null.
	 * @param partName Name of the part to be sold.
	 * @return A copy of the WarehousePart that is sold (with an updated quantity field)
	 * or null if no part can be sold.
	 */
	public WarehousePart sell(String partName)
	{
		for (int i = 0; i < inv.size(); i++)
		{
			if (partName.equals(inv.get(i).getName()))
			{
				WarehousePart foundPart = inv.get(i);
				if (foundPart.getQuantity() > 0)
				{
					foundPart.setQuantity(foundPart.getQuantity() - 1);
					return new WarehousePart(foundPart);
				}
				else
				{
					break;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Sell a part from the warehouse. If the part exists and has a non-zero quantity,
	 * decrement the quantity, and return a new WarehousePart that contains the same
	 * name, number, prices, and sale status as the sold part.
	 * If the part doesn't exist, or has a zero quantity, return null.
	 * @param partNum Number of the part to be sold
	 * @return A copy of the WarehousePart that is sold, or null if no part can be sold.
	 */
	public WarehousePart sell(int partNum)
	{
		for (int i = 0; i < inv.size(); i++)
		{
			if (partNum == inv.get(i).getPartNum())
			{
				WarehousePart foundPart = inv.get(i);
				if (foundPart.getQuantity() > 0)
				{
					foundPart.setQuantity(foundPart.getQuantity() - 1);
					return new WarehousePart(foundPart);
				}
				else
				{
					break;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Return a copy of the WarehosePart that matches the given name. Return null if 
	 * a matching part cannot be found.
	 * @param partName Name of the part to be found
	 * @return A copy of the part that is found, or null if no part can be found
	 */
	public WarehousePart find(String partName)
	{
		for (int i = 0; i < inv.size(); i++)
		{
			if (partName.equals(inv.get(i).getName()))
			{
				return new WarehousePart(inv.get(i));
			}
		}
		
		return null;
	}
	
	/**
	 * Return a copy of the WarehousePart that matches the given number. Return null 
	 * if a matching part cannot be found.
	 * @param partNum Number of the part to be found
	 * @return A copy of the part that is found, or null if no part can be found
	 */
	public WarehousePart find(int partNum)
	{
		for (int i = 0; i < inv.size(); i++)
		{
			if (partNum == inv.get(i).getPartNum())
			{
				return new WarehousePart(inv.get(i));
			}
		}
		
		return null;
	}
	
	/**
	 * Generate a report of all parts in the warehouse.
	 * @return An array of all WarehouseParts in the warehouse.
	 */
	public WarehousePart[] report()
	{
		WarehousePart[] returnInv = new WarehousePart[inv.size()];
		for (int i = 0; i < inv.size(); i++)
		{
			returnInv[i] = new WarehousePart(inv.get(i));
		}
		
		return returnInv;
	}
	
	/**
	 * Add multiple new parts to the warehouse. This method does NOT update the prices
	 * of any existing parts, only the quantity.
	 * @param parts Array of parts to be delivered
	 */
	public void deliever(WarehousePart[] parts)
	{
		for(int i = 0; i < parts.length; i++)
		{
			boolean found = false;
			for (int j = 0; j < inv.size(); j++)
			{
				if (parts[i].getPartNum() == inv.get(j).getPartNum())
				{
					inv.get(i).setQuantity(parts[i].getQuantity() + inv.get(j).getQuantity());
					found = true;
					break;
				}
			}
			if (!found)
			{
				inv.add(parts[i]);
			}
		}
	}
	
	/**
	 * Attempt to remove a specified amount of multiple parts. Return an identical 
	 * list of parts, where the quantities identify the amount of parts that could be
	 * removed.
	 * @param parts List of parts to be removed
	 * @return List of parts that were removed, where quantity is the number of parts successfully removed.
	 */
	public WarehousePart[] remove(WarehousePart[] parts)
	{
		WarehousePart[] returnInv = new WarehousePart[parts.length];
		for (int i = 0; i < returnInv.length; i++)
		{
			WarehousePart foundPart = new WarehousePart(parts[i]);
			foundPart.setQuantity(0);
			for (int j = 0; j < inv.size(); j++)
			{
				if (parts[i].getPartNum() == inv.get(j).getPartNum())
				{
					foundPart = new WarehousePart(inv.get(j));
					foundPart.setQuantity(Math.min(parts[i].getQuantity(), inv.get(j).getQuantity()));
					inv.get(j).setQuantity(inv.get(j).getQuantity() - foundPart.getQuantity());
					break;
				}
			}
			returnInv[i] = foundPart;
		}
		
		return returnInv;
	}
	
	public WarehousePart remove (WarehousePart part)
	{
		WarehousePart returnPart = new WarehousePart(part);
		
		for (int i = 0; i < inv.size(); i++) {
			if (part.getPartNum() == inv.get(i).getPartNum())
			{
				returnPart.setQuantity(Math.min(part.getQuantity(), inv.get(i).getQuantity()));
				inv.get(i).setQuantity(inv.get(i).getQuantity() - returnPart.getQuantity());
				break;
			}
		}
		
		return returnPart;
	}
	
	public LinkedList<WarehousePart> getInv() 
	{
		return inv;
	}
}
