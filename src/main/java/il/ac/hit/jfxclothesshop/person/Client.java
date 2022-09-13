package il.ac.hit.jfxclothesshop.person;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@DatabaseTable
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client extends AbstractPerson {

    @DatabaseField(unique = true)
    private String phone;
    @DatabaseField
    private String address;
    @DatabaseField
    private String email;


    @Builder
    public Client(String name, String email, String phone, String address) {
        super(name);
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String getInfo() {
        return this.toString();
    }
}
