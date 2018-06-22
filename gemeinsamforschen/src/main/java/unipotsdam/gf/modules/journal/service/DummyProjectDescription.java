package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DummyProjectDescription implements ProjectDescriptionService {

    ArrayList<Map<String,String>> links;
    ArrayList<String> group;
    ProjectDescription testProject;

    public DummyProjectDescription(){

        links = new ArrayList();
        HashMap<String,String> link = new HashMap<>();
        link.put("Test", "www.test.de");
        link.put("Google", "www.google.de");

        group = new ArrayList<>();
        group.add("Test Person");
        group.add("Person Test");

        testProject = new ProjectDescription(0,"Test","Testdesription", new Project(), link, group, new Date().getTime());
    }


    @Override
    public ProjectDescription getProject(String project) {
        return testProject;
    }

    @Override
    public void saveProjectText(String text) {
        testProject.setDescription(text);
    }

    @Override
    public void saveProjectLinks(String text) {
        //convert String to List
        //setLinks
    }
}
