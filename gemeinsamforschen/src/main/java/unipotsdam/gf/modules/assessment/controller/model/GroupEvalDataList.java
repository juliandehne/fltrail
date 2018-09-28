package unipotsdam.gf.modules.assessment.controller.model;

import java.util.ArrayList;
import java.util.List;

public class GroupEvalDataList {
    private String[] labels;

    private List<GroupEvalDataDatasets> datasets = new ArrayList<>();

    public GroupEvalDataList() {
    }

    public GroupEvalDataList(String[] labels, List<GroupEvalDataDatasets> datasets) {
        this.datasets = datasets;
        this.labels = labels;
    }

    public GroupEvalDataList(List<GroupEvalDataDatasets> datasets, String[] labels) {
        this.labels = labels;
        this.datasets = datasets;
    }


    public List<GroupEvalDataDatasets> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<GroupEvalDataDatasets> dataset) {
        this.datasets = dataset;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public void appendDataSet(GroupEvalDataDatasets data) {
        this.datasets.add(data);

    }
}
