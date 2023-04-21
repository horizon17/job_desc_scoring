package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("!test")
public class CustomRunner implements CommandLineRunner {

    @Autowired
    private MainComponent mainComponent;

    @Override
    public void run(String... args) throws Exception {
        mainComponent.run();
    }
}
