package il.ac.hit.jfxclothesshop.person;

import com.j256.ormlite.field.DatabaseField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPerson {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    protected int id;
    @Setter
    @DatabaseField()
    protected String name;

    public AbstractPerson(String name) {
        this.name = name;
    }

    public abstract String getInfo();

}

