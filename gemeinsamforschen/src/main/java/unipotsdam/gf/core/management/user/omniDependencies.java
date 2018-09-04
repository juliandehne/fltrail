package unipotsdam.gf.core.management.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class omniDependencies extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String projectId = request.getParameter("projectId");
        JspWriter out = getJspContext().getOut();
        out.println("<meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>fltrail</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../assets/css/styles.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../assets/css/footer.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"../assets/fonts/font-awesome.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../assets/css/Sidebar-Menu-1.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../assets/css/Sidebar-Menu.css\">\n" +
                "    <script src=\"../assets/js/Sidebar-Menu.js\"></script>\n" +
                "    <script src=\"../assets/js/utility.js\"></script>\n" +
                "    <script src=\"../assets/js/footer.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.1.0/css/all.css\"\n" +
                "          integrity=\"sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt\" crossorigin=\"anonymous\">");
    }


};