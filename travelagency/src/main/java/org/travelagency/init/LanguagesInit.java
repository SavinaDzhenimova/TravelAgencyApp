package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class LanguagesInit implements CommandLineRunner {

    

    @Override
    public void run(String... args) throws Exception {

    }
}
