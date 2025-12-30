package TradingPlatform.Main;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;

public class TradeUI {
    private final TradeOperation tradeOp = new TradeOperation();
    private Map<Integer, Trade> trades = new TreeMap<>();
    Scanner in = new Scanner(System.in);
    public void UI(){
    

    int num = readInt(in, "Welcome to our Trading Management System! Please enter the number of trades you want to make: ");
    for (int i = 1; i <= num; i++) {

    System.out.println("Logging Trade #" + i);

    tradeDirection direction = readDirection(in);
    System.out.println("Enter product name:");
    String product = in.next();

    double price = readDouble(in, "Enter price:");
    int quantity = readInt(in, "Enter quantity:");

    try {
        Trade trade = new Trade(product, price, quantity, direction);
        trades.put(trade.getTradeID(), trade);
    } catch (InvalidTradeException e) {
        System.out.println("Trade rejected: " + e.getMessage());
        i--; 
        }

    }
}
    public void print(){
        System.out.println("Your current transactions: ");
        for(Trade t : trades.values())
            System.out.println(t);
    }

    public void modify(){
        
        String decision = readDecision(in, "Do you wish to modify or cancel any of those trades? y/n ");

        
        if(decision.contains("y")){
            String answer = "";
            while(!answer.equals("modify") && !answer.equals("cancel")){
                System.out.println("Please select: modify or cancel");
                answer = in.next();
            }



            int exit = 0;
            while(exit != 1){
            System.out.println("Please enter the ID number of the trade you want to " + (answer.equals("cancel") ? "cancel" : "modify") +": ");
            int num = validateTradeNumber(in, trades);

            Trade modified = trades.get(num);
            if(answer.equals("cancel")){
                tradeOp.validateTrade(modified);
                tradeOp.acceptTrade(modified);
                tradeOp.cancelTrade(modified);
                System.out.println("Trade successfully cancelled: " + modified);
                break;
            } else {
            System.out.println("Please enter what you want to modify: direction, type, quantity, price");
            String mod = validateModification(in);

            

                switch(mod){
                    case "direction":
                    modified.setDirection( modified.getDirection().equals(tradeDirection.buy) ? tradeDirection.sell : tradeDirection.buy);
                    break;
                    case "type":
                    System.out.println("Please enter what type of product you want to trade instead: ");
                    String newProd = in.next();
                    modified.setProduct(newProd);
                    break;
                    case "quantity":
                    System.out.println("Please enter what quantity you want to trade instead: ");
                    int newQuantity = validateNegatives(in);
                    modified.setQuantity(newQuantity);
                    
                    break;

                    case "price":
                    System.out.println("Please enter the new price to be set: ");
                    int newPrice = validateNegatives(in);

                    modified.setPrice(newPrice);
                    break;
                }

                tradeOp.validateTrade(modified);
                System.out.println("Modified Trade: " + modified);
                String dec = readDecision(in, "Would you like to modify another trade? y/n");
                exit = (dec.contains("n") ? 1 : 0);
            }
        }

        } else if(decision.contains("n")){
            System.out.println("Thank you for using our Trading Management System! Have a good day.");
        }
    

    }

    private static int readInt(Scanner in, String message) {
    while (true){
        System.out.println(message);
        try {
            return in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer number.");
            in.nextLine();
        }
    }
}

    private static double readDouble(Scanner in, String message) {
    while (true){
        System.out.println(message);
        try {
            return in.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
            in.nextLine();
        }
    }
}

    private static tradeDirection readDirection(Scanner in) {
    while (true){
        System.out.println("Would you like to buy or sell?");
        String input = in.next().toLowerCase();

        try {
            return tradeDirection.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid trade direction: buy or sell.");
        }
    }
}

    private static String readDecision(Scanner in, String message) {
    while (true){
        System.out.println(message);
        String input = in.next().toLowerCase();

        if (input.equals("y") || input.equals("n")) {
            return input;
        }

        System.out.println("Please enter a valid input: y/n");
    }
}

    private static int validateNegatives(Scanner in){
        while(true){
            int input = in.nextInt();

            if(input > 0){
                return input;
            }

            System.out.println("Input cannot be negative. Please enter a valid input: ");
        }
    }

    private static int validateTradeNumber(Scanner in, Map<Integer, Trade> trades){
        while(true){
            int input = in.nextInt();

            if(input > 0 && input <= trades.size()){
                return input;
            }

            System.out.println("Trade does not exist. Please enter a valid trade number: ");
        }
    }

    private static String validateModification(Scanner in){
        while(true){
            String input = in.next().toLowerCase();

            if(input.equals("direction") || input.equals("type")|| input.equals("quantity") || input.equals("price"))
            return input;
        
            System.out.println("Please enter a valid field to modify: direction, type, quantity, price");
    }

}
}
