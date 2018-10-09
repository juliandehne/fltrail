package unipotsdam.gf.taglibs;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Footer extends SimpleTagSupport {

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String footerString = "    </div>\n" +
                "\n" +
                "    <div style=\"clear:left\"></div>\n" +
                "\n" +
                "</main>\n" +
                "<footer>\n" +
                "    <p>\n" +
                "        Impressum </br>\n" +
                "        Ansprechpartner</br>\n" +
                "        Fides</br>\n" +
                "    </p>\n" +
                "</footer>\n";
        footerString += "" +
                "        </ul>\n" +
                "    </div>";
        out.println(footerString);
    }
}