public class TakenByMatcher implements ITaskMatcher{
    private final String requiredTakenBy;

    public TakenByMatcher(String requiredTakenBy){
        //Tror att vi kan skita i toLowercase om vi vill spelar ej stor roll
        this.requiredTakenBy = requiredTakenBy.toLowerCase();
    }

    @Override
    public boolean match(Task task) {
        String takenBy = task.getTakenBy();
        if(takenBy == null || takenBy.isEmpty()){
            return false; //task är ej upptaget
        }
        return takenBy.equals(requiredTakenBy); //kanske behöver (trim och/eller lowercase)
    }

}
