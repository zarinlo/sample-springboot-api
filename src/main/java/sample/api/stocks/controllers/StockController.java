package sample.api.stocks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        @ApiResponse(responseCode = "200", description = "Successful: Stock(s) found."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Check input parameter(s) syntax for invalid characters."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not entitled to retrieve information."),
        @ApiResponse(responseCode = "404", description = "Not Found: Stock(s) not found."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Backend service is down.")
})
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    //---------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get all stocks")
    @GetMapping(value = "/stocks")
    public ResponseEntity<StockGeneralResponse> getAllStocks() {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.getAllStocks();
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get a stock by symbol")
    @GetMapping(value = "/stocks/{symbol}")
    //    @Parameters({
    //            @Parameter(name="symbol", allowableValues = "SIEMENS, INDUSTOWER, NIFTY NEXT 50")
    //    })
    public ResponseEntity<StockGeneralResponse> getStockBySymbol(
            @Parameter(description = "A stock symbol", required = true)
            @PathVariable() String symbol) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.getStockBySymbol(symbol);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Create a new stock")
    @PostMapping(value = "/stocks")
    public ResponseEntity<StockGeneralResponse> createStock(
            @Parameter(description = "New stock object", required = true)
            @RequestBody() Stock stock) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.createStock(stock);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Update an existing stock by symbol")
    @PutMapping(value = "/stocks/{symbol}")
    public ResponseEntity<StockGeneralResponse> updateStockBySymbol(
            @Parameter(description = "A stock symbol", required = true)
            @PathVariable() String symbol,
            @Parameter(description = "Last Price", required = true)
            @RequestParam() Double lastPrice) throws StocksResponseException {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.updateStock(symbol, lastPrice);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

    //---------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Delete a stock by symbol")
    @DeleteMapping(value = "/stocks/{symbol}")
    public ResponseEntity<StockGeneralResponse> deleteStockBySymbol(
            @Parameter(description = "A stock symbol", required = true)
            @PathVariable() String symbol) {

        ResponseEntity<StockGeneralResponse> responseEntity;
        StockGeneralResponse serviceResponse = stockService.deleteStock(symbol);
        responseEntity = new ResponseEntity<>(serviceResponse, serviceResponse.getHttpStatus());
        return responseEntity;
    }

}