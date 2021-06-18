package sample.api.stocks.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sample.api.stocks.controllers.StockController;

@RestControllerAdvice(assignableTypes = StockController.class)
public class StocksResponseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksResponseExceptionHandler.class);

}
