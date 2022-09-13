package il.ac.hit.jfxclothesshop.person;

public class SalesPerson extends AbstractPerson{
    private User user;

    public SalesPerson(int id, String name) {
        super(id, name);
    }

    public SalesPerson() {
        super();
    }

    @Override
    public String getInfo() {
        return this.toString();
    }
}
