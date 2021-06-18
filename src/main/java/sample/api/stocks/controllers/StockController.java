package sample.api.stocks.controllers;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.api.stocks.exceptions.StocksResponseException;
import sample.api.stocks.models.Stock;
import sample.api.stocks.models.StockGeneralResponse;
import sample.api.stocks.services.StockService;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(
        value="/api/v1",
        consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, ALL_VALUE},
        produces = {APPLICATION_JSON_VALUE})
@ApiResponses({
        @ApiResponse(code = 200, message = "Successful: Stock(s) found."),
        @ApiResponse(code = 400, message = "Bad Request: Check input parameter(s) syntax for invalid characters."),
        @ApiResponse(code = 401, message = "Unauthorized: User is not entitled to retrieve information."),
        @ApiResponse(code = 404, message = "Not Found: Stock(s) not found."),
        @ApiResponse(code = 500, message = "Internal Server Error: Backend service is down.")
})
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Get all stocks")
    @GetMapping(value = "/stocks")
    public ResponseEntity<StockGeneralResponse> getAllStocks() {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.getAllStocks();
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Get a stock by symbol")
    @GetMapping(value = "/stocks/{symbol}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="symbol", allowableValues = "SIEMENS, INDUSTOWER, NIFTY NEXT 50")
    })
    public ResponseEntity<StockGeneralResponse> getStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.getStockBySymbol(symbol);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Create a new stock")
    @PostMapping(value = "/stocks")
    public ResponseEntity<StockGeneralResponse> createStock(
            @ApiParam(value = "New stock object", required = true)
            @RequestBody Stock stock) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.createStock(stock);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Update an existing stock by symbol")
    @PutMapping(value = "/stocks/{symbol}")
    public ResponseEntity<StockGeneralResponse> updateStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol,
            @ApiParam(value = "Last Price", required = true, example = "127.05")
            @RequestParam Double lastPrice) throws StocksResponseException {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.updateStock(symbol, lastPrice);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @ApiOperation(value = "Delete a stock by symbol")
    @DeleteMapping(value = "/stocks/{symbol}")
    public ResponseEntity<StockGeneralResponse> deleteStockBySymbol(
            @ApiParam(value = "A stock symbol", required = true)
            @PathVariable String symbol) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.deleteStock(symbol);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

}