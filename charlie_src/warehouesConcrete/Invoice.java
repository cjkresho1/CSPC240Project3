package warehouesConcrete;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Invoice 
{
	private LinkedList<WarehousePart> sales;
	private LocalDateTime date;
	private String shopOwner;
	
	public Invoice(String _shopOwner)
	{
		sales = new LinkedList<WarehousePart>();
		date =  LocalDateTime.now();
		this.shopOwner = _shopOwner;
	}
	
	/**
	 * Add a sale of a WarehousePart to the invoice. Should represent the state of the
	 * part at the time of sale, as well as the quantity sold.
	 */
	public void add(WarehousePart part)
	{
		sales.add(part);
	}

	/**
	 * @return the sales
	 */
	public LinkedList<WarehousePart> getSales() 
	{
		return sales;
	}
	
	/**
	 * @return the date
	 */
	public LocalDateTime getDate()
	{
		return date;
	}
	
	public void setDate(LocalDateTime date)
	{
		this.date = date;
	}

	public void print() 
	{
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mma");
		double total = 0;
		System.out.println("Sales Invoice for " + " Bicycle Shop, " + dtf1.format(date) + " at " + dtf2.format(date));
		System.out.println("Part Name\t\tPart Number\t\tPrice\tSales Price\tQnty\tTotal Cost");
		for (int i = 0; i < sales.size(); i++)
		{
			WarehousePart cur = sales.get(i);
			double curTotal = cur.getQuantity();
			if (cur.isOnSale())
			{
				curTotal *= cur.getSalePrice();
			}
			else
			{
				curTotal *= cur.getPrice();
			}
			total += curTotal;
			System.out.printf("%-24s%-16d%-16.2f%-16.2f%-8d%.2f%n", cur.getName(), 
					cur.getPartNum(), cur.getPrice(), cur.getSalePrice(), cur.getQuantity(), curTotal);
		}
		System.out.printf("Total\t\t\t\t\t\t\t\t\\tt%.2f%n", total);
	}

	/**
	 * @return the shopOwner
	 */
	public String getShopOwner() {
		return shopOwner;
	}
}
