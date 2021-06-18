package sample.api.stocks.models;

import java.util.List;

public class StockList {

    List<Stock> stockList;

    public StockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
