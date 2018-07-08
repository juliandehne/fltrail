package unipotsdam.gf.modules.journal.model;

import java.util.ArrayList;

public interface LinkDAO {

    void addLink(Link link);
    void deleteLink(String linkId);
    Link getLink(String linkId);
    ArrayList<Link> getAllLinks(String descriptionID);

}
