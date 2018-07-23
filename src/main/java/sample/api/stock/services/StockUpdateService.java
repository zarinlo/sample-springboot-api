package sample.api.stock.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sample.api.stock.models.Stock;
import sample.api.stock.repositories.StockRepository;

import java.time.LocalDateTime;
import java.util.Iterator;

@Service
public class StockUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockUpdateService.class);

    @Autowired
    private StockRepository stockRepository;

    private static String API_URL = "https://api.iextrading.com/1.0/stock/market/batch";

    StockUpdateService() {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        Unirest.setHttpClient(httpClient);
    }

    @Scheduled(fixedRate = 60000)
    public void updateStockQuotes() {
        LOGGER.info("Updating Stock Quotes");
        try {
            HttpResponse<JsonNode> stocksResponse = Unirest.get(API_URL)
                    .queryString("symbols", "AAPL, FB, BK, GOOGL, AMZN, SNAP, MSFT, IBM, MS")
                    .queryString("types", "quote")
                    .asJson();
            LOGGER.debug(stocksResponse.getBody().toString());
            JSONObject stocks = stocksResponse.getBody().getObject();
            Iterator keys = stocks.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                JSONObject stock = stocks.getJSONObject(key).getJSONObject("quote");
                saveToDatabase(stock);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        LOGGER.info("Finished Updating Stock Quotes");
    }

    private void saveToDatabase(JSONObject stock) {
        Stock stockDTO = new Stock();
        stockDTO.setLastUpdated(LocalDateTime.now());
        stockDTO.setName(stock.getString("companyName"));
        stockDTO.setSymbol(stock.getString("symbol"));
        stockDTO.setPrice(stock.getDouble("latestPrice"));
        stockDTO.setDescription(stock.getString("primaryExchange"));
        LOGGER.info(String.format("Updating %s", stockDTO.getSymbol()));
        stockRepository.save(stockDTO);
    }
}