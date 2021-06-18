package sample.api.stocks.services;

import sample.api.stocks.exceptions.StocksResponseException;
import sample.api.stocks.models.Stock;
import sample.api.stocks.models.StockGeneralResponse;

public interface StockService {

    StockGeneralResponse getAllStocks();
    void populateStockDatabase() throws StocksResponseException;
    StockGeneralResponse getStockBySymbol(String symbol);
    StockGeneralResponse createStock(Stock stock);
    StockGeneralResponse deleteStock(String symbol);
    StockGeneralResponse updateStock(String symbol, Double lastPrice) throws StocksResponseException;
}
