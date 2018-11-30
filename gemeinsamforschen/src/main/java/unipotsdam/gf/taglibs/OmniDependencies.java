package unipotsdam.gf.taglibs;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OmniDependencies extends SimpleTagSupport {
    private Integer hierarchyLevel = 0;

    public void doTag() throws IOException {
        hierarchyLevel = getHierarchy();
        JspWriter out = getJspContext().getOut();
        out.println("<meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>fltrail</title>\n" +
                "    <link href=\"" + hierarchyToString(hierarchyLevel) + "libs/css/googleAPIS400-700.css\" rel=\"stylesheet\"> \n" +
                "    <link href=\""+hierarchyToString(hierarchyLevel) +"libs/css/googleAPIS300-400-700\" rel=\"stylesheet\">" +
                "    <link rel=\"stylesheet\" href=\""+hierarchyToString(hierarchyLevel)+"libs/bootstrap/css/bootstrap3.3.7.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/css/styles.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "taglibs/css/footer.css\">\n" +
                "    <script src=\"" + hierarchyToString(hierarchyLevel) + "libs/jquery/jquery.3.3.1.min.js\"></script>\n" +
                "    <script src=\"" + hierarchyToString(hierarchyLevel) + "libs/jquery/jquery.3.3.7.min.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/fonts/font-awesome.min.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/css/Sidebar-Menu-1.css\">\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/css/Sidebar-Menu.css\">\n" +
                "    <script src=\"" + hierarchyToString(hierarchyLevel) + "taglibs/js/utility.js\"></script>\n" +
                "    <script src=\"" + hierarchyToString(hierarchyLevel) + "taglibs/js/footer.js\"></script>\n" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "project/css/all.css\">" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "project/css/style.css\" type=\"text/css\">" +
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/tagsinput/jquery.tagsinput.min.css\">\n" +
                "    <script src=\"" + hierarchyToString(hierarchyLevel) + "libs/tagsinput/jquery.tagsinput.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"" + hierarchyToString(hierarchyLevel) + "libs/jquery/jqueryTemplate.js\"></script>"+
                "    <link rel=\"stylesheet\" href=\"" + hierarchyToString(hierarchyLevel) + "libs/fonts/fontawesome.5.1.0.css/\"\n" +
                "      integrity=\"sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt\" crossorigin=\"anonymous\">");
    }

    static public String hierarchyToString(Integer hierarchyLevel) {
        StringBuilder resultBuilder = new StringBuilder();
        String result;
        for (Integer count = 0; count < hierarchyLevel; count++) {
            resultBuilder.append("../");
        }
        result = resultBuilder.toString();
        return result;
    }

    public Integer getHierarchy() {
        return hierarchyLevel;
    }

    public void setHierarchy(Integer hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }
}