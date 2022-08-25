package sample.api.stocks.exceptions;

public class StockResponseException extends Exception {

    public StockResponseException(String message) {
        super(message);
    }
}