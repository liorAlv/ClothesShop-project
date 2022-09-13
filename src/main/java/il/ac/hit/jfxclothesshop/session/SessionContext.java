package il.ac.hit.jfxclothesshop.session;

import il.ac.hit.jfxclothesshop.shop.clothing.Clothing;
import il.ac.hit.jfxclothesshop.person.Client;
import il.ac.hit.jfxclothesshop.person.User;
import lombok.Data;

//singleton. will save the type of user that logged in only if he logged in. there will be only one object of that type.

@Data
public final class SessionContext {
    private User currentUser;
    private Clothing currentBook;
    private Client currentClient;

    private SessionContext() {

    }

    public boolean isCurrentUserManager() {
        return getCurrentUser() != null && User.UserType.MANAGER == getCurrentUser().getUserType();
    }

    private static class InstanceHolder {
        public static final SessionContext INSTANCE = new SessionContext();
    }

    public static SessionContext getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
