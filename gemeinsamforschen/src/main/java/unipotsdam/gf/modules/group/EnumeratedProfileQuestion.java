package unipotsdam.gf.modules.group;

import java.util.ArrayList;
import java.util.List;

/**
 * If the question asks the user to select an option then
 * the question neds to provide a list of options as string
 * else: the number of checkboxes is stored and the answer is an index
 * note: multiple select is not implemented
 */
public class EnumeratedProfileQuestion extends ProfileQuestion {
    private java.util.List<String> options;

    public EnumeratedProfileQuestion() {
        super();
        this.options = new ArrayList<>();
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
