package sample.api.stocks.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class Stock {
    public double dayHigh;
    public double dayLow;
    public String identifier;
    public double lastPrice;
    public String lastUpdateTime;
    public double open;
    public double previousClose;
    @Id
    public String symbol;
    public double totalTradedValue;
    public long totalTradedVolume;
    public long yearHigh;
    public long yearLow;

    public Stock() {
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTotalTradedValue() {
        return totalTradedValue;
    }

    public void setTotalTradedValue(double totalTradedValue) {
        this.totalTradedValue = totalTradedValue;
    }

    public double getTotalTradedVolume() {
        return totalTradedVolume;
    }

    public void setTotalTradedVolume(long totalTradedVolume) {
        this.totalTradedVolume = totalTradedVolume;
    }

    public long getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(long yearHigh) {
        this.yearHigh = yearHigh;
    }

    public long getYearLow() {
        return yearLow;
    }

    public void setYearLow(long yearLow) {
        this.yearLow = yearLow;
    }
}
