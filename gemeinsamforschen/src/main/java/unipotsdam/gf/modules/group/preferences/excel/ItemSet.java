package unipotsdam.gf.modules.group.preferences.excel;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

public class ItemSet {

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String literature;
    @ExcelCell(1)
    private String context;
    @ExcelCell(2)
    private String comment;
    @ExcelCell(3)
    private String variable;
    @ExcelCell(4)
    private String variableDefinition;
    @ExcelCell(5)
    private String subVariable;
    @ExcelCell(7)
    private String itemGerman;
    @ExcelCell(8)
    private String ownTranslation;
    @ExcelCell(9)
    private String itemEnglish;
    @ExcelCell(10)
    private String ownTranslationEnglish;

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getLiterature() {
        return literature;
    }

    public void setLiterature(String literature) {
        this.literature = literature;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariableDefinition() {
        return variableDefinition;
    }

    public void setVariableDefinition(String variableDefinition) {
        this.variableDefinition = variableDefinition;
    }

    public String getSubVariable() {
        return subVariable;
    }

    public void setSubVariable(String subVariable) {
        this.subVariable = subVariable;
    }

    public String getItemGerman() {
        return itemGerman;
    }

    public void setItemGerman(String itemGerman) {
        this.itemGerman = itemGerman;
    }

    public String getOwnTranslation() {
        return ownTranslation;
    }

    public void setOwnTranslation(String ownTranslation) {
        this.ownTranslation = ownTranslation;
    }

    public String getItemEnglish() {
        return itemEnglish;
    }

    public void setItemEnglish(String itemEnglish) {
        this.itemEnglish = itemEnglish;
    }

    public String getOwnTranslationEnglish() {
        return ownTranslationEnglish;
    }

    public void setOwnTranslationEnglish(String ownTranslationEnglish) {
        this.ownTranslationEnglish = ownTranslationEnglish;
    }
}
