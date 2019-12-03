package warehouesConcrete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import networkInterfaces.LoginAccount;
import networkInterfaces.LoginType;

/**
 * Runs the login and permission system for login accounts, and holds all people and 
 * warehouse information.
 * @author Team5
 *
 */
public class LoginSystem 
{
	private static final String DEFAULT_FILE = "DB_File.txt";
	private Scanner scan;
	private MainWarehouse main;
	private LinkedList<LoginAccount> people;
	
	private LoginAccount curUser;
	
	public LoginSystem()
	{
		scan = new Scanner(System.in);
		main = new MainWarehouse();
		people = new LinkedList<LoginAccount>();
		curUser = null;
		
		people.add(new SystemAdmin("Charlie", "Kresho", "ckemail@email.com", "admin", "madni"));
		
		run(DEFAULT_FILE);
	}
	
	public LoginSystem(String fileName)
	{
		scan = new Scanner(System.in);
		main = new MainWarehouse();
		people = new LinkedList<LoginAccount>();
		curUser = null;
		
		run(fileName);
	}
	
	/******************************PRIVATE METHODS**********************************/
	
	private void run(String fileName)
	{
		readFileOnStart(fileName);
		
		int choice = 0;
		boolean keepRunning = true;
		while (keepRunning)
		{
			System.out.println("Please select from the following options:");
			System.out.println("1: Loggin");
			System.out.println("2: Close system");
			System.out.print("Please enter the number of your choice:");
			
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			switch(choice)
			{
			case 1:
				loginPrompt();
				break;
			
			case 2:
				keepRunning = false;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
		
		writeFileOnClose(fileName);
	}
	
	private void loginPrompt()
	{
		System.out.print("Username: ");
		String username = scan.next();
		
		System.out.print("\nPassword: ");
		String password = scan.next();
		
		if (login(username, password))
		{
			switch(curUser.getType())
			{
			case SYSTEM_ADMIN:
				systemAdminPrompt();
				break;
			case OFFICE_MANAGER:
				officeManagerPrompt();
				break;
			case SALES_ASSOCIATE:
				salesAssociatePrompt();
				break;
			case WAREHOUSE_MANAGER:
				warehouesManagerPrompt();
				break;
			default:
				System.out.println("ERROR: THIS SHOULD NEVER HAVE HAPPENED");
				throw(new RuntimeException("Unexpected LoginType at loginPrompt()"));
				
			}
		}
		else
		{
			System.out.println("A username with this password was not found, please"
					+ " try again.\n");
		}
		
	}
	
	private boolean login(String username, String password)
	{
		for(int i = 0; i < people.size(); i++)
		{
			if(people.get(i).getUsername().equals(username))
			{
				if(people.get(i).login(password))
				{
					curUser = people.get(i);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void systemAdminPrompt()
	{
		int choice = 0;
		boolean loggedIn = true;
		
		while (loggedIn)
		{
			//Print options
			System.out.println("Please select from the following options:");
			System.out.println("1: Add new Office Manager account");
			System.out.println("2: Add new Warehouse Manager account");
			System.out.println("3: Add new Sales Associate account");
			System.out.println("4: Change account password");
			System.out.println("5: Delete account");
			System.out.println("6: Loggout");
			System.out.print("Please enter the number of your choice: ");
			
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			switch(choice)
			{
			case 1:
				createPerson(LoginType.OFFICE_MANAGER);
				break;
			case 2:
				createPerson(LoginType.WAREHOUSE_MANAGER);
				break;
			case 3:
				createPerson(LoginType.SALES_ASSOCIATE);
				break;
			case 4:
				changePassword();
				break;
			case 5:
				deleteAccount();
				break;
			case 6:
				loggedIn = false;
				curUser = null;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
	}
	
	private void createPerson(LoginType type)
	{
		String first = null;
		String last = null;
		String email = null;
		String user = null; 
		String password = null;
		
		while (first == null)
		{
			System.out.print("First name: ");
			try
			{
				first = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				first = null;
			}
		}
		
		while (last == null)
		{
			System.out.print("Last name: ");
			try
			{
				last = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				last = null;
			}
		}
		
		while (email == null)
		{
			System.out.print("Email: ");
			try
			{
				email = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				email = null;
			}
		}
		
		while (user == null)
		{
			System.out.print("Username: ");
			try
			{
				user = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				user = null;
			}
		}
		
		while (password == null)
		{
			System.out.print("Password: ");
			try
			{
				password = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				password = null;
			}
		}
		
		people.add(PersonFactory.createPerson(type, first, last, email, user, password));
	}
	
	private boolean changePassword()
	{
		LoginAccount accountToChange = null;
		String username = null;
		while (username == null)
		{
			System.out.print("Username: ");
			try
			{
				username = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				username = null;
			}
		}
		for (int i = 0; i < people.size(); i++)
		{
			if(people.get(i).getUsername().equals(username)) 
			{
				accountToChange = people.get(i);
				break;
			}
		}
		
		if (accountToChange == null)
		{
			System.out.println("Username not found.");
			return false;
		}
		else
		{
			String password = null;
			while (password == null)
			{
				System.out.print("New password: ");
				try
				{
					password = scan.next();
				}
				catch(Exception e)
				{
					System.out.println("Invalid input, please try again.");
					password = null;
				}
			}
			
			accountToChange.setPassword(password);
			return true;
		}
	}
	
	private boolean deleteAccount()
	{
		String username = null;
		while (username == null)
		{
			System.out.print("Username: ");
			try
			{
				username = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				username = null;
			}
		}
		for (int i = 0; i < people.size(); i++)
		{
			if(people.get(i).getUsername().equals(username)) 
			{
				people.remove(i);
				return true;
			}
		}
		
		System.out.println("Username not found.");
		return false;
	}
	
	private void officeManagerPrompt()
	{
		int choice = 0;
		boolean loggedIn = true;
		
		while (loggedIn)
		{
			//Print options
			System.out.println("Please select from the following options:");
			System.out.println("1: Examine Part");
			System.out.println("2: Order Parts");
			System.out.println("3: Generate Sales Commission");
			System.out.println("4: Loggout");
			System.out.print("Please enter the number of your choice: ");
			
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			//Switch on choice
			switch(choice)
			{
			case 1:
				examinePart();
				break;
			case 2:
				orderParts();
				break;
			case 3:
				salesCommission();
				break;
			case 4:
				loggedIn = false;
				curUser = null;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
	}
	
	private void examinePart() 
	{
		System.out.println("Would you like to find a part by name, or number?");
		System.out.print("Enter 1 for name, or 2 for number: ");
		
		int choice = 0;
		try
		{
			choice = scan.nextInt();
		}
		catch(Exception e)
		{
			choice = 0;
		}
		
		WarehousePart part = null;
		
		switch(choice)
		{
		case 1:
			String name = null;
			while (name == null)
			{
				System.out.print("Please enter a part name: ");
				try
				{
					name = scan.next();
				}
				catch(Exception e)
				{
					System.out.println("Invalid input, please try again.");
					name = null;
				}
			}
			
			part = main.find(name);
			break;
		case 2:
			System.out.print("Please enter a part name: ");
			int num = 0;
			try
			{
				num = scan.nextInt();
			}
			catch(Exception e)
			{
				num = 0;
			}
			
			part = main.find(num);
			break;
		default:
			System.out.println("Invalid input.");
		}
		
		if (part == null)
		{
			System.out.println("Part not found.");
			return;
		}
		
		System.out.println("Part name: " + part.getName());
		System.out.println("Part number: " + part.getPartNum());
		System.out.println("Part price: " + part.getPrice());
		System.out.println("Part sale price: " + part.getSalePrice());
		System.out.print("Part sale status: ");
		if (part.isOnSale())
		{
			System.out.println("True");
		}
		else
		{
			System.out.println("False");
		}
		System.out.println("Quantity:" + part.getQuantity());
	}

	private void orderParts() 
	{
		LinkedList<WarehousePart> partList = main.getInv();
		for (int i = 0; i < partList.size(); i ++) 
		{
			WarehousePart tempPart = partList.get(i);
			if (tempPart.getQuantity() <= 15) {
				System.out.println(tempPart.getName() + " inventory is low. Current quantity: " + tempPart.getQuantity());
			}
		}
		
	}

	private void salesCommission() 
	{
		String username = null;
		while (username == null)
		{
			System.out.print("Sales Associate Name: ");
			try
			{
				username = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				username = null;
			}
		}
		SalesAssociate foundPerson = null;
		
		for (int i = 0; i < people.size(); i++)
		{
			if(people.get(i).getUsername().equals(username)) 
			{
				if (people.get(i).getType() == LoginType.SALES_ASSOCIATE)
				{
					foundPerson = (SalesAssociate)people.get(i);
					break;
				} else {
					System.out.println("Please enter a Sales Associate.");
					break;
				}
			}
		}
		
		if (foundPerson.equals(null)) {
			System.out.println("Please enter a valid Sales Associate.");
			return;
		}
		
		
		
		System.out.println("Username not found.");
		// TODO 
	}

	private void warehouesManagerPrompt()
	{
		int choice = 0;
		boolean loggedIn = true;
		
		while (loggedIn)
		{
			//Print options
			System.out.println("Please select from the following options:");
			System.out.println("1: Update Inventory");
			System.out.println("2: Examine Part");
			System.out.println("3: Loggout");
			System.out.print("Please enter the number of your choice:");
			
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			//Switch on choice
			switch(choice)
			{
			case 1:
				updateInventory();
				break;
			case 2:
				examinePart();
				break;
			case 3:
				loggedIn = false;
				curUser = null;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
	}
	
	private void updateInventory() 
	{
		// TODO Auto-generated method stub
		
	}

	private void salesAssociatePrompt()
	{
		int choice = 0;
		boolean loggedIn = true;
		
		while (loggedIn)
		{
			//Print options
			System.out.println("Please select from the following options:");
			System.out.println("1: Load Sales Van");
			System.out.println("2: Generate Sales Invoice");
			System.out.println("3: Loggout");
			System.out.print("Please enter the number of your choice:");
			
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			//Switch on choice
			switch(choice)
			{
			case 1:
				loadVan();
				break;
			case 2:
				generateInvoice();
				break;
			case 3:
				loggedIn = false;
				curUser = null;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
	}
	
	private void loadVan() 
	{
		System.out.print("Please enter the file name: ");
		
		String fileName = null;
		while (fileName == null)
		{
			System.out.print("File name: ");
			try
			{
				fileName = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				fileName = null;
			}
		}
		
		File transferFile = new File(fileName);
		if (transferFile.exists())
		{
			Scanner transferScan = null;
			try 
			{
				transferScan = new Scanner(transferFile);
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("File could not be found.");
				return;
			}
			
			String sourceName = transferScan.next();
			SalesAssociate source = null;
			for(int i = 0; i < people.size(); i++)
			{
				if(people.get(i).getUsername().equals(sourceName))
				{
					if (people.get(i).getType() == LoginType.SALES_ASSOCIATE)
					{
						source = (SalesAssociate)people.get(i);
					}
				}
			}
			
			if (source == null)
			{
				System.out.println("Source not found.");
				transferScan.close();
				return;
			}
			
			LinkedList<WarehousePart> requestParts = new LinkedList<WarehousePart>();
			while (transferScan.hasNext())
			{
				String nextPart = transferScan.next();
				requestParts.add(new WarehousePart(nextPart));
			}
			
			WarehousePart[] requestPartsArray = (WarehousePart[]) requestParts.toArray();
			WarehousePart[] recievedParts = source.getVan().remove(requestPartsArray);
			
			for (int i = 0; i < recievedParts.length; i++)
			{
				if (requestPartsArray[i].getQuantity() != recievedParts[i].getQuantity())
				{
					System.out.println("Not enough of part " + recievedParts[i].getName() + " for request. "
							+ recievedParts[i].getQuantity() + " parts were recieved "
									+ "out of " + requestPartsArray[i].getQuantity() + "requested.");
				}
			}
			
			((SalesAssociate)curUser).getVan().deliever(recievedParts);
			transferScan.close();
		}
	}

	private void generateInvoice() 
	{
		SalesAssociate tempAssociate = (SalesAssociate) curUser;
		System.out.println("What is the name of the associate you are selling to?");
		String associateName = null;
		while (associateName == null)
		{
			System.out.print("Associate name: ");
			try
			{
				associateName = scan.next();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input, please try again.");
				associateName = null;
			}
		}
		Invoice voice = new Invoice(associateName);
		
		boolean selling = true;
		int choice = 0;
		while (selling)
		{
			System.out.print("Do you have another part to sell? 1 for yes, 2 for no: ");
			try
			{
				choice = scan.nextInt();
			}
			catch(Exception e)
			{
				choice = 0;
			}
			
			switch(choice)
			{
			case 1:
				WarehousePart part = null;
				
				int choicePart = 0;
				System.out.print("Sell by part name or number? 1 for name, 2 for number: ");
				try
				{
					choicePart = scan.nextInt();
				}
				catch(Exception e)
				{
					choicePart = 0;
				}
				switch(choicePart)
				{
				case 1:
					String name = null;
					while (name == null)
					{
						System.out.print("Please enter a part name: ");
						try
						{
							name = scan.next();
						}
						catch(Exception e)
						{
							System.out.println("Invalid input, please try again.");
							name = null;
						}
					}
					
					part = main.find(name);
					break;
				case 2:
					System.out.print("Please enter a part number: ");
					int num = 0;
					try
					{
						num = scan.nextInt();
					}
					catch(Exception e)
					{
						num = 0;
					}
					
					part = main.find(num);
					break;
				default:
					System.out.println("Invalid input.");
				}
				
				if (part == null)
				{
					System.out.println("Part not found.");
				}
				else if(part.getQuantity() > 0)
				{
					System.out.println("There are " + part.getQuantity() + " of this part.");
					System.out.print("How many parts would you like to sell?");
					
					int quant = 0;
					try
					{
						quant = scan.nextInt();
					}
					catch(Exception e)
					{
						quant = 0;
					}
					
					if (quant > 0)
					{
						WarehousePart tempPart = main.remove(part);
						voice.add(tempPart);
						System.out.println("" + tempPart.getQuantity() + " of part "
								+ tempPart.getName() + " were sold.");
					}
				}
				else
				{
					System.out.println("Part not found.");
				}
				break;
			case 2:
				selling = false;
				break;
				
			default:
				System.out.println("Invalid input, please try again.\n");
			}
		}
		
		tempAssociate.addInvoice(voice);
		voice.print();
	}

	private void readFileOnStart(String fileName)
	{
		// TODO
	}
	
	private void writeFileOnClose(String fileName)
	{
		// TODO
	}
}
