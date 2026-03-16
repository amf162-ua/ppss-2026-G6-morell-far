package ppss.P06;

import java.time.LocalDate;
import java.util.List;

public class MailServer {
    private String name;
    private String pass;
    public MailServer(String name, String pass){
        this.name = name;
        this.pass = pass;
    }
    public List<String> findMailItemsWithDate(LocalDate fecha) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
