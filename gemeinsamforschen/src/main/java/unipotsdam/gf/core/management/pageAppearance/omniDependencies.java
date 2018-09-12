package unipotsdam.gf.core.management.pageAppearance;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class omniDependencies extends SimpleTagSupport {
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>fltrail</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../libs/css/styles.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../libs/css/footer.css\">\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
                "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"../libs/fonts/font-awesome.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../libs/css/Sidebar-Menu-1.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"../libs/css/Sidebar-Menu.css\">\n" +
                "    <script src=\"../libs/js/Sidebar-Menu.js\"></script>\n" +
                "    <script src=\"../libs/js/utility.js\"></script>\n" +
                "    <script src=\"../libs/js/footer.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.1.0/css/all.css\"\n" +
                "      integrity=\"sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt\" crossorigin=\"anonymous\">");
    }
}