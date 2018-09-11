package unipotsdam.gf.modules.assessment.controller.model;
import java.util.*;

public class GroupEvalDataList {
    private String[] labels ;

    private List<GroupEvalDataDatasets> datasets = new ArrayList<>();

    public GroupEvalDataList(){}


    public GroupEvalDataList(List<GroupEvalDataDatasets> datasets,String[] labels){
        this.labels=labels;
        this.datasets=datasets;
    }


    public List<GroupEvalDataDatasets> getDataset() {
        return datasets;
    }

    public void setDataset(List<GroupEvalDataDatasets> dataset) {
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
