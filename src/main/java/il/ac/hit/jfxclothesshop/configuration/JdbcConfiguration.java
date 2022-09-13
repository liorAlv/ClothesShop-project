package il.ac.hit.jfxclothesshop.configuration;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;
//data base
@Configuration
public class JdbcConfiguration {


    @Bean
    @Primary
    public String JdbcConnectionString() {
        return "jdbc:sqlite:src/main/resources/bookies.db";
    }

    @Bean
    public ConnectionSource getConnection(String connectionString) throws SQLException {
        return new JdbcConnectionSource(connectionString);
    }
}
