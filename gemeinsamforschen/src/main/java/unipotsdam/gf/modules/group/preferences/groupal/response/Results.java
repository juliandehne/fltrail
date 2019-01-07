package unipotsdam.gf.modules.group.preferences.groupal.response;

public class Results
{
    private String stDev;

    private String varianz;

    private String matcher;

    private String n;

    private String avg;

    private String averageVarianz;

    private String performanceIndex;

    private String normStDev;

    public String getStDev ()
    {
        return stDev;
    }

    public void setStDev (String stDev)
    {
        this.stDev = stDev;
    }

    public String getVarianz ()
    {
        return varianz;
    }

    public void setVarianz (String varianz)
    {
        this.varianz = varianz;
    }

    public String getMatcher ()
{
    return matcher;
}

    public void setMatcher (String matcher)
    {
        this.matcher = matcher;
    }

    public String getN ()
    {
        return n;
    }

    public void setN (String n)
    {
        this.n = n;
    }

    public String getAvg ()
    {
        return avg;
    }

    public void setAvg (String avg)
    {
        this.avg = avg;
    }

    public String getAverageVarianz ()
    {
        return averageVarianz;
    }

    public void setAverageVarianz (String averageVarianz)
    {
        this.averageVarianz = averageVarianz;
    }

    public String getPerformanceIndex ()
    {
        return performanceIndex;
    }

    public void setPerformanceIndex (String performanceIndex)
    {
        this.performanceIndex = performanceIndex;
    }

    public String getNormStDev ()
    {
        return normStDev;
    }

    public void setNormStDev (String normStDev)
    {
        this.normStDev = normStDev;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stDev = "+stDev+", varianz = "+varianz+", matcher = "+matcher+", n = "+n+", avg = "+avg+", averageVarianz = "+averageVarianz+", performanceIndex = "+performanceIndex+", normStDev = "+normStDev+"]";
    }
}
