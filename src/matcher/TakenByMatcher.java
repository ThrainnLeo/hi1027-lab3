package matcher;

import model.Task;

public class TakenByMatcher implements ITaskMatcher{
    private final String requiredTakenBy;

    public TakenByMatcher(String requiredTakenBy){
        this.requiredTakenBy = requiredTakenBy;
    }

    @Override
    public boolean match(Task task) {
        String takenBy = task.getTakenBy();
        if(takenBy == null || takenBy.isEmpty()){
            return false;
        }
        return requiredTakenBy.equals(takenBy);
    }
}
