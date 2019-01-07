package unipotsdam.gf.modules.group.preferences.groupal.response;

public class ResponseHolder
{
    private Results results;

    private String whichMatcherUsed;

    private String countOfGroups;

    private Groups[] groups;

    public Results getResults ()
    {
        return results;
    }

    public void setResults (Results results)
    {
        this.results = results;
    }

    public String getWhichMatcherUsed ()
    {
        return whichMatcherUsed;
    }

    public void setWhichMatcherUsed (String whichMatcherUsed)
    {
        this.whichMatcherUsed = whichMatcherUsed;
    }

    public String getCountOfGroups ()
    {
        return countOfGroups;
    }

    public void setCountOfGroups (String countOfGroups)
    {
        this.countOfGroups = countOfGroups;
    }

    public Groups[] getGroups ()
    {
        return groups;
    }

    public void setGroups (Groups[] groups)
    {
        this.groups = groups;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", whichMatcherUsed = "+whichMatcherUsed+", countOfGroups = "+countOfGroups+", groups = "+groups+"]";
    }
}
