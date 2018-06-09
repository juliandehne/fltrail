package unipotsdam.gf.modules.journal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.Visibility;
import unipotsdam.gf.modules.journal.view.JournalView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Service Implementation to test the Rest
 */
public class DummyJournalService implements JournalService, IJournal {

    Logger log = LoggerFactory.getLogger(DummyJournalService.class);


    Calendar cal = Calendar.getInstance();
    StudentIdentifier studentIdentifier = new StudentIdentifier("0","0");
    StudentIdentifier studentIdentifier2 = new StudentIdentifier("0","1");

    Journal j1 = new Journal(studentIdentifier,"test", cal.getTimeInMillis() , Visibility.All, "test1");
    Journal j2 = new Journal(studentIdentifier,"test2", cal.getTimeInMillis() , Visibility.NONE, "test2");
    Journal j3 = new Journal(studentIdentifier,"test3", cal.getTimeInMillis() , Visibility.GROUP, "test3");
    Journal j4 = new Journal(studentIdentifier,"test4", cal.getTimeInMillis() , Visibility.DOZENT ,"test4");
    Journal j5 = new Journal(studentIdentifier2,"test5", cal.getTimeInMillis() , Visibility.GROUP, "test5");

    ArrayList<Journal> journals = new ArrayList<>();


    @Override
    public ArrayList<Journal> getAllJournals(String student, String project) {

        log.debug(">> get all journals(" + student , "," + project + ")");

        ArrayList<Journal> result = new ArrayList<>();

        for (Journal j: journals) {

            if (j.getVisibility() == Visibility.All){
                result.add(j);
            }
            if (j.getVisibility()== Visibility.NONE && j.getStudentIdentifier().getStudentId().equals(student)){
                result.add(j);
            }

            //Goup != Project, but for testing ok
            if (j.getVisibility()== Visibility.NONE && j.getStudentIdentifier().getProjectId().equals(project)){
                result.add(j);
            }

            //if Dozent

        }
        log.debug("<< get all journals(" + student , "," + project + ")");

        return result;
    }


    @Override
    public String exportJournal(StudentIdentifier student) {
        return null;
    }

    ArrayList<Journal> resetList () {
        journals = new ArrayList<>();

        journals.add(j1);
        journals.add(j2);
        journals.add(j3);
        journals.add(j4);
        journals.add(j5);

        return journals;
    }

}
