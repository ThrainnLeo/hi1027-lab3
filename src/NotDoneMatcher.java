public class NotDoneMatcher implements ITaskMatcher{
    //Kriteriet är fast så man behöver ej en konstruktor
    @Override
    public boolean match(Task task){
        //Den ska matcha om taskens tillstånd inte är DONE
        return task.getState() != TaskState.DONE;
    }
}
