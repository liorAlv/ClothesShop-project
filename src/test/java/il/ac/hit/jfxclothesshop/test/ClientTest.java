package il.ac.hit.jfxclothesshop.test;

import de.saxsys.javafx.test.JfxRunner;
import il.ac.hit.jfxclothesshop.person.managing.ClientManager;
import il.ac.hit.jfxclothesshop.person.managing.ClientUtils;
import javafx.scene.control.TextField;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@RunWith(JfxRunner.class)
@ContextConfiguration(classes = {
        JdbcConfiguration.class, ClientManager.class
})
public class ClientTest {
    private ClientManager clientManager;

    @Test
    public void testPhoneIsNotInFormatNumbers() throws Exception {
        TextField phone = new TextField("55555555");
        TextField name = new TextField("aa");
        TextField email = new TextField("aaa");
        TextField address = new TextField("aa");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }
    @Test
    public void testPhoneIsNotInFormatText() throws Exception {
        TextField phone = new TextField("55555fgdf555");
        TextField name = new TextField("aa");
        TextField email = new TextField("aaa");
        TextField address = new TextField("aa");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }

    @Test
    public void testPhoneEmptyFields() throws Exception{
        TextField phone = new TextField("");
        TextField name = new TextField("a");
        TextField email = new TextField("a");
        TextField address = new TextField("a");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }

    @Test
    public void testNameEmptyFields() throws Exception{
        TextField phone = new TextField("05644");
        TextField name = new TextField("");
        TextField email = new TextField("a");
        TextField address = new TextField("a");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }

    @Test
    public void testEmailEmptyFields() throws Exception{
        TextField phone = new TextField("05644");
        TextField name = new TextField("a");
        TextField email = new TextField("");
        TextField address = new TextField("a");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }

    @Test
    public void testAddressEmptyFields() throws Exception{
        TextField phone = new TextField("05644");
        TextField name = new TextField("a");
        TextField email = new TextField("a");
        TextField address = new TextField("");
        Assert.assertTrue(ClientUtils.isClientInputNotOkay(phone, name, email, address));
    }


}
