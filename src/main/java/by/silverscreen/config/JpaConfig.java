package by.silverscreen.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by sbaranau on 11/25/2016.
 */


@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class JpaConfig extends HikariConfig {

    @Bean
    public DataSource dataSource() throws SQLException {
        return new HikariDataSource(this);
    }

    @Bean
    DSLContext dslContext(@Value("${spring.datasource.jdbcUrl}") final String url)
            throws SQLException {
        return DSL.using(dataSource(), JDBCUtils.dialect(url));
    }
}
