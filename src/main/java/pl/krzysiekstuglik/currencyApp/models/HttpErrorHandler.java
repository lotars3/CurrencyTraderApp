package pl.krzysiekstuglik.currencyApp.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class HttpErrorHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        if ((response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR)
                || (response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR)) {
            return true;
        }
        return false;
    }
}