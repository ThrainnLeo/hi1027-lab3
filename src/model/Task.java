package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a single task within a project.
 * The class implements {@code Comparable} for sorting (based on priority and description)
 * and is {@code Serializable} for file I/O.
 */

public class Task  implements Comparable<Task>, Serializable {
    private final String description;
    private final int id;
    private String takenBy;
    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;

    /**
     * Creates a new instance of {@code Task}.
     *
     * @param description The description of the task.
     * @param prio The priority of the task.
     * @param id The unique ID of the task within the project.
     */

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

    /**
     * Sets the name of the person taking responsibility for the task.
     * Can only be set if the task is not already assigned.
     * Also updates {@code lastUpdate}.
     *
     * @param takenBy The name of the person taking the task.
     * @throws IllegalStateException If the task is already assigned.
     */

    public void setTakenBy(String takenBy) throws IllegalStateException{
        if(this.takenBy != null){
            throw new IllegalStateException("Activity already occupied by: " + this.takenBy);
        }
        this.takenBy = takenBy;
        lastUpdated();
    }

    /**
     * Sets the new state of the task.
     * Also updates {@code lastUpdate}.
     *
     * @param state The new state as a {@code TaskState}.
     */

    public void setState(TaskState state){
        this.state = state;
        lastUpdated();
    }

    /**
     * Sets the new priority of the task.
     * Also updates {@code lastUpdate}.
     *
     * @param prio The new priority as a {@code TaskPrio}.
     */

    public void setPrio(TaskPrio prio){
        this.prio = prio;
        lastUpdated();
    }

    /**
     * Compares this task with another task for sorting.
     * Sorts primarily by priority (highest priority first), then by description.
     *
     * @param other The {@code Task} to compare with.
     * @return A negative integer, zero, or a positive integer as this task is
     * less than, equal to, or greater than the specified task.
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
     * Indicates whether some other object is "equal to" this one. Two tasks are
     * considered equal if they have the same priority and description (case-sensitive).
     * The ID is excluded from the comparison.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return prio == task.prio && Objects.equals(description, task.description);
    }

    /**
     * Returns a hash code value for the object. The hash code is based on the description
     * and priority.
     *
     * @return A hash code for this object.
     */

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
