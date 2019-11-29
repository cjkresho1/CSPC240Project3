/**
 * @author Group5
 */
public class PartInfo extends Part {
	/**
	 * The price of the part.
	 */
	protected double price;

	/**
	 * The price of the part when on sale.
	 */
	protected double salePrice;

	/**
	 * True if the part is on sale, false otherwise.
	 */
	protected boolean onSale;

	/**
	 * Creates a new PartInfo with trash values.
	 */
	public PartInfo() {
		super();
		price = -1;
		salePrice = -1;
		onSale = false;
	}

	/**
	 * Creates a new PartInfo.
	 * 
	 * @param name      part name
	 * @param num       part number
	 * @param price     part price
	 * @param salePrice part sale price
	 * @param onSale    true if the part is on sale, false otherwise
	 */
	public PartInfo(String name, int num, double price, double salePrice, boolean onSale) {
		super(name, num);
		this.price = price;
		this.salePrice = salePrice;
		this.onSale = onSale;
	}

	/**
	 * Creates a new PartInfo from a BikePart
	 * 
	 * @param part existing BikePart
	 */
	public PartInfo(BikePart part) {
		super(part.getName(), part.getNum());
		this.price = part.getPrice();
		this.salePrice = part.getSalePrice();
		this.onSale = part.isOnSale();
	}

	/**
	 * @param o object to be compared
	 * @return true if the objects have the same values, false otherwise
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		if (o == this) {
			return true;
		}

		PartInfo other = (PartInfo) o;

		return (this.name.equals(other.name) && this.num == other.num && this.price == other.price
				&& this.salePrice == other.salePrice && this.onSale == other.onSale);
	}

	/**
	 * @return the price, or the sale price if onSale is true
	 */
	public double getCurPrice() {
		// Ternary operator. Returns the proper price
		return (onSale) ? salePrice : price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the list price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param salePrice the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the sale price
	 */
	public double getSalePrice() {
		return salePrice;
	}

	/**
	 * @return the onSale
	 */
	public boolean isOnSale() {
		return onSale;
	}

	/**
	 * @param onSale the onSale to set
	 */
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

}
