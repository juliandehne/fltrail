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

        String desc = "<p id=\"output\"><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus urna purus, interdum vel efficitur et, consectetur nec nulla. Donec ut diam tellus. Donec vitae tellus ac dolor finibus luctus sed eget velit. Aliquam vitae ullamcorper eros, sit amet venenatis enim. Phasellus dui enim, viverra eu odio eget, ultrices tincidunt neque. Mauris venenatis luctus malesuada. Etiam vehicula turpis sed enim rhoncus, interdum interdum leo pretium. Pellentesque nec porttitor tellus, id tincidunt lacus. Mauris vitae cursus dui. Suspendisse ut ante quis nibh fermentum euismod a et ipsum. Donec aliquet id enim ut iaculis. Proin pulvinar est ac mollis fermentum. Quisque placerat pulvinar sapien, in dapibus mi aliquam finibus. Suspendisse fermentum vel lorem eget viverra. Aliquam eu orci ac nunc varius feugiat pellentesque quis dolor. Praesent erat sem, dictum eu elit quis, accumsan maximus leo.</p>\n" +
                "<p><strong>Donec nec facilisis nibh, sed sagittis tortor. Suspendisse vel felis ac leo dignissim efficitur. Nunc non egestas eros, sit amet vestibulum nunc. Sed bibendum varius molestie. Proin augue mauris, mollis sed efficitur efficitur, sagittis quis eros. Praesent</strong> tincidunt tincidunt porttitor. Maecenas quis ornare tellus. Nunc euismod vestibulum neque, sed luctus neque convallis in. Duis molestie ex ut nunc dignissim condimentum ut vitae dui. Vestibulum diam lorem, eleifend sit amet lobortis nec, vulputate a leo. In nec ante felis. Maecenas interdum nunc et odio placerat fringilla. Aenean felis purus, mollis id lectus non, fringilla tincidunt mi. Nunc sed rutrum ex, vel tempus odio.</p>\n" +
                "<p>Aenean turpis risus, ultrices nec fermentum quis, condimentum id orci. Vestibulum eu nibh dapibus, dictum ligula sed, tempus urna. Suspendisse scelerisque volutpat nibh sed accumsan. Ut laoreet condimentum ullamcorper. Nulla a dui eu mauris dictum accumsan. Nam viverra mauris in ultrices sollicitudin. Donec bibendum velit molestie ultricies commodo. Phasellus vulputate ullamcorper sapien eu ultricies. Pellentesque volutpat sed augue ac posuere. Integer finibus tempor eros a laoreet. Maecenas porta nibh elit, sed lacinia odio iaculis eu. Sed ornare ligula ipsum. Nunc vestibulum, arcu eget dapibus interdum, augue mi tempus velit, et sagittis massa magna sed eros. Nulla vehicula ac tortor a fringilla. Quisque in justo urna.</p>\n" +
                "<p><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/FuBK_testcard_vectorized.svg/2000px-FuBK_testcard_vectorized.svg.png\" alt=\"asd\"></p>\n" +
                "<p>Donec ut erat eget diam accumsan ornare condimentum eu purus. Morbi ullamcorper ex quam, ut varius magna iaculis ut. Maecenas rutrum vitae arcu ac pretium. Duis congue tempus eros non molestie. Vivamus at erat eu massa iaculis commodo. Nam aliquet, nibh a ultrices faucibus, diam ipsum molestie odio, quis imperdiet ligula nunc et erat. Sed vel leo vitae ex mattis pellentesque.</p>\n" +
                "<p>Nullam porttitor sit amet augue condimentum finibus. Curabitur ut pharetra lacus, in consequat nibh. Curabitur nec varius sapien, nec ornare felis. Mauris ornare varius arcu. Nulla quis tellus tempor, faucibus elit a, sollicitudin mi. Vivamus ligula diam, interdum a lorem in, pharetra tempor nunc. Mauris et dapibus erat, et sodales tortor. Vestibulum id tristique odio, ac vehicula orci. Quisque diam felis, volutpat nec condimentum vel, cursus eget justo.</p></p>";

        testProject = new ProjectDescription(0,"Eine kreative Überschrift",desc, new Project(), link, group, new Date().getTime());
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
    public void addLink(String link, String name) {
        //convert String to List
        //setLinks
    }

    @Override
    public void deleteLink(String link) {

    }
}
