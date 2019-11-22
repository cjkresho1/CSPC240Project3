import java.lang.IllegalArgumentException;

/**
 * @author Group5
 */
public class BikePart extends Part {
	private double price;
	private double salePrice;
	private boolean onSale;
	private int quantity;

	/*
	 * Creates a new, empty BikePart. Should never be used unless values are to be
	 * set right after.
	 */
	public BikePart() {
		super();
		price = -1;
		salePrice = -1;
		onSale = false;
		quantity = -1;
	}

	/**
	 * Creates a new custom BikePart
	 * 
	 * @param name        the name of the bike part
	 * @param num         the part number
	 * @param listPrice   the price the part is sold for
	 * @param salePrice   the price to produce the part
	 * @param isBeingSold true if the part is on sale, false otherwise
	 * @param quantity    the number of parts being stored
	 */
	public BikePart(String name, int num, double listPrice, double salePrice, boolean isBeingSold, int quantity) {
		super(name, num);
		this.price = listPrice;
		this.salePrice = salePrice;
		this.onSale = isBeingSold;
		this.quantity = quantity;
	}

	/**
	 * Create a new BikePart from existing PartInfo and PartQuantity.
	 * 
	 * @throws IllegalArgumentException if part and quant don't have matching names
	 *                                  and numbers
	 * @param info  existing PartInfo
	 * @param quant existing PartQuantity
	 */
	public BikePart(PartInfo info, PartQuantity quant) throws IllegalArgumentException {
		super(info.getName(), info.getNum());
		if (!info.getName().equals(quant.getName())) {
			throw new IllegalArgumentException("Mismatching part name");
		}
		if (info.getNum() != quant.getNum()) {
			throw new IllegalArgumentException("Mismatching part number. IF THIS HAPPENS CONTACT CHARLIE.");
		}
		this.price = info.getPrice();
		this.salePrice = info.getSalePrice();
		this.onSale = info.isOnSale();
		this.quantity = quant.getQuantity();
	}

	/**
	 * @return A string representation of the bike part:
	 *         partName,partNumber,listPrice,salePrice,onSale,quantity
	 */
	public String toString() {
		String val = String.format("%s,%d,%.2f,%.2f,", name, num, price, salePrice);

		if (onSale) {
			return val + "true," + quantity;
		}
		return val + "false," + quantity;
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

		BikePart other = (BikePart) o;

		return (this.name.equals(other.name) && this.num == other.num && this.price == other.price
				&& this.salePrice == other.salePrice && this.onSale == other.onSale && this.quantity == other.quantity);
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

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
