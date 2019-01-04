package pl.szymonsmenda.CurrencyTraderApp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.szymonsmenda.CurrencyTraderApp.models.CurrencyModel;
import pl.szymonsmenda.CurrencyTraderApp.models.services.CurrencyService;
import pl.szymonsmenda.CurrencyTraderApp.models.tasks.SendSMSTask;


@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    final CurrencyService currencyService;
    final SendSMSTask sendSMSTask;

    @Autowired
    public MainController(CurrencyService currencyService, SendSMSTask sendSMSTask) {
        this.currencyService = currencyService;
        this.sendSMSTask = sendSMSTask;
    }

    @GetMapping("/{currencyOne}/{currencyTwo}")
    public String index(@PathVariable("currencyOne") String currencyOne,
                        @PathVariable("currencyTwo") String currencyTwo,
                        Model model) {
        ResponseEntity<CurrencyModel> currencyModel;
        try {
            currencyModel = currencyService.getCurrencyModelResponseEntity(currencyOne);
        } catch (IllegalStateException e) {
            getError(currencyOne);
            return "redirect:/";
        }
        model.addAttribute("labels", currencyService.getBidDates(currencyModel));
        model.addAttribute("currencyNameOne", currencyService.getCurrencyName(currencyModel));
        model.addAttribute("dataOne", currencyService.getBid(currencyModel));

        ResponseEntity<CurrencyModel> currencyModel2;
        try {
            currencyModel2 = currencyService.getCurrencyModelResponseEntity(currencyTwo);
        } catch (IllegalStateException e) {
            getError(currencyTwo);
            return "redirect:/";
        }
        model.addAttribute("currencyNameTwo", currencyService.getCurrencyName(currencyModel2));
        model.addAttribute("dataTwo", currencyService.getBid(currencyModel2));
        return "index";
    }

    public void getError(String currencyCode) {
        LOGGER.error("Nie można wyświetlić waluty: " + currencyCode);
    }


    @GetMapping("/{currency}")
    public String currentRate(@PathVariable("currency") String currency,
                              Model model) {
        ResponseEntity<CurrencyModel> currentCurrencyModel;
        try {
            currentCurrencyModel = currencyService.getCurrencyModelResponseEntity(currency);
        } catch (IllegalStateException e) {
            getError(currency);
            return "redirect:/current_rate";
        }
        model.addAttribute("labels", currencyService.getBidDates(currentCurrencyModel));
        model.addAttribute("currentCurrency", currencyService.getCurrencyName(currentCurrencyModel));
        model.addAttribute("dataCurrentCurrency", currencyService.getBid(currentCurrencyModel));
        sendSMSTask.runSmsTask();
        return "current_rate";
    }
}

