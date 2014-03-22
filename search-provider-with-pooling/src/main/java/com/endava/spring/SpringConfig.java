package com.endava.spring;

import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.domain.IMDBEntry;
import com.endava.repository.IMDBDatabase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.setup.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Ionuț Păduraru
 */
@Configuration
public class SpringConfig {

    @Autowired
    private IMDBSearchEngineConfig config;
    @Autowired
    private Environment environment;

    @Bean
    public IMDBDatabase imdbDatabase() throws Exception {
        List<IMDBEntry> data = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream jsonStream = getClass().getResourceAsStream("/imdb.json");
        if (jsonStream != null) {
            try {
                data = objectMapper.readValue(jsonStream, new TypeReference<List<IMDBEntry>>() {});
            } finally {
                jsonStream.close();
            }
        }
        return new IMDBDatabase(data);
    }

}
