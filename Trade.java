package TradingPlatform.Main;

import java.util.Comparator;

public class Trade {

    private String product;
    private double price; //should be < 0
    private int quantity, tradeID; //should be < 0, 2 tradeIDs cannot be equal
    private tradeDirection direction;
    private TradeState state;

    public static int counter;

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (price <= 0) {
        throw new InvalidTradeException("Price must be greater than zero.");
    }
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
        throw new InvalidTradeException("Quantity must be greater than zero.");
    }
        this.quantity = quantity;
    }
    public int getTradeID() {
        return tradeID;
    }
    public void setTradeID(int tradeID) {
        this.tradeID = tradeID;
    }
    public tradeDirection getDirection() {
        return direction;
    }
    public void setDirection(tradeDirection direction) {
        this.direction = direction;
    } 

    public TradeState getState() {
        return state;
    }
    void setInternalState(TradeState newState) {
    this.state = newState;
}
    
    public Trade(String product, double price, int quantity, tradeDirection direction) {
        if (product == null || product.isBlank()) {
            throw new InvalidTradeException("Product name is required.");
        }

        if (price <= 0) {
            throw new InvalidTradeException("Price must be greater than zero.");
        }

        if (quantity <= 0) {
            throw new InvalidTradeException("Quantity must be greater than zero.");
        }

        if (direction == null) {
            throw new InvalidTradeException("Trade direction is required.");
        }
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.direction = direction;

        this.state = TradeState.CREATED;
        this.tradeID = ++counter; //may cause an error, counter uninitialized
    }

    public Trade(){
        this.state = TradeState.CREATED;
    }

    Comparator<Trade> compare =
    Comparator.comparingInt(Trade::getTradeID);

    public String toString(){
        return "Product type: " + this.product + ", direction: " + this.direction + ", price: " + this.price + ", quantity: " + this.quantity + ".\n" + "Trade ID: " + this.tradeID + " Current State: " + this.state;
    }
    
    
    
}