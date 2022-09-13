package il.ac.hit.jfxclothesshop.test;

import javafx.embed.swing.JFXPanel;
import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.shop.sales.Inventory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
//@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        JdbcConfiguration.class, Clothing.class, Inventory.class
})
public class BookTest {
    @Autowired
    private Inventory inventory;

    @BeforeClass
    public static void bootstrapJavaFx(){
        // implicitly initializes JavaFX Subsystem
        // see http://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap
        new JFXPanel();
    }

    @Test
    public void addBook() throws Exception
    {
        Clothing book = new Clothing();
        book.setAuthor("J.K Rolling");
        book.setCategory("Fantasy");
        book.setLocation("G6");
        book.setTitle("Harry potter");
        inventory.add(book);

        Assert.assertEquals(JdbcDriverSetup.getDao(Clothing.class).queryBuilder()
                .where()
                .eq("title", "Harry potter")
                .queryForFirst(), book);

    }
    @Test
    public void removeBook(){



    }

}
