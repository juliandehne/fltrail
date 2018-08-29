package unipotsdam.gf.core.management.user;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class footer extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<footer>\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"progress\">\n" +
                "                <div class=\"progress-bar pg-groups\" role=\"progressbar\" id=\"progressbar\">\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    Assessment - Präsentationsphase - Dossier - Reflexionsphase - Feedbackphase - Gruppenbildung\n" +
                "                </div>\n" +
                "                <div class=\"progress-bar pg-rest\" role=\"progressbar\">\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <button id=\"nextPhase\" class=\"btn btn-light\">nächste Phase</button>\n" +
                "            <button id=\"btnUnstructuredUpload\" class=\"btn btn-light\">Unstrukturierte Abgabe</button>\n" +
                "        </div>\n" +
                "    </footer>");
    }


};