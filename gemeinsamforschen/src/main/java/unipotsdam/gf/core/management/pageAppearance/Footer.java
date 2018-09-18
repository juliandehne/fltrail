package unipotsdam.gf.core.management.pageAppearance;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.states.model.ProjectPhase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class Footer extends SimpleTagSupport {
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String projectId = request.getParameter("projectId");
        ManagementImpl management = new ManagementImpl();
        ProjectPhase projectPhase;
        try {
            projectPhase = management.getProjectById(projectId).getPhase();
        } catch (Exception e) {
            projectPhase = null;
        }
        JspWriter out = getJspContext().getOut();
        String footerString = "<Footer>\n" +
                "        <div class=\"container\">\n";
        if (projectPhase != null) {
            footerString += "        <div class=\"progress\">\n" +
                    "                <div class=\"progress-bar pg-" + projectPhase.toString().trim() + "\" role=\"progressbar\" id=\"progressbar\">\n" +
                    "                </div>\n" +
                    "                <div>\n";
            if (projectPhase.equals(ProjectPhase.GroupFormation))
                footerString +="  GroupFormation\n";
            if (projectPhase.equals(ProjectPhase.Execution))
                footerString +="  Execution\n";
            if (projectPhase.equals(ProjectPhase.Assessment))
                footerString +="  Assessment\n";
            if (projectPhase.equals(ProjectPhase.DossierFeedback))
                footerString +="  DossierFeedback\n";
            if (projectPhase.equals(ProjectPhase.Projectfinished))
                footerString +="  Projectfinished\n";
            footerString +="        </div>\n" +
                    "                <div class=\"progress-bar pg-rest\" role=\"progressbar\">\n" +
                    "                </div>\n" +
                    "            </div>\n";
        }

        footerString += "<button id=\"btnUnstructuredUpload\" class=\"btn btn-light\">Unstrukturierte Abgabe</button>\n" +
                "<button id=\"footerBack\" class=\"btn btn-light\">zurück</button>\n" +
                "        </div>\n" +
                "    </Footer>";
        out.println(footerString);
    }
}