package application;


import dao.CoffeeDao;
import entity.Coffee;
import application.Display;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Menu {


	   private Display display = Display.getInstance();
	    private Scanner scanner = new Scanner(System.in);
	    private List<String> options = Arrays.asList(
	            "Display current coffee items",
	            "Display a specific coffee item",
	            "Create a  coffee item",
	            "Update a coffee item",
	            "Delete a coffee item",
	            "Exit"
	    );

	    CoffeeDao coffeeDao = new CoffeeDao();

	    public void start() {

	        String selection = "";

	        do {
	            displayMenu();
	            selection = scanner.nextLine();

	            try {
	                switch (selection) {
	                    case "1": displayAllCoffee();
	                        break;
	                    case "2": displayCoffee();
	                        break;
	                    case "3": createCoffee();
	                        break;
	                    case "4": updateCoffee();
	                        break;
	                    case "5": deleteCoffee();
	                        break;
	                    case "6":
	                        display.displayAlert("Thank you and Good Bye!");
	                        break;
	                    default:
	                        display.displayAlert("Invalid selection. Please try again!!!");
	                        break;
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	                display.displayAlert("There was an error retrieving the selected information.");
	            }

	            display.displayInstructions("Press enter to continue...");
	            scanner.nextLine();

	        } while (!selection.equals("6"));

	    }

	    private void displayMenu() {
	        System.out.print("Main Menu : ");
	        display.displayInstructions("Select an Option below:");

	        for (int i = 0; i < options.size(); i++) {
	            display.displayOption(options.get(i), i + 1);
	        }
	    }

	    private void displayAllCoffee() throws SQLException {

	        List<Coffee> allCoffee = coffeeDao.getAllCoffee();

	      System.out.print("All Coffee Items: ");

	        if (allCoffee.size() == 0) display.displayAlert("There are NO Coffee Items to display...");

	        for (Coffee coffee : allCoffee) {
	            displayCoffeeItem(coffee);
	        }

	       
	    }

	    private void displayCoffee() throws SQLException {
	      System.out.print("Coffee Item: "); 
	        displayCoffeeItem(getCoffeeById());
	       
	    }

	    private void displayCoffeeItem(Coffee coffeeItem) throws SQLException {
	        display.displayResponse("#" + coffeeItem.getId() + " " + coffeeItem.getName() + " $" + coffeeItem.getPrice().doubleValue() + " per " + coffeeItem.getQuantity());
	    }

	    private void createCoffee() throws SQLException {
	        System.out.print("New Coffee Item: ");
	        display.displayInstructions("Enter the new Coffee name: ");
	        String name = scanner.nextLine().trim();

	        display.displayInstructions("Enter the quantity: ");
	        String quantity = scanner.nextLine().trim();

	        if (quantity.equals("") || quantity.equals("1")) quantity = "1 item";

	        display.displayInstructions("Enter the price: ");
	        Double price = scanner.nextDouble();

	        coffeeDao.createNewCoffee(name, quantity, price);

	       
	    }

	    private void updateCoffee() throws SQLException {
	      System.out.print("Update Coffee Item"); 

	        Coffee coffeeItem = getCoffeeById();

	        displayCoffeeItem(coffeeItem);
	        display.displayInstructions("Select the option you would like to update: ");
	        display.displayOption("Name", 1);
	        display.displayOption("Price", 2);
	        display.displayOption("Quantity", 3);
	        display.displayOption("Cancel", 0);

	        int selection = Integer.parseInt(scanner.nextLine());

	        switch (selection) {
	            case 1: setCoffeeName(coffeeItem);
	                break;
	            case 2: setCoffeePrice(coffeeItem);
	                break;
	            case 3: setCoffeeQuantity(coffeeItem);
	                break;
	            default: // ANY OTHER KEY TO CANCEL
	                Display.displayAlert("Cancelling update...\n");
	                break;
	        }

	    }

	    private void setCoffeeName(Coffee coffeeItem) throws SQLException {
	        display.displayInstructions("Enter the new name: ");
	        coffeeItem.setName(scanner.nextLine());

	        coffeeDao.updateCoffee(coffeeItem);
	    }

	    private void setCoffeePrice(Coffee coffeeItem) throws SQLException {
	        display.displayInstructions("Enter the new price: ");
	        coffeeItem.setPrice(Double.parseDouble(scanner.nextLine()));

	        coffeeDao.updateCoffee(coffeeItem);
	    }

	    private void setCoffeeQuantity(Coffee coffeeItem) throws SQLException {
	        display.displayInstructions("Enter the new quantity: ");
	        coffeeItem.setQuantity(scanner.nextLine());

	        coffeeDao.updateCoffee(coffeeItem);
	    }

	    private void deleteCoffee() throws SQLException {
	        System.out.print("Delete Item: ");  

	        Coffee coffeeItem = getCoffeeById();
	        displayCoffeeItem(coffeeItem);
	        display.displayAlert("Are you sure you want to delete this item? Y or N? ");

	        if (scanner.nextLine().toUpperCase().charAt(0) == 'Y') {
	            coffeeDao.deleteCoffee(coffeeItem.getId());
	        } else {
	            Display.displayAlert("Cancel Delete Selection...");
	        }

	    }

	    private Coffee getCoffeeById() throws SQLException {
	        display.displayInstructions("Enter the Coffee id: ");
	        int id = Integer.parseInt(scanner.nextLine());
	        return coffeeDao.getCoffeeById(id);
	    }

	}
