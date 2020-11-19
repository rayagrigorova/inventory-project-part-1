package Project_17;

import java.util.InputMismatchException;
import java.util.Scanner;



public class ProductTester {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);	
		
		int maxSize=-1, menuChoice;
		
		maxSize = getNumProducts(in);
		
		if(maxSize ==0) {
			//Display a no products message if zero is entered
			System.out.println("No products required!");
		}
		else {
			try {
				Product[] products = new Product[maxSize];
				addToInventory(products, in, maxSize);
				
			    	do {
			    		menuChoice = getMenuOption(in);
			    		executeMenuChoice(menuChoice, products, in);
			    	}
			    	while(menuChoice != 0);
		    }
			catch(InputMismatchException e) {
				System.out.println("Incorrect data type entered!");
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
		
	
	public static void displayInventory(Product[]products) {
	    for(Product product: products)
	    	System.out.println(product);
	}
	
	public static void addToInventory(Product[] products, Scanner in, int maxSize) {
		int tempNumber;
		String tempName;
		int tempQty;
		double tempPrice;
		
		for(int i = 0; i<maxSize; i++) {
        	in.nextLine();

    		System.out.print("\n\nPlease enter the product name: ");
    		tempName = in.nextLine();
    		System.out.print("Please enter the quantity of stock for this product: ");
    		tempQty = in.nextInt();
    		System.out.print("Please enter the price for this product: ");
    		tempPrice = in.nextDouble();
    		System.out.print("Please enter the item number: ");
    		tempNumber = in.nextInt();
    		
    		
    		products[i] = new Product(tempNumber, tempName, tempQty, tempPrice);
		}
	}
	
	public static int getNumProducts(Scanner in) {
		int temp = -1;
		
		int flag = 0;
		
		do {
		flag = 0;
		
		System.out.println("Enter the number of products you would like to add.");
		System.out.print("Enter 0 (zero) if you do not wish to add products: ");
		
		try{
			temp = in.nextInt();
		}
		catch (InputMismatchException e) {
			System.out.println("Incorrect data type entered!");
			in.nextLine();
			flag = 1;
		}
		catch(Exception e) {
			System.out.println(e);
			in.nextLine();
			flag = 1;
		}
		
		if(temp < 0 && flag != 1)
			System.out.println("Incorrect Value entered");
		}
		while(temp < 0);
		
		return temp;
	}
	

	
	public static int getMenuOption (Scanner in) {
		int temp;
		System.out.println("1. View Inventory\n2. Add Stock\n3. "
				+ "Deduct Stock\n4. Discontinue Stock\n0.Exit\n\nPlease enter a menu option:");
		
		do {
			try {
				temp = in.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Incorrect data type entered!");
				in.nextLine();
				
				temp = -1;
			}
			catch(Exception e){
				System.out.println(e);
				in.nextLine();
				
				temp = -1;
			}
		}
		while (!(temp >= 0 && temp <= 4));
		
		return temp;
	}
	
	public static int getProductNumber(Product [] products, Scanner in) {
		int productChoice = -1;
		
		for(int i = 0; i < products.length; i++) {
			System.out.println("Product name: " + products[i].getName() +
					" Product index: " + i);
		}
		
		System.out.println("Enter the index of the product to update:");
		
		try {
			productChoice = in.nextInt();
		}
		catch(InputMismatchException e) {
			System.out.println("Incorrect data type entered!");
			in.nextLine();
		}
		catch(Exception e) {
			System.out.println(e);
			in.nextLine();
		}
		
		if(productChoice >= 0 && productChoice < products.length) {
			return productChoice;
		}
		
		System.out.println("The index you have entered is invalid");
		return -1;
		
	}
	
	public static void addInventory(Product[] products, Scanner in) {
		int productChoice, updateValue = -1, flag = 0;
		productChoice = getProductNumber(products, in);
		
		if(productChoice == -1) return;
		
		do {
			flag = 0;
			System.out.println("How many products do you want to add?");
			
			try {
				updateValue = in.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Incorrect data type entered!");
				in.nextLine();
				updateValue = -1;
				flag = 1;
				
			}
			catch(Exception e ) {
				System.out.println(e);
				in.nextLine();
				updateValue = -1;
				flag = 1;
			}
			
			if(updateValue <= 0 && flag == 0) {
				System.out.println("The update value must be a positive integer.");
			}

		}	
		while(!(updateValue > 0));
		
		products[productChoice].addToInventory(updateValue);
	}
	
	public static void deductInventory(Product[] products, Scanner in) {
		int productChoice, updateValue = -1, flag = 0;
		productChoice = getProductNumber(products, in);
		
		if(productChoice == -1) return;
		
		do {
			flag = 0;
			System.out.println("How many products do you want to deduct?");
			
			try {
				updateValue = in.nextInt();
			}
			catch(InputMismatchException e) {
				System.out.println("Incorrect data type entered!");
				in.nextLine();
				updateValue = -1;
				flag = 1;
				
			}
			catch(Exception e ) {
				System.out.println(e);
				in.nextLine();
				updateValue = -1;
				flag = 1;
			}
			
			if(updateValue <= 0 && flag == 0) {
				System.out.println("The update value must be a positive integer.");
			}
			else if(products[productChoice].getQtyInStock() - updateValue < 0 && flag == 0) {
				System.out.println("The quantity to deduct is too high");
			}
		}	
		while(!(updateValue > 0 && (products[productChoice].getQtyInStock() - updateValue >= 0)));
		
		products[productChoice].deductFromInventory(updateValue);
	}
	
	public static void discontinueInventory(Product[] products, Scanner in) {
		int productChoice = -1;
		do {
			try {
				productChoice = getProductNumber(products, in);
			}
			catch(InputMismatchException e) {
				System.out.println("Incorrect data type entered!");
				in.nextLine();
			}
			catch (Exception e) {
				System.out.println(e);
				in.nextLine();
			}
		}
		while(productChoice == -1);
		
		
		products[productChoice].setActive(false);
	}
	
	public static void executeMenuChoice(int menuChoice, Product[] products, Scanner in) {
		
		switch(menuChoice) {
		case 0: System.out.println("Exit");
				return;
				
		case 1: System.out.println("View Inventory");
				displayInventory(products);
				break;
				
		case 2: System.out.println("Add Stock");
				addInventory(products, in);
				break;
				
		case 3: System.out.println("Deduct Stock");
				deductInventory(products, in);
				break;
				
		case 4: System.out.println("Discontinue Stock");
				discontinueInventory(products, in);
				break;
		
		default: System.out.println("Invalid option");
		}
	}
}


