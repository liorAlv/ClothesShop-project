package il.ac.hit.jfxclothesshop.shop.clothing;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import il.ac.hit.jfxclothesshop.JdbcDriverSetup;
import lombok.*;

import java.sql.SQLException;

@DatabaseTable(tableName = "item")
@NoArgsConstructor
@Data
public class Clothing {
    @DatabaseField(columnName = "id",generatedId = true, allowGeneratedIdInsert = true)
    @Setter(AccessLevel.NONE)
    private int sku;
    @DatabaseField
    private String title;
    @DatabaseField
    private String author;
    @DatabaseField
    private String category;
    @DatabaseField
    private int amount;
    @DatabaseField
    private String location;

    //Constructors
    @Builder
    public Clothing(String title, String author, String category, String location){
        this.title=title;
        this.author=author;
        this.category=category;
        this.amount=amount;
        this.location=location;

    }

    // taking data of book from DB
    public Clothing showBookInfo(String sku){
        try {
            return JdbcDriverSetup.getDao(Clothing.class).queryForId(sku);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }
}

