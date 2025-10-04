package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The class represent a single task within a Project.
 * Tasks are identifies by ID within the Project
 * and are naturally ordered by Prio and Description
 */

public class Task  implements Comparable<Task>, Serializable {
    private final String description;
    private final int id;
    private String takenBy;
    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;

    /**
     * This constructor is package-private to ensure Tasks are only created
     * via the Project class to manage unique ID.
     * @param description
     * @param prio
     * @param id
     */

    Task(String description, TaskPrio prio, int id){
        this.description = description;
        this.id = id;
        this.prio = prio;

        this.takenBy = null;
        this.state = TaskState.TO_DO;
        this.lastUpdate = LocalDate.now();
    }

    /**
     *
     * @return the current state of the task
     */

    public TaskState getState(){
        return state;
    }

    /**
     *
     * @return the date of the last update to the task
     */

    public LocalDate getLastUpdate(){
        return lastUpdate;
    }

    /**
     *
     * @return the priority of the task
     */

    public TaskPrio getPrio(){
        return prio;
    }

    /**
     *
     * @return the name of the person assigned to the task
     * null if unassigned
     */

    public String getTakenBy(){
        return takenBy;
    }

    /**
     *
     * @return unique ID of the task within its project
     */

    public int getId(){
        return id;
    }

    /**
     * Updates the timestamp to the current date
     */

    private void  lastUpdated(){
        this.lastUpdate = LocalDate.now();
    }

    /**
     * Assigns the task to a person and does it only once
     * @param takenBy
     * @throws IllegalStateException if the task is already assigned
     */

    public void setTakenBy(String takenBy) throws IllegalStateException{
        if(this.takenBy != null){
            throw new IllegalStateException("Activity already occupied by: " + this.takenBy);
        }
        this.takenBy = takenBy;
        lastUpdated();
    }

    /**
     * Sets the state of the task and updates the date
     * @param state
     */

    public void setState(TaskState state){
        this.state = state;
        lastUpdated();
    }

    /**
     * Sets the priority of the task and updates the date
     * @param prio
     */

    public void setPrio(TaskPrio prio){
        this.prio = prio;
        lastUpdated();
    }

    /**
     * Compares this task with another task based on priority
     * and description as a secundary check for full natural order
     * @param other
     * @return Negativ, Zero or Positive integer as this object is less, equeal or grater
     * than the specified object
     */

    @Override
    public int compareTo(Task other) {
        int prioComp = prio.compareTo(other.prio);
        if(prioComp != 0){
            return prioComp;
        }
        return description.compareTo(other.description);
    }

    /**
     * Compares this object to the specified object and sets them
     * as equal based on priority and description
     * @param o   the reference object with which to compare.
     * @return true if the objects are equal (or false)
     */

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
