package unipotsdam.gf.modules.researchreport.model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.HashMap;
import java.util.Objects;

public class ResearchReport {
    private HashMap<Category, ResearchReportSection> researchReportMap;

    public ResearchReport() {
        researchReportMap = new HashMap<>();
        for (Category category : Category.values()) {
            researchReportMap.put(category, null);
        }
    }

    public boolean isComplete() {
        for (Category category : Category.values()) {
            if (Objects.isNull(researchReportMap.get(category))) {
                return false;
            }
        }
        return true;
    }

    public HashMap<Category, ResearchReportSection> getResearchReportMap() {
        return researchReportMap;
    }
}
