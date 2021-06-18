package sample.api.stocks.exceptions;

public class StocksResponseException extends Exception {

    public StocksResponseException(String message) {
        super(message);
    }
}