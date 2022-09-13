package il.ac.hit.jfxclothesshop;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.shop.sales.Sales;
import il.ac.hit.jfxclothesshop.person.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class JdbcDriverSetup {

    private static ConnectionSource connectionSource;

    @Autowired
    private JdbcDriverSetup(ConnectionSource connectionSource) {
        JdbcDriverSetup.connectionSource = connectionSource;
    }

    public static void initTables() throws IOException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Client.class);
            TableUtils.createTableIfNotExists(connectionSource, Clothing.class);
            TableUtils.createTableIfNotExists(connectionSource, Sales.class);
        } catch (SQLException e) {
            throw new IOException("could not create tables", e);
        }
    }

    public static <T, ID> Dao<T, ID> getDao(Class<T> clazz) {
        return DaoManager.lookupDao(connectionSource, clazz);
    }

}
