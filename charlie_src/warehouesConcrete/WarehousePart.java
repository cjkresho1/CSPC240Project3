package warehouesConcrete;

import networkInterfaces.Part;

/**
 * Holds all information regarding a WarehousePart.
 * @author Team5
 *
 */
public class WarehousePart extends Part
{
	/**
	 * Price of the part when not on sale.
	 */
	private double price;

	/**
	 * Price of the part when on sale.
	 */
	private double salePrice;
	
	/**
	 * If the part is on sale or not.
	 */
	private boolean onSale;
	
	/**
	 * Quantity of parts in the warehouse.
	 */
	int quantity;
	
	/**
	 * Creates a new WarehousePart
	 * @param _name Name of the part
	 * @param _num Number of the part
	 * @param _price Price of the part
	 * @param _salePrice Price of the part when on sale
	 * @param _onSale True of the part is on sale, false otherwise
	 * @param _quantity Quantity of parts
	 */
	public WarehousePart(String _name, int _num, double _price, double _salePrice, boolean _onSale, int _quantity) 
	{
		super(_name, _num);
		this.price = _price;
		this.salePrice = _salePrice;
		this.onSale = _onSale;
		this.quantity = _quantity;
	}

	
	public WarehousePart(WarehousePart otherPart) 
	{
		super(otherPart.getName(), otherPart.getPartNum());
		this.price = otherPart.getPrice();
		this.salePrice = otherPart.getSalePrice();
		this.onSale = otherPart.isOnSale();
		this.quantity = otherPart.getQuantity();
	}


	/**
	 * @return the price
	 */
	public double getPrice() 
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) 
	{
		this.price = price;
	}

	/**
	 * @return the salePrice
	 */
	public double getSalePrice() 
	{
		return salePrice;
	}

	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(double salePrice) 
	{
		this.salePrice = salePrice;
	}

	/**
	 * @return the onSale
	 */
	public boolean isOnSale() 
	{
		return onSale;
	}

	/**
	 * @param onSale the onSale to set
	 */
	public void setOnSale(boolean onSale) 
	{
		this.onSale = onSale;
	}
	
	/**
	 * @return the quantity
	 */
	public int getQuantity()
	{
		return quantity;
	}
	
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
}
