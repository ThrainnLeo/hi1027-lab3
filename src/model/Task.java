package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


public class Task  implements Comparable<Task>, Serializable {
    private final String description;
    private final int id;
    private String takenBy;
    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;


    Task(String description, TaskPrio prio, int id){
        this.description = description;
        this.id = id;
        this.prio = prio;

        this.takenBy = null;
        this.state = TaskState.TO_DO;
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

    private void  lastUpdated(){
        this.lastUpdate = LocalDate.now();
    }

    public void setTakenBy(String takenBy) throws IllegalStateException{
        if(this.takenBy != null){
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

    @Override
    public int compareTo(Task other) {
        int prioComp = prio.compareTo(other.prio);
        if(prioComp != 0){
            return prioComp;
        }
        return description.compareTo(other.description);
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return prio == task.prio && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, prio);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", takenBy='" + takenBy + '\'' +
                ", state=" + state +
                ", lastUpdate=" + lastUpdate +
                ", prio=" + prio +
                '}';
    }
}
