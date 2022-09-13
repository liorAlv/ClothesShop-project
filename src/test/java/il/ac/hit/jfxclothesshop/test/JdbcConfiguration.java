package il.ac.hit.jfxclothesshop.test;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.SQLException;

@Configuration
public class JdbcConfiguration {
    @Bean
    @Primary
    public String JdbcConnectionString() {
        return "jdbc:h2:~/testdb;DB_CLOSE_ON_EXIT=TRUE";
    }

    @Bean
    public ConnectionSource getConnection(String connectionString) throws SQLException {
        return new JdbcConnectionSource(connectionString);
    }
}
