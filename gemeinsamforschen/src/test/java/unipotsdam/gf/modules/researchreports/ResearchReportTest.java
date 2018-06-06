package unipotsdam.gf.modules.researchreports;




import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.junit.Test;
import org.w3c.dom.ls.LSInput;
import unipotsdam.gf.modules.researchreport.ResearchReport;

import java.util.*;

public class ResearchReportTest {

    @Test
    public void egal() {
        System.out.print("hello world");
        List<ResearchReport> researchReportList = new ArrayList<ResearchReport>();
        List<ResearchReport> researchReportList2 = new LinkedList<>();

        for (ResearchReport researchReport : researchReportList2) {
            System.out.print(researchReport.getTitle());
        }

    }
}
