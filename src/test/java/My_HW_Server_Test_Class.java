import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import ru.smirnov.myjettypackage.My_HW_Server;
import org.junit.Assert;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class My_HW_Server_Test_Class {
    private static final String URL_CONST = "http://localhost:8081";
    private static final String USER_AGENT = ""; //"Mozilla/5.0";
    Server serverForTest;

    @Before
    public void startTheServer() throws Exception {
        //вызов метода запуска сервера в отдельном потоке,
        //чтобы тест обращения к серверу корректно отработал
        //(т.е. из текущего потока обращался к серверу с GET-запросом):
        new Thread(new Runnable() {
            @Override
            public void run() {
                serverForTest = new Server(8081);
                try {
                    My_HW_Server.runServer(serverForTest);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Test
    public void testGetResponse() throws Exception {
        URL obj = new URL(URL_CONST);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        //добавление request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("a", "4");
        con.setRequestProperty("b", "5");

        int responseCode = con.getResponseCode();

        System.out.println("GET Response Code :: " + responseCode);

        Assert.assertEquals(HttpURLConnection.HTTP_OK, responseCode);

        /*
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            System.out.println("GET request сработал");
        } else {
            System.out.println("GET request не сработал");
        }
        //*/
    }

    @After
    public void stopTheServer() throws Exception {
        My_HW_Server.stopServer(serverForTest);
    }
}
