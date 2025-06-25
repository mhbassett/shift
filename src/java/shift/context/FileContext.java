package shift.context;

import java.util.HashMap;
import java.util.Map;
import shift.structure.ShiftVariable;

public class FileContext extends ContextItem {
    public Map<String, ShiftVariable> variables = new HashMap<>();
}
