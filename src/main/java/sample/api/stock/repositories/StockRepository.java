package sample.api.stock.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sample.api.stock.models.Stock;

@Repository
public interface StockRepository extends MongoRepository<Stock, String> {

    Stock findBySymbol(String symbol);
    void deleteDistinctBySymbol(String symbol);
}