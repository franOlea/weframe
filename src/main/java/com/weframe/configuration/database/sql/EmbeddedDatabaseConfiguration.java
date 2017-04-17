package com.weframe.configuration.database.sql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@SuppressWarnings("unused")
@Configuration
@Profile("embedded")
public class EmbeddedDatabaseConfiguration {

    @Bean
    @Profile("local")
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("sql/h2.sql")
                .addScript("sql/schema.sql")
                .addScript("sql/inserts.sql")
                .build();
    }

}
