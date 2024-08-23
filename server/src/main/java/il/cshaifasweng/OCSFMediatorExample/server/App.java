package il.cshaifasweng.OCSFMediatorExample.server;
import org.hibernate.Session;
import java.io.IOException;


public class App {
    public static Session session;
    private static SimpleServer server;

    public static void main(String[] args) throws Exception {
        server = new SimpleServer(3002);
        ConnectToDataBase.initializeDatabase();
        System.out.println("Server is listening");
        server.listen();


    }}