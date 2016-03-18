package javanio_app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Lenovo on 10.03.2016.
 */
public class Authorization implements Runnable {
    private List queue = new LinkedList();
    private final String mrOK = "OK";
    private final String mrFalse = "FALSE";
    private String inStr;
    private ServerDataEvent dataEvent;
    private static String url = "";
    private static String driver;
    private static String username;
    private static String password;
    static{
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("../server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("drivername");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public Authorization(String input, ServerDataEvent dataEvent) {
        this.inStr = input;
        this.dataEvent = dataEvent;
    }

    public void run() {
        inStr = inStr.replaceAll("\r\n","");
        String result = "NO\r\n";
        try
        {
            int begin = inStr.indexOf("[");
            int end = inStr.indexOf("]");
            String logPass = inStr.substring(begin + 1,end);

            String[] authData = logPass.split("&&");
            String Login = authData[0];
            String Password = authData[1];

            Connection conn = null;
            try {
                Class.forName(driver);
                conn = DriverManager
                        .getConnection(url,
                                username, password);

                CallableStatement proc = conn.prepareCall("{ ? = call auth(?, ?) }");
                proc.registerOutParameter(1, Types.INTEGER);
                proc.setString(2,Login);
                proc.setString(3,Password);
                proc.execute();

                int procRes = proc.getInt(1);

                if (procRes == 1)
                    result = "YES\r\n";
                proc.close();
                conn.close();
            } catch (Exception e) {
               e.printStackTrace();
                return;
            }
        }catch (Exception e)
        {

        }
        dataEvent.server.send(dataEvent.socket, result.getBytes());
    }
}
