package il.ac.hit.jfxclothesshop.person.managing;

import com.j256.ormlite.stmt.Where;
import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.person.Client;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ClientManager {
    public Client getClientByIdOrPhone(Integer id, String phone) throws SQLException {
        Where<Client, Object> where = JdbcDriverSetup.getDao(Client.class)
                .queryBuilder()
                .where();
        if (id != null) {
            where = where.eq("id", id);
        }

        if (phone != null) {
            where =where.eq("phone", phone);
        }
        return where
                .queryForFirst();
    }



    public void addClient(Client client) {
        try {
            JdbcDriverSetup.getDao(Client.class).create(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String id) throws SQLException {
        JdbcDriverSetup.getDao(Client.class).deleteById(id);
    }

    public void updateClient(Client client) throws SQLException {
        JdbcDriverSetup.getDao(Client.class)
                .update(client);
    }
}
