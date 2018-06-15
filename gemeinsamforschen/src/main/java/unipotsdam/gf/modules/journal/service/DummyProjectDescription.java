package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DummyProjectDescription implements ProjectDescriptionService {

    ArrayList<Map<String,String>> links;
    ArrayList<StudentIdentifier> group;
    ProjectDescription testProject;

    public DummyProjectDescription(){

        links = new ArrayList();
        HashMap<String,String> l1 = new HashMap<>();
        HashMap<String,String> l2 = new HashMap<>();
        l1.put("Test", "www.test.de");
        l2.put("Google", "www.google.de");
        links.add(l1);
        links.add(l2);

        group = new ArrayList<>();
        group.add(new StudentIdentifier("0","0"));
        group.add(new StudentIdentifier("0","1"));

        testProject = new ProjectDescription(0,"Test","Testdesription", new Project(), links, group, new Date().getTime());
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
