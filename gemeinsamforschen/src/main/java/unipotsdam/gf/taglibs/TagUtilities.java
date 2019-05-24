package unipotsdam.gf.taglibs;


import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static java.net.URLDecoder.decode;

public class TagUtilities {
    private static final Logger log = LoggerFactory.getLogger(TagUtilities.class);
    @Inject
    private ProjectDAO projectDAO;

    public TagUtilities() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    public String hierarchyToString(String hierarchyLevel) {
        StringBuilder resultBuilder = new StringBuilder();
        String result;
        for (int count = 0; count < Integer.parseInt(hierarchyLevel); count++) {
            resultBuilder.append("../");
        }
        result = resultBuilder.toString();
        return result;
    }

    public String printMe(String text) {
        return text;
    }

    public String getParamterFromQuery(String searchString, HttpServletRequest request) {
        String query;
        try {
            query = decode(request.getQueryString(), "UTF-8");
            String result;
            int ampersandPosition = query.indexOf('&');
            int queryParameterValuePosition = query.indexOf(searchString) + searchString.length() + 1;
            if (ampersandPosition != -1) {
                String queryPart = query.substring(queryParameterValuePosition);
                if (queryPart.indexOf('&') != -1) {
                    result = queryPart.substring(0, queryPart.indexOf('&'));
                } else {
                    result = queryPart;
                }
            } else {
                result = query.substring(queryParameterValuePosition);
            }
            return result;
        } catch (Exception e) {
            log.debug(e.toString());
        }
        return null;
    }

    public Phase getPhase(String projectName) {
        return projectDAO.getProjectByName(projectName).getPhase();
    }
}
