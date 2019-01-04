package pl.szymonsmenda.CurrencyTraderApp.models.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.szymonsmenda.CurrencyTraderApp.models.CurrencyModel;
import pl.szymonsmenda.CurrencyTraderApp.models.services.CurrencyService;
import pl.szymonsmenda.CurrencyTraderApp.models.services.SmsService;

import java.util.List;

@Service
public class SendSMSTask {

    final SmsService smsService;
    final CurrencyService currencyService;

    @Autowired
    public SendSMSTask(SmsService smsService, CurrencyService currencyService) {
        this.smsService = smsService;
        this.currencyService = currencyService;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void runSmsTask() {
        ResponseEntity<CurrencyModel> responseEntity;
        responseEntity = currencyService.getCurrencyModelResponseEntity("EUR");
        List<Double> listRates = currencyService.getBid(responseEntity);
        boolean isGrowing = true;
        for (int i = 0; i < listRates.size() - 1; i++) {
            for (int j = i + 1; j < listRates.size(); j++) {
                if (listRates.get(i) > listRates.get(j)) {
                    isGrowing = false;
                } else {
                    isGrowing = true;
                }
            }
        }
        if (isGrowing) {
           smsService.sendSms("xxxxxxxxx", "Kurs dla twojej waluty rośnie");
        } else {
           smsService.sendSms("xxxxxxxxx", "Kurs dla twojej waluty spada");
        }
    }
}
