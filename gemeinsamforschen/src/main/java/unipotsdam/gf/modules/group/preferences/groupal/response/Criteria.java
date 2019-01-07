package unipotsdam.gf.modules.group.preferences.groupal.response;

public class Criteria
{
    private String weight;

    private String minValue;

    private String name;

    private String[] value;

    private String isHomogeneous;

    private String maxValue;

    public String getWeight ()
    {
        return weight;
    }

    public void setWeight (String weight)
    {
        this.weight = weight;
    }

    public String getMinValue ()
    {
        return minValue;
    }

    public void setMinValue (String minValue)
    {
        this.minValue = minValue;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getValue ()
    {
        return value;
    }

    public void setValue (String[] value)
    {
        this.value = value;
    }

    public String getIsHomogeneous ()
    {
        return isHomogeneous;
    }

    public void setIsHomogeneous (String isHomogeneous)
    {
        this.isHomogeneous = isHomogeneous;
    }

    public String getMaxValue ()
    {
        return maxValue;
    }

    public void setMaxValue (String maxValue)
    {
        this.maxValue = maxValue;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [weight = "+weight+", minValue = "+minValue+", name = "+name+", value = "+value+", isHomogeneous = "+isHomogeneous+", maxValue = "+maxValue+"]";
    }
}
