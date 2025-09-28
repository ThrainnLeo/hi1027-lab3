public interface ITaskMatcher{

    //Kontrollerar om ett givet Task-obj matchar det angivna kriteriet
    boolean match(Task task);
}
