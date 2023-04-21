package com.alexxwhite.jdscoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomRunner implements CommandLineRunner {

    @Autowired
    private MainComponent mainComponent;

    @Override
    public void run(String... args) throws Exception {
        mainComponent.run();
    }
}
