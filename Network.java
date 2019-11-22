import java.util.LinkedList;
import java.util.Arrays;

import java.lang.IllegalArgumentException;
import java.lang.reflect.Array;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * Simulates a network of a primary warehouse, and a fleet of vans.
 * 
 * @author Group5
 */
public class Network {
	/**
	 * Name of the primary warehouse. No van can have this name.
	 */
	public static final String WAREHOUSE_NAME = "PRIMARY";

	/**
	 * Primary warehouse.
	 */
	private Warehouse warehouse;

	/**
	 * List of vans.
	 */
	private LinkedList<Warehouse> vans;

	/**
	 * Cumulative list of parts in all warehouses.
	 */
	private LinkedList<PartInfo> parts;

	/**
	 * Creates an empty network with no parts and no vans.
	 */
	public Network() {
		warehouse = new Warehouse(WAREHOUSE_NAME);
		vans = new LinkedList<Warehouse>();
		parts = new LinkedList<PartInfo>();
	}

	/**
	 * Creates a network with parts and vans based on the passed file.
	 * 
	 * @param filename file to pull data from
	 * @throws FileNotFoundException if the file doesn't exist
	 */
	public Network(String filename) throws FileNotFoundException {
		
        File theFile = new File(filename);
        if (theFile.exists())
        {
        	Scanner scanner = new Scanner(theFile);
            warehouse = new Warehouse(WAREHOUSE_NAME);
            vans = new LinkedList<Warehouse>();
            parts = new LinkedList<PartInfo>();
            LinkedList<String> master = new LinkedList<String>();
            int counter = 0;
            while (scanner.hasNext()) {
                int i = 0;
                master.add(scanner.nextLine());
                i++;
            }

            for (int i = 0; i < master.size(); i++) {
                while (counter == 0) {
                    if (!master.get(i).equals("")) {
                        String temp = master.get(i);
                        String[] toPlace = temp.split(",");
                        BikePart part = new BikePart(toPlace[0], Integer.parseInt(toPlace[1]),
                                Double.parseDouble(toPlace[2]), Double.parseDouble(toPlace[3]),
                                Boolean.parseBoolean(toPlace[4]), Integer.parseInt(toPlace[5]));
                        warehouse.add(part);
                        parts.add(new PartInfo(part));
                        i++;

                    } else {
                        counter++;
                    }
                }
                
                    if (!master.get(i).equals("")) {
                    	
                        String temp = master.get(i);
                        if (!temp.contains(",")) {
                            addVan(temp);
                        }
                        else {
                            String[] toPlace = temp.split(",");
                            BikePart part = new BikePart(toPlace[0], Integer.parseInt(toPlace[1]),
                                    Double.parseDouble(toPlace[2]), Double.parseDouble(toPlace[3]),
                                    Boolean.parseBoolean(toPlace[4]), Integer.parseInt(toPlace[5]));
                            vans.get(vans.size() - 1).add(part);
                            parts.add(new PartInfo(part));
                            
                        }

                }
            }
            scanner.close();
        }
        
    }

	/**
	 * Add parts based on an inventory file
	 * 
	 * @param filename inventory file to pull data from
	 * @return true if the file is valid, false otherwise
	 */
	public boolean deliver(String filename) {
		Scanner scnr = null;
		try {
			File inFile = new File(filename);
			if (!inFile.exists()) {
				return false;
			}

			scnr = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scnr.hasNext()) {
			String[] partStuff = (scnr.next().split(","));
			String newPartName = partStuff[0];
			int newPartNum = Integer.parseInt(partStuff[1]);
			double newPartBasePrice = Double.parseDouble(partStuff[2]);
			double newPartSalePrice = Double.parseDouble(partStuff[3]);
			boolean newPartOnSale = Boolean.parseBoolean(partStuff[4]);
			int newPartQuantity = Integer.parseInt(partStuff[5]);
			BikePart deliverPart = new BikePart(newPartName, newPartNum, newPartBasePrice, newPartSalePrice,
					newPartOnSale, newPartQuantity);
			warehouse.add(deliverPart);
			parts.add(new PartInfo(deliverPart));
		}
		scnr.close();
		return true;
	}

	/**
	 * Add an individual part to a warehouse.
	 * 
	 * @param part      part to be added
	 * @param warehouseName warehouse to add part to
	 * @return true if the part is added, false otherwise (ex: invalid warehouse)
	 */

