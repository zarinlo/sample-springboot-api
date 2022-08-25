package sample.api.stocks.services;

import sample.api.stocks.exceptions.StockResponseException;
import sample.api.stocks.models.Stock;
import sample.api.stocks.models.StockGeneralResponse;

public interface StockService {

    void populateStockDatabase() throws StockResponseException;
    StockGeneralResponse getAllStocks();
    StockGeneralResponse getStockBySymbol(String symbol);
    StockGeneralResponse createStock(Stock stock);
    StockGeneralResponse deleteStock(String symbol);
    StockGeneralResponse updateStock(String symbol, Double lastPrice) throws StockResponseException;
}
