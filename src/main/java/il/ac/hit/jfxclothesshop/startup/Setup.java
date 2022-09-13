package il.ac.hit.jfxclothesshop.startup;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//setup users(librarian and manager)
@Builder
@NoArgsConstructor
@Data
public class Setup {
    private String managerUser;
    private String managerPassword;
    private String librarianUser;
    private String librarianPassword;
    private String librarian2User;
    private String librarian2Password;
//this code will help to read the setup.jason file
    @JsonCreator
    public Setup(@JsonProperty("managerUser") String managerUser,
                 @JsonProperty("managerPassword") String managerPassword,
                 @JsonProperty("librarianUser") String librarianUser,
                 @JsonProperty("librarianPassword") String librarianPassword,
                 @JsonProperty("librarian2User") String librarian2User,
                 @JsonProperty("librarian2Password") String librarian2Password)  {
        this.managerUser = managerUser;
        this.managerPassword = managerPassword;
        this.librarianUser = librarianUser;
        this.librarianPassword = librarianPassword;
        this.librarian2User = librarian2User;
        this.librarian2Password = librarian2Password;

    }
}
