package pl.krzysiekstuglik.currencyApp.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.krzysiekstuglik.currencyApp.models.CurrencyModel;
import pl.krzysiekstuglik.currencyApp.models.HttpErrorHandler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Bean
    public List<String> getActualCurrencies(){
        return Arrays.asList(
                "USD",
                "EUR",
                "CHF",
                "GBP",
                "THB",
                "AUD",
                "HKD",
                "CAD",
                "NZD",
                "SGD",
                "UAH",
                "HUF",
                "JPY",
                "CNY",
                "CZK",
                "DKK",
                "ISK",
                "NOK",
                "SEK",
                "HRD",
                "RON",
                "BGN",
                "TRY",
                "CLP",
                "PHP",
                "MXN",
                "ZAR",
                "BRL",
                "MYR",
                "RUB",
                "INR",
                "ILS",
                "KRW",
                "XDR"
        );
    }

    public ResponseEntity<CurrencyModel> getCurrencyModelResponseEntity(String currencyCode) {
        ResponseEntity<CurrencyModel> currencyModel;
        currencyModel = getRestTemplate().getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode + "/last/10/?format=json", CurrencyModel.class);
        return currencyModel;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().errorHandler(new HttpErrorHandler()).build();
    }

    public String getCurrencyName(ResponseEntity<CurrencyModel> currencyModel){
        return currencyModel
                .getBody()
                .getCurrencyName();
    }

    public List<LocalDate> getBidDates(ResponseEntity<CurrencyModel> currencyModel){
        return currencyModel
                .getBody()
                .getRates()
                .stream()
                .map(s -> s.getBidData())
                .collect(Collectors.toList());
    }

    public List<Double> getBid(ResponseEntity<CurrencyModel> currencyModel){
        return currencyModel
                .getBody()
                .getRates()
                .stream()
                .map(s -> s.getBid())
                .collect(Collectors.toList());
    }

}
