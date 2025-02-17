package isc.etax.taxfiling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaxFilingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxFilingApplication.class, args);
    }

}
