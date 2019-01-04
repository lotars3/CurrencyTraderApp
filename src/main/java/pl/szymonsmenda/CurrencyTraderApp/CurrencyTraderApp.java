package pl.szymonsmenda.CurrencyTraderApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyTraderApp{

	public static void main(String[] args) {
		SpringApplication.run(CurrencyTraderApp.class, args);
	}
}
