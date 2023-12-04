import java.io.Serializable;
import java.time.LocalDate;

public class User{
    public String username;
    String password;
    String designation;
    User()
    {
        username=null;
        password=null;
        designation=null;
    }

    User(String u, String p, String d)
    {
        username=u;
        password=p;
        designation=d;
    }
    
    public String getUsername()
    {
        return username;
    }
    String getPassword()
    {
        return password;
    }
    String getDesignation()
    {
        return designation;
    }
    void setPassword(String p)
    {
        password=p;
    }
    public void setUsername(String u)
    {
        username=u;
    }
    void setDesignation(String d)
    {
        designation=d;
    }

    
}


