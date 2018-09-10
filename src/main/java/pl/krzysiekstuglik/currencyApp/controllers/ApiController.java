package pl.krzysiekstuglik.currencyApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.krzysiekstuglik.currencyApp.models.services.CurrencyService;

import java.util.stream.Collectors;

@RestController
public class ApiController {
    final CurrencyService currencyService;

    @Autowired
    public ApiController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/api/currency/{typed}")
    public ResponseEntity getCurrency(@PathVariable("typed") String typed){
        return ResponseEntity
                .ok()
                .body(currencyService.getActualCurrencies()
                        .stream()
                        .filter(s -> s.contains(typed.toUpperCase()))
                        .collect(Collectors.toList()));
    }
}
