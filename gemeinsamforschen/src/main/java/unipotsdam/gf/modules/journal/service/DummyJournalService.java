package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.model.Visibility;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Service Implementation to test rest, no Database operations
 */

public class DummyJournalService implements JournalService {

    private Logger log = LoggerFactory.getLogger(DummyJournalService.class);


    private Calendar cal = Calendar.getInstance();

    private long id = 4;

    private ArrayList<Journal> journals = new ArrayList<>();

    public DummyJournalService(){

        resetList();
    }

    @Override
    public Journal getJournal(String id) {
        for (Journal j : journals) {
            if(j.getId() == Long.valueOf(id)){
                return j;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Journal> getAllJournals(String student, String project, JournalFilter filter) {
        log.debug(">> get all journals(" + student  + "," + project + ","  + filter + ")");

        ArrayList<Journal> result = new ArrayList<>();

        for (Journal j: journals) {

            //always show own Journals
            if(j.getStudentIdentifier().getStudentId().equals(student)){
                result.add(j);
            }else{

                // if Visibility All, show if Filter allows it
                if (j.getVisibility() == Visibility.ALL && filter==JournalFilter.ALL){
                    result.add(j);
                }

                //If Visibility Group, show if student is in group and filter allows it
                //TODO: project != Group, for testing ok, change for real Service
                if (j.getVisibility()== Visibility.GROUP && j.getStudentIdentifier().getProjectId().equals(project) && filter == JournalFilter.ALL){
                    result.add(j);
                }

                //TODO if Dozent
            }

        }
        log.debug("<< get all journals(" + student , "," + project + ")");

        return result;
    }

    @Override
    public ArrayList<Journal> getAllJournals(String student, String project) {
        return getAllJournals(student,project,JournalFilter.ALL);
     }

    @Override
    public boolean saveJournal(long id, String student, String project, String text, String visibility, String category) {
        if (id == -1){

            StudentIdentifier studentId = new StudentIdentifier(student,project);
            journals.add(new Journal(this.id++, studentId, text , cal.getTimeInMillis(), stringToVisibility(visibility) , category));

        } else {
            for (Journal j : journals){
                if(j.getId() == id){
                    j.setEntry(text);
                    j.setVisibility(stringToVisibility(visibility));
                    j.setCategory(category);
                }
            }
            resetList();
        }
        return true;
    }

    @Override
    public boolean deleteJournal(long id) {
        for (Journal j : journals) {
            if (j.getId() == id) {
                journals.remove(j);
                return true;
            }
        }
        return false;
    }

    @Override
    public void closeJournal(String journal) {

    }

    private Visibility stringToVisibility(String visibility) {
        // If String does not match enum IllegalArgumentException
        Visibility v ;
        try{
            v = Visibility.valueOf(visibility);
        }catch (IllegalArgumentException e){
            v = Visibility.MINE;
            log.debug("Illegal argument for visibility, default to MINE");
        }
        return v;
    }

    private void resetList() {

        StudentIdentifier studentIdentifier = new StudentIdentifier("0","0");
        StudentIdentifier studentIdentifier2 = new StudentIdentifier("0","1");

        String test = "**nec** nec facilisis nibh, sed sagittis tortor. Suspendisse vel felis ac leo dignissim efficitur. Nunc non egestas eros, sit amet vestibulum nunc. Sed bibendum varius molestie. Proin augue mauris, mollis sed efficitur efficitur, sagittis quis eros. Praesent tincidunt tincidunt porttitor. Maecenas quis ornare tellus. Nunc euismod vestibulum neque, sed luctus neque convallis in. Duis molestie ex ut nunc dignissim condimentum ut vitae dui. Vestibulum diam lorem, eleifend sit amet lobortis nec, vulputate a leo. In nec ante felis. Maecenas interdum nunc et odio placerat fringilla. Aenean felis purus, mollis id lectus non, fringilla tincidunt mi. Nunc sed rutrum ex, vel tempus odio.";

        Journal j1 = new Journal(0,studentIdentifier,test, cal.getTimeInMillis() , Visibility.ALL, "Recherche");
        j1.setCreator("Test Test");
        Journal j2 = new Journal(1,studentIdentifier,test, cal.getTimeInMillis() , Visibility.MINE, "Untersuchungskonzept");
        j2.setCreator("Test Test");
        Journal j3 = new Journal(2,studentIdentifier,test, cal.getTimeInMillis() , Visibility.GROUP, "Methodik");
        j3.setCreator("Test Test");
        Journal j4 = new Journal(3,studentIdentifier,test, cal.getTimeInMillis() , Visibility.DOZENT ,"Recherche");
        j4.setCreator("Test Test");
        Journal j5 = new Journal(4,studentIdentifier2,test, cal.getTimeInMillis() , Visibility.GROUP, "Durchführung");
        j5.setCreator("ASD DSA");

        journals = new ArrayList<>();

        journals.add(j1);
        journals.add(j2);
        journals.add(j3);
        journals.add(j4);
        journals.add(j5);

    }



}
