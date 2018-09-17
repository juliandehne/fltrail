package unipotsdam.gf.modules.annotation.websocket;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationWSTarget {

    // variables
    String targetId;
    Category targetCategory;

    // constructor
    public AnnotationWSTarget(String targetId, Category targetCategory) {
        this.targetId = targetId;
        this.targetCategory = targetCategory;
    }

    // methods
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Category getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(Category targetCategory) {
        this.targetCategory = targetCategory;
    }

    @Override
    public String toString() {
        return "AnnotationWSTarget{" +
                "targetId='" + targetId + '\'' +
                ", targetCategory=" + targetCategory.toString() +
                '}';
    }

}
