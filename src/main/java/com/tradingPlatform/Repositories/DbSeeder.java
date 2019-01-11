package com.tradingPlatform.Repositories;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    private CMCRepository cmcRepository;

    public DbSeeder(CMCRepository cmcRepository) {
        this.cmcRepository = cmcRepository;
    }

    @Override
    public void run(String... args) {
    }
}

