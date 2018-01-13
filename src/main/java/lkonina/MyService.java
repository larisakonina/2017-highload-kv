package lkonina;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lkonina.MyDAO;
import org.jetbrains.annotations.NotNull;
import ru.mail.polis.KVService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyService implements KVService {
    private static final String PREFIX = "id=";
    @NotNull
    private final HttpServer server;
    @NotNull
    private MyDAO dao;

    private static String extractId(@NotNull final String query) {

        if (!query.startsWith(PREFIX)) {
            throw new IllegalArgumentException("bad string");
        }
        return query.substring(PREFIX.length());

    }

    public MyService(int port, @NotNull final MyDAO dao) throws IOException {

        this.server = HttpServer.create(
                new InetSocketAddress(port), 0);
        this.server.createContext("/v0/status", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                final String response = "online";
                httpExchange.sendResponseHeaders(200, response.length());
                httpExchange.getResponseBody().write(response.getBytes());
                httpExchange.close();
            }
        });

        this.server.createContext("/v0/entity",

                http -> {
                    final String id = extractId((http.getRequestURI().getQuery()));

                    if (!id.isEmpty()) {
                        switch (http.getRequestMethod()) {

                            case "GET":
                                try {
                                    final byte[] value = dao.get(id);
                                    http.sendResponseHeaders(200, value.length);
                                    http.getResponseBody().write(value);
                                } catch (IOException e) {
                                    http.sendResponseHeaders(404, 0);
                                }
                                break;


                            case "DELETE":
                                dao.delete(id);
                                http.sendResponseHeaders(202, 0);
                                break;

                            case "PUT":
                                final int contentLength = Integer.valueOf(http.getRequestHeaders().getFirst("Content-Length"));
                                final byte[] putValue = new byte[contentLength];
                                http.getRequestBody().read(putValue);
                                dao.upsert(id, putValue);
                                http.sendResponseHeaders(201, 0);
                                break;

                            default:
                                http.sendResponseHeaders(405, 0);

                        }
                    } else http.sendResponseHeaders(400, 0);
                    http.close();

                });
    }


    @Override
    public void start() {

        this.server.start();
    }

    @Override
    public void stop() {

        this.server.stop(0);
    }
}
