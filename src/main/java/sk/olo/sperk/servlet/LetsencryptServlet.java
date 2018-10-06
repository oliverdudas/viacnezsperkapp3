package sk.olo.sperk.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Deprecated
/**
 * Not used anymore. Instead the AUTOMATIC SSL_MANAGEMENT by GCP is used
 */
public class LetsencryptServlet extends HttpServlet {

    public static final Map<String, String> challenges = new HashMap<>();

    static {
        challenges.put("YcvwBNHLk6I8UFXGqceWkVTwx8PmnHxgjAUkIXHXuDI", "YcvwBNHLk6I8UFXGqceWkVTwx8PmnHxgjAUkIXHXuDI.ORrhtxuI6QuTxk4ZN29rnZZkYTvzg1680C-9KUf4NDY");
        challenges.put("VBD8WfaR8nN6drYnloOTDdNHqnfHh4rT5PSybvYtJFk", "VBD8WfaR8nN6drYnloOTDdNHqnfHh4rT5PSybvYtJFk.ORrhtxuI6QuTxk4ZN29rnZZkYTvzg1680C-9KUf4NDY");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!req.getRequestURI().startsWith("/.well-known/acme-challenge/")) {
            resp.sendError(404);
            return;
        }
        String id = req.getRequestURI().substring("/.well-known/acme-challenge/".length());
        if (!challenges.containsKey(id)) {
            resp.sendError(404);
            return;
        }
        resp.setContentType("text/plain");
        resp.getOutputStream().print(challenges.get(id));
    }
}
