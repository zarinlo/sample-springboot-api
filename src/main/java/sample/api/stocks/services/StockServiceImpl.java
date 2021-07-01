package sample.api.stocks.services;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sample.api.stocks.exceptions.StocksResponseException;
import sample.api.stocks.models.*;
import sample.api.stocks.repositories.StockRepository;

import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final RestTemplate stocksApiRestTemplate;

    private HttpHeaders httpHeaders;

    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    public StockServiceImpl(@Qualifier("stocksApiRestTemplate") RestTemplate stocksApiRestTemplate,
                            StockRepository stockRepository) {
        this.stocksApiRestTemplate = stocksApiRestTemplate;
        this.stockRepository = stockRepository;

        httpHeaders = new HttpHeaders();
        httpHeaders.add("x-rapidapi-key", "9e87a2c143msh6b92309e36af212p15ccc6jsn2bc37ea481bd");
    }

    //---------------------------------------------------------------------------------------------------------------

    @Scheduled(fixedRate = 60000)
    public void populateStockDatabase() throws StocksResponseException {
        String path = "/price";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(path)
                .queryParam("Indices", "NIFTY NEXT 50");

        ResponseEntity<FastList<Stock>> response = stocksApiRestTemplate.exchange(builder.build().toString(), HttpMethod.GET, new HttpEntity<>(httpHeaders), new ParameterizedTypeReference<FastList<Stock>>() {
        });

        if (response.getStatusCode() == HttpStatus.OK) {
            Objects.requireNonNull(response.getBody()).forEach(stockRepository::save);
        } else {
            throw new StocksResponseException("Error: Issue retrieving stocks.");
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    public StockGeneralResponse getAllStocks() {
        return new StockGeneralResponse(stockRepository.findAll(), HttpStatus.OK);
    }

    //---------------------------------------------------------------------------------------------------------------

    public StockGeneralResponse getStockBySymbol(String symbol) {
        Stock stock = stockRepository.findBySymbol(symbol.toUpperCase());
        if (stock != null) {
            return new StockGeneralResponse(stock, HttpStatus.OK);
        } else {
            return new StockGeneralResponse(symbol, HttpStatus.NOT_FOUND);
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    public StockGeneralResponse createStock(Stock stock) {
        stockRepository.save(stock);
        return new StockGeneralResponse(stock, HttpStatus.CREATED);
    }

    //---------------------------------------------------------------------------------------------------------------

    public StockGeneralResponse deleteStock(String symbol) {
        stockRepository.deleteDistinctBySymbol(symbol.toUpperCase());
        return new StockGeneralResponse(symbol, HttpStatus.NO_CONTENT);
    }

    //---------------------------------------------------------------------------------------------------------------

    public StockGeneralResponse updateStock(String symbol, Double lastPrice) throws StocksResponseException {
        Stock currentStock = stockRepository.findBySymbol(symbol.toUpperCase());
        if (currentStock != null) {
            currentStock.setLastPrice(lastPrice);
            stockRepository.save(currentStock);
            return new StockGeneralResponse(symbol, HttpStatus.OK);
        } else {
            throw new StocksResponseException("The stock you are trying to update does not exist.");
        }
    }

}