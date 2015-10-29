import utils.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Jerry on 25/10/15.
 */
public class MainServlet extends AbsElasticServlet {

    private static final String INDEX = "mobile";
    private static final String TYPE = "cars";
    private static final String FIELD_FROM = "from";
    private static final String FIELD_TO = "to";
    private static final String FIELD_RANGE = "field_range";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        getServletContext().getRequestDispatcher("/elastic.jsp").forward
                (req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        String action = req.getParameter("action");
        if (action.equals("query")) {
            searchDocumentWithBoolQueryBuidler(INDEX, TYPE, req);
            if (Strings.isValid(req.getParameter(FIELD_FROM)) && Strings.isValid(req.getParameter(FIELD_TO))) {
                addRangeFilter(req.getParameter(FIELD_RANGE), req.getParameter(FIELD_FROM), req.getParameter(FIELD_TO));
            }
            req.setAttribute("dataSet", getSearchHits());
            getServletContext().getRequestDispatcher("/elastic.jsp").forward
                    (req, resp);
        } else {
            out.println("Wrong POST request!");
        }
    }

}