	public boolean add(BikePart part, String warehouseName) {

		boolean check = false;

		if (warehouseName.equals(WAREHOUSE_NAME)) {
			warehouse.add(part);
			parts.add(new PartInfo(part));
			check = true;
		} else if (check=false) {
			for (int i = 0; i < vans.size(); i++) {
				if (warehouseName.equals(vans.get(i).getName())) {
					vans.get(i).add(part);
					parts.add(new PartInfo(part));
					check = true;
				}
			}
		}
		else {
			check = false;
		}
		return check;

	}

	/**
	 * Return information about a part
	 * 
	 * @param partName part to return
	 * @return part info, empty string (ie: "") if part cannot be found
	 */
	public String display(String partName) {
		String returnPart = "Part not in Database";
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).getName().equals(partName)) {
				returnPart = (getPartFromDatabase(partName).getName() + "," + Integer.toString(getPartFromDatabase(partName).getNum()) + "," + Double.toString(getPartFromDatabase(partName).getPrice()) + "," + Double.toString(getPartFromDatabase(partName).getSalePrice()) + "," + Boolean.toString(getPartFromDatabase(partName).isOnSale()));
			}
		}
		return returnPart;
	}

	/**
	 * Sell a part from a specific warehouse.
	 * 
	 * @param partNum   part to sell
	 * @param warehouse warehouse to sell part from
	 * @return info about sale, empty string (ie: "") if sale cannot be made
	 */
	public String sell(int partNum, String warehouse) {
		PartInfo tempPart = getPartFromDatabase(partNum);
		Warehouse tempWarehouse = null;
		double price;
		if (warehouse.equals(WAREHOUSE_NAME)) {
			tempWarehouse = this.warehouse;
		} else {
			for (int i = 0; i < vans.size(); i++) {
				if (warehouse.equals(vans.get(i).getName())) {
					tempWarehouse = vans.get(i);
					break;
				}
				if (i == vans.size() - 1) {
					return "";
				}
			}
		}

		if (parts.contains(tempPart)) {
			String quantString = tempWarehouse.sell(partNum);
			if (quantString.equals("")) {
				return "";
			}
			if (tempPart.isOnSale()) {
				price = tempPart.getSalePrice();
			} else {
				price = tempPart.getPrice();
			}
			return (tempPart.getName() + "," + tempPart.getNum() + "," + price);
		} else {
			return "";
		}

	}

	/**
	 * Return a sorted array of the contents of a warehouse, or of all warehouses
	 * 
	 * @param warehouseSort warehouse to sort, empty string (ie: "") indicates all
	 *                  warehouses
	 * @return a sorted array of parts by name
	 */
	public BikePart[] sortName(String warehouseSort) {
		//TODO FIX METHOD
		int i;
		PartQuantity[] sortedPartQuantities;
		Warehouse sortedWarehouse = warehouse;
		BikePart[] sortedBikeParts = null;

		if (warehouseSort.equals("all")) {
			i = 0;
			sortedBikeParts = new BikePart[parts.size()];
		} else {
			i = 1;
			for (int j = 0; j < vans.size(); j++) {
				if (warehouseSort.equals(vans.get(j).getName())) {
					sortedWarehouse = vans.get(j);
					break;
				}
			}
		}

		if (i == 1) {
			sortedPartQuantities = sortedWarehouse.sortName();
			sortedBikeParts = new BikePart[sortedPartQuantities.length];
			for (int j = 0; j < sortedPartQuantities.length; j++) {
				PartQuantity sortPart = sortedPartQuantities[j];
				BikePart sortedBikePart = new BikePart(getPartFromDatabase(sortPart.getName()), sortPart);
				sortedBikeParts[j] = sortedBikePart;
			}
			return sortedBikeParts;
		} else if (i == 0) {
			LinkedList<PartQuantity> tempSortedPartQuantities = new LinkedList<>();
			for (int j = 0; j < vans.size(); j++) {
				Warehouse currentWH = vans.get(j);
				PartQuantity[] warehouseSortedParts = currentWH.sortName();
				for (int k = 0; k < warehouseSortedParts.length; k++) {
					tempSortedPartQuantities.add(warehouseSortedParts[k]);
				}
			}
			PartQuantity[] warehouseSortedParts = warehouse.sortName();
			for (int j = 0; j < warehouseSortedParts.length; j++) {
				tempSortedPartQuantities.add(warehouseSortedParts[j]);
			}
			for (int j = 0; j < tempSortedPartQuantities.size(); j++) {
				Warehouse tempWarehouse = new Warehouse("tempWarehouse");
				tempWarehouse.add(tempSortedPartQuantities.get(j));
				if (j == tempSortedPartQuantities.size() - 1) {
					sortedPartQuantities = tempWarehouse.sortName();
					sortedBikeParts = new BikePart[sortedPartQuantities.length];
					for (int k = 0; k < sortedPartQuantities.length; k++) {
						PartQuantity sortPart = sortedPartQuantities[k];
						BikePart sortedBikePart = new BikePart(getPartFromDatabase(sortPart.getName()), sortPart);
						sortedBikeParts[k] = sortedBikePart;
					}
					return sortedBikeParts;
				}
			}
		}
		return sortedBikeParts;
	}

	/**
	 * Return a sorted array of the contents of a warehouse, or of all warehouses
	 * 
	 * @param warehouseSort warehouse to sort, empty string (ie: "") indicates all
	 *                  warehouses
	 * @return a sorted array of parts by number
	 */
	public BikePart[] sortNum(String warehouseSort) {
		//TODO FIX METHOD
		int i;
		PartQuantity[] sortedPartQuantities;
		Warehouse sortedWarehouse = null;
		BikePart[] sortedBikeParts = null;

		if (warehouseSort.equals("all")) {
			i = 0;
			sortedBikeParts = new BikePart[parts.size()];
		} else {
			i = 1;
			for (int j = 0; j < vans.size(); j++) {
				if (warehouseSort.equals(vans.get(j).getName())) {
					sortedWarehouse = vans.get(j);
					break;
				}
			}
		}

		if (i == 1) {
			sortedPartQuantities = sortedWarehouse.sortNumber();
			sortedBikeParts = new BikePart[sortedPartQuantities.length];
			for (int j = 0; j < sortedPartQuantities.length; j++) {
				PartQuantity sortPart = sortedPartQuantities[j];
				BikePart sortedBikePart = new BikePart(getPartFromDatabase(sortPart.getName()), sortPart);
				sortedBikeParts[j] = sortedBikePart;
			}
			return sortedBikeParts;
		} else if (i == 0) {
			LinkedList<PartQuantity> tempSortedPartQuantities = new LinkedList<>();
			for (int j = 0; j < vans.size(); j++) {
				Warehouse currentWH = vans.get(j);
				PartQuantity[] warehouseSortedParts = currentWH.sortNumber();
				for (int k = 0; k < warehouseSortedParts.length; k++) {
					tempSortedPartQuantities.add(warehouseSortedParts[k]);
				}
			}
			PartQuantity[] warehouseSortedParts = warehouse.sortNumber();
			for (int j = 0; j < warehouseSortedParts.length; j++) {
				tempSortedPartQuantities.add(warehouseSortedParts[j]);
			}
			for (int j = 0; j < tempSortedPartQuantities.size(); j++) {
				Warehouse tempWarehouse = new Warehouse("tempWarehouse");
				tempWarehouse.add(tempSortedPartQuantities.get(j));
				if (j == tempSortedPartQuantities.size() - 1) {
					sortedPartQuantities = tempWarehouse.sortNumber();
					sortedBikeParts = new BikePart[sortedPartQuantities.length];
					for (int k = 0; k < sortedPartQuantities.length; k++) {
						PartQuantity sortPart = sortedPartQuantities[k];
						BikePart sortedBikePart = new BikePart(getPartFromDatabase(sortPart.getName()), sortPart);
						sortedBikeParts[k] = sortedBikePart;
					}
					return sortedBikeParts;
				}
			}
		}
		return sortedBikeParts;
	}

	/**
	 * Add a new van to the fleet. Must be a unique, non-empty name
	 * 
	 * @param vanName name of van. Must be unique and non-empty (ie: not "")
	 * @return true if the van is added, false if there is a name conflict
	 */
	public boolean addVan(String vanName) {
		if (vans.contains(new Warehouse(vanName))) {
			return false;
		} else {
			vans.add(new Warehouse(vanName));
			return true;
		}
	}

	/**
	 * Transfer parts between vans. Attempt to move as many as quantity allows.
	 * Ignore parts that do not exist in a van.
	 * 
	 * @param filename name of transfer file
	 */
	public void transfer(String filename) {
		Scanner scnr = null;
		try {
			File file = new File(filename);
			scnr = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String tempString = scnr.next();
		String[] lineSplit = tempString.split(",");

		String sourceWarehouse = lineSplit[0];
		String deliverWarehouse = lineSplit[1];

		Warehouse sourceWH = null;
		Warehouse deliverWH = null;

		if (sourceWarehouse.equals(WAREHOUSE_NAME)) {
			sourceWH = warehouse;
		} else {
			for (int i = 0; i < vans.size(); i++) {
				if (vans.get(i).getName().equals(sourceWarehouse)) {
					sourceWH = vans.get(i);
					break;
				} else if (i == vans.size() - 1) {
					System.out.println("Could not find specified warehouse.");
					return;
				}
			}
		}
		for (int i = 0; i < vans.size(); i++) {
			if (vans.get(i).getName().equals(deliverWarehouse)) {
				deliverWH = vans.get(i);
				break;
			}
		}

		while (scnr.hasNext()) {
			tempString = scnr.next();
			lineSplit = tempString.split(",");
			String name = lineSplit[0];
			int quantity = Integer.parseInt(lineSplit[1]);

			PartInfo newPartInfo = getPartFromDatabase(name);
			BikePart newBikePart = new BikePart(newPartInfo.getName(), newPartInfo.getNum(), newPartInfo.getPrice(),
					newPartInfo.getSalePrice(), newPartInfo.isOnSale(), quantity);

			int partsMoved = sourceWH.remove(newBikePart);
			if(partsMoved != 0)
				deliverWH.add(newBikePart.getName(), newBikePart.getNum(), partsMoved);
		}
		scnr.close();
	}

	public boolean quit(String filename) {
		File file = new File(filename);
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < warehouse.parts.size(); i++) {
				try {
					PartQuantity tempQuant = warehouse.parts.get(i);
					BikePart tempPart = null;
					for (int j = 0; j < parts.size(); j++) {
						if (tempQuant.getName().equals(parts.get(j).getName())) {
							tempPart = new BikePart(parts.get(j), tempQuant);
						}
					}
					bufferedWriter.write(tempPart.toString());
					bufferedWriter.newLine();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < vans.size(); i++) {
				bufferedWriter.newLine();
				bufferedWriter.write(vans.get(i).getName());
				bufferedWriter.newLine();
				for (int j = 0; j < vans.get(i).parts.size(); j++) {
					PartQuantity tempQuant = vans.get(i).parts.get(j);
					BikePart tempPart = null;
					for (int k = 0; k < parts.size(); k++) {
						if (tempQuant.getName().equals(parts.get(k).getName())) {
							tempPart = new BikePart(parts.get(k), tempQuant);
						}
					}
					bufferedWriter.write(tempPart.toString());
					bufferedWriter.newLine();
				}
			}
		bufferedWriter.flush();
		bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*********************************
	 * HELPER METHODS HERE USE ME PLZ
	 ***************/

	/**
	 * Add new part to the inventory list, or update an existing part
	 * 
	 * @param part complete part info of part to be added/updated
	 * @throws IllegalArgumentException if the passed part has a matching part
	 *                                  number as a part in the list, but a
	 *                                  different name.
	 */
	private void addPartToDatabase(PartInfo part) throws IllegalArgumentException {
		boolean found = false;
		for (PartInfo val : parts) {
			if (val.getNum() == part.getNum()) {
				if (!val.getName().equals(part.getName())) {
					throw new IllegalArgumentException("Part data mismatch.");
				} else {
					val.setOnSale(part.isOnSale());
					val.setPrice(part.getPrice());
					val.setSalePrice(part.getSalePrice());
				}
				found = true;
				break;
			}
		}

		if (!found) {
			parts.add(part);
		}
	}

	/**
	 * Return information about a part by part number
	 * 
	 * @param partNum number of part to be found
	 * @return PartInfo on the part. val.equals(new PartInfo()) if partNum is not
	 *         found
	 */
	private PartInfo getPartFromDatabase(int partNum) {
		for (PartInfo val : parts) {
			if (val.getNum() == partNum) {
				return val;
			}
		}

		return new PartInfo();
	}

	/**
	 * Return information about a part by part name
	 * 
	 * @param partName name of part to be found
	 * @return PartInfo on the part. val.equals(new PartInfo()) iff partNum is not
	 *         found
	 */
	private PartInfo getPartFromDatabase(String partName) {
		for (PartInfo val : parts) {
			if (val.getName().equals(partName)) {
				return val;
			}
		}

		return new PartInfo();
	}
}
