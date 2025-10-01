package matcher;

import model.Task;
import model.TaskPrio;

public class PrioMatcher implements ITaskMatcher{
    private final TaskPrio requiredPrio;

    public PrioMatcher(TaskPrio requiredPrio){
        this.requiredPrio = requiredPrio;
    }

    // Matchar om taskens prioritet är exakt densamma som den önskade prioriteten.
    @Override
    public boolean match(Task task) {
        return task.getPrio() == requiredPrio;
    }
}
