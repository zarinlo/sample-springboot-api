package sample.api.stocks.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockGeneralResponse<T> {

    @JsonProperty("response")
    private T response;

    @JsonProperty("status")
    private HttpStatus httpStatus;

    @JsonProperty("userMessages")
    private UnifiedSet<String> userMessages = UnifiedSet.newSet();

    public StockGeneralResponse() {
    }

    public StockGeneralResponse(T response, HttpStatus httpStatus) {
        this.response = response;
        this.httpStatus = httpStatus;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public UnifiedSet<String> getUserMessages() {
        return userMessages;
    }

    public void setUserMessages(UnifiedSet<String> userMessages) {
        this.userMessages = userMessages;
    }
}