import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Comparable<Task>, Serializable{ //Kolla exakt vad detta betyder
    private final String description;
    private final int id;
    private String takenBy;
    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;

    public Task(String description, TaskPrio prio, int id){
        this.description = description;
        this.id = id;
        this.prio = prio;

        this.takenBy = null;
        this.state = TaskState.TODO;
        this.lastUpdate = LocalDate.now();
    }

    public TaskState getState(){
        return state;
    }

    public LocalDate getLastUpdate(){
        return lastUpdate;
    }

    public TaskPrio getPrio(){
        return prio;
    }

    public String getTakenBy(){
        return takenBy;
    }

    public int getId(){
        return id;
    }

    //-------Setters--------
    //Allt måste uppdateras till aktuellt datum
    private void  lastUpdated(){
        this.lastUpdate = LocalDate.now();
    }

    //Sätter namnet på den som tar sig an uppgiften, (takenBy) Namnet på den som tar sig an uppgiften
    //Skickar en exception om uppgiften redan är tagen
    public void setTakenBy(String takenBy) throws IllegalStateException{
        //Den här kan vara fel måste kolla på den noggrannare senare
        if(takenBy != null && !takenBy.isEmpty()){
            throw new IllegalStateException("Activity already occupied by: " + this.takenBy);
        }
        this.takenBy = takenBy;
        lastUpdated();
    }

    public void setState(TaskState state){
        this.state = state;
        lastUpdated();
    }

    public void setPrio(TaskPrio prio){
        this.prio = prio;
        lastUpdated();
    }


    //måste göra ett interface eller hitta ett interface i instruktionerna om jag ska fortsätta

    @Override
    public int compareTo(Task other) {
        //Lite oklart vad som händer här kolla lite senare
        int prioComp = other.prio.compareTo(this.prio);
        if(prioComp != 0){
            return prioComp;
        }
        return description.compareTo(other.description);
    }
}
