package unipotsdam.gf.modules.assessment.controller.model;



public class GroupEvalDataDatasets {
    private String label;
    private int[] data;

    private String borderColor= "rgba(0,255,0,0.2)";
    private String backgroundColor= "rgba(0,255,0,0.2)";
    private boolean fill=false;

    public GroupEvalDataDatasets(){}

    public GroupEvalDataDatasets(String label,int[] data, String backgroundColor, String borderColor, boolean fill){
        this.backgroundColor=backgroundColor;
        this.borderColor=borderColor;
        this.data=data;
        this.fill=fill;
        this.label=label;
    }


    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
/*
    public void appendData(int data) {
        this.data.add(data);
    }
*/
    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}


