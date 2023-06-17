package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.zajavka.business.ReloadDataService;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

public class ZajavkaStoreApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ReloadDataService reloadDataService = applicationContext.getBean(ReloadDataService.class);
        reloadDataService.loadRandomData();

    }
}
