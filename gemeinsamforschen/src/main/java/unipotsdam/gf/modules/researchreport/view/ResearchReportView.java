package unipotsdam.gf.modules.researchreport.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;
import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.researchreport.model.ResearchReportSection;
import unipotsdam.gf.modules.researchreport.service.ResearchReportSectionDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Path("/researchReport")
public class ResearchReportView {

    Logger log = LoggerFactory.getLogger(ResearchReportView.class);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/saveResearchReportPart")
    public Response saveResearchReportPart(@FormParam("student") String student,
                                           @FormParam("project") String project, @FormParam("text") String text,
                                           @FormParam("category") String categoryString) {

        Category category = hasEmptyParameter(categoryString) ? Category.TITEL : Category.valueOf(categoryString);
        int id;
        if (!hasEmptyParameter(student, project, categoryString)) {
            category = Category.valueOf(categoryString);
            GroupDAO groupDAO = new GroupDAO(new MysqlConnect());
            id = groupDAO.getGroupIdByStudentIdentifier(new StudentIdentifier(project, student));
            if (id != 0) {
                ResearchReportSection researchReportSection = new ResearchReportSection(text, category, id, project);
                ResearchReportSectionDAO reportSectionDAO = new ResearchReportSectionDAO(new MysqlConnect());
                reportSectionDAO.persist(researchReportSection);
            } else {
                log.error("Group not found for projectId: {} and studentId: {}", project, student);
            }
        } else {
            log.error("One of the parameter was empty. student: {}, project: {}, category: {}", student, project, category);
            id = 0;
        }
        log.debug("student: {}, project: {}, text: {}, category: {}", student, project, text, categoryString);
        URI location;

        try {
            if (id == 0) {
                location = getLink(category, student, project, false);
            } else {
                location = getLink(category, student, project, true);
            }
            return Response.temporaryRedirect(location).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

    public URI getLink(Category category, String student, String project, boolean forward) throws URISyntaxException {
        String locationLink;

        switch (category) {
            case TITEL:
                locationLink = forward ? "create-research.jsp" : "create-title.jsp";
                break;
            case RECHERCHE:
                locationLink = forward ? "create-bibliography.jsp" : "create-research.jsp";
                break;
            case LITERATURVERZEICHNIS:
                locationLink = forward ? "create-question.jsp" : "create-bibliography.jsp";
                break;
            case FORSCHUNGSFRAGE:
                locationLink = forward ? "create-concept.jsp" : "create-question.jsp";
                break;
            case UNTERSUCHUNGSKONZEPT:
                locationLink = forward ? "create-method.jsp" : "create-concept.jsp";
                break;
            case METHODIK:
                locationLink = forward ? "create-process-description.jsp" : "create-method.jsp";
                break;
            case DURCHFUEHRUNG:
                locationLink = forward ? "create-evaluation.jsp" : "create-process-description.jsp";
                break;
            case AUSWERTUNG:
                locationLink = forward ? "create-overview.jsp" : "create-evaluation.jsp";
                break;
            default:
                locationLink = "create-overview.jsp";
                log.error("Wrong category for locationlink. Acutal category was: {}", category.name());
        }
        return new URI("../researchReport/" + locationLink + "?token=" + student + "&projectId=" + project);
    }

    public boolean hasEmptyParameter(String... parameters) {
        for (String parameter : parameters) {
            if (Objects.isNull(parameter) || parameter.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
