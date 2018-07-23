package sample.api.stock;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sample.api.exceptions.StockNotFoundException;
import sample.api.stock.models.Stock;
import sample.api.stock.models.StocksResponse;
import sample.api.stock.repositories.StockRepository;

@RestController
@RequestMapping("/api/v1")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @ApiOperation(value = "Get all stocks")
    @RequestMapping(value = "/stocks", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public StocksResponse getStocks() {
        return new StocksResponse(stockRepository.findAll());
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Get a stock object by symbol")
    @RequestMapping(value = "/stocks/{symbol}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Stock getStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol) {
        Stock stockObj = stockRepository.findBySymbol(symbol.toUpperCase());
        if (stockObj != null) {
            return stockRepository.findBySymbol(symbol.toUpperCase());
        } else {
            throw new StockNotFoundException(String.format("The Stock with Symbol: %s, was not found in the database", symbol.toUpperCase()));
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Create a new stock")
    @RequestMapping(value = "/stocks", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Stock createStock(
            @ApiParam(value = "New stock object", required = true)
            @RequestBody Stock newStock) {
        newStock.setSymbol(newStock.getSymbol().toUpperCase());
        return stockRepository.save(newStock);
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Update an existing stock by symbol")
    @RequestMapping(value = "/stocks/{symbol}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Stock updateStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol,
            @ApiParam(value = "Updated stock object", required = true)
            @RequestBody Stock updatedStock) {
        Stock foundStock = stockRepository.findBySymbol(symbol.toUpperCase());
        if (foundStock != null) {
            foundStock.setName(updatedStock.getName());
            foundStock.setDescription(updatedStock.getDescription());
            foundStock.setPrice(updatedStock.getPrice());
            foundStock.setLastUpdated(updatedStock.getLastUpdated());
            return stockRepository.save(foundStock);
        } else {
            throw new StockNotFoundException(String.format("The Stock with Symbol: %s, was not found in the database", symbol.toUpperCase()));
        }
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Delete a stock by symbol")
    @RequestMapping(value = "/stocks/{symbol}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol) {
        stockRepository.deleteDistinctBySymbol(symbol.toUpperCase());
    }
}