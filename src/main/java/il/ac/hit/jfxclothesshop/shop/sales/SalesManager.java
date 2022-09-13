package il.ac.hit.jfxclothesshop.shop.sales;

import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.person.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//taking data of sales from DB
@Service
@Slf4j
public class SalesManager {
    public Client getActiveClientForBook(int id) throws SQLException {
        Sales borrowBook = JdbcDriverSetup
                .getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("item_id", id)
                .and()
                .eq("active", true)
                .queryForFirst();
        if (borrowBook != null) {
            return borrowBook.getClient();
        } else {
            return null;
        }
    }

    //taking data of how much borrowed book we have from DB
    public long getActiveBookBorrowsSize() throws SQLException {
        return JdbcDriverSetup.getDao(Sales.class)
                .queryBuilder()
                .where()
                .eq("active", true)
                .countOf();
    }

    //build the data of sales
    public Sales borrowBookByClient(Clothing item, Client client) throws SQLException {
        var borrow = Sales
                .builder()
                .item(item)
                .client(client)
                .active(true)
                .build();
        JdbcDriverSetup.getDao(Sales.class).create(borrow);
        return borrow;
    }

    //return item
    public void deactivateBookBorrow(Sales borrowBook) throws SQLException {
        borrowBook.setActive(false);
        JdbcDriverSetup.getDao(Sales.class)
                .update(borrowBook);
    }

    //delete from data
    public void deleteBookBorrowByClient(int clientID) throws SQLException{
        List<Sales> borrowBookList = JdbcDriverSetup.getDao(Sales.class).queryBuilder()
                        .where()
                        .eq("client_id", clientID).query();
        JdbcDriverSetup.getDao(Sales.class).delete(borrowBookList);
    }


    
}
