package unipotsdam.gf.modules.group.preferences.model;

public class Item {
    private String germanName;
    private String englishName;
    private Boolean positive;
    private ScaleType scaleType;

    public Item(
            String germanName, String englishName, Boolean positive, ScaleType scaleType) {
        this.germanName = germanName;
        this.englishName = englishName;
        this.positive = positive;
        this.scaleType = scaleType;
    }

    public String getGermanName() {
        return germanName;
    }

    public void setGermanName(String germanName) {
        this.germanName = germanName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Boolean getPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }
}
