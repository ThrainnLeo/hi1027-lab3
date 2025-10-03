package matcher;

import model.Task;
import model.TaskState;

public class NotDoneMatcher implements ITaskMatcher{

    @Override
    public boolean match(Task task){
        //Den ska matcha om taskens tillstånd inte är DONE
        return task.getState() != TaskState.DONE;
    }
}
