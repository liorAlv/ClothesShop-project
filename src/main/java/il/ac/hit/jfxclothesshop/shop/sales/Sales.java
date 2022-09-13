package il.ac.hit.jfxclothesshop.shop.sales;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.person.Client;
import lombok.*;

import java.util.Calendar;

@DatabaseTable
@NoArgsConstructor
@Data
public class Sales {
    @DatabaseField(foreign = true, columnName = "client_id", foreignColumnName = "id")
    private Client client;
    @DatabaseField
    private long date;
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    @Setter(AccessLevel.NONE)
    private int id;
    @DatabaseField(foreign = true, columnName = "item_id", foreignColumnName = "id")
    private Clothing item;
    @DatabaseField(dataType = DataType.BOOLEAN_INTEGER)
    private boolean active;

    @Builder
    public Sales(Client client, Clothing item, boolean active) {
        this.client = client;
        this.id = id;
        this.item = item;
        this.active = active;
        this.date = System.currentTimeMillis();
    }


    public void setClient(Client client) {
        this.client = client;
    }


    public Calendar getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public Client getClient() {
        return client;
    }


}
