package model;

import matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a project, containing a list of tasks
 * Projects are naturally ordered by their title
 */

public class Project implements Comparable<Project>, Serializable {
    private final List<Task> tasks;
    private final String title;
    private final int id;
    private final String description;
    private final LocalDate created;
    private int nextTaskId;

    /**
     * This constructor is package-private to ensure Projects are only created
     * via the ProjectsManager class to manage unique title.
     * @param title
     * @param description
     * @param id
     */

    Project(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;

        this.tasks = new ArrayList<>();
        this.created = LocalDate.now();
        this.nextTaskId = 1;
    }

    /**
     * Retrives a Task by its unique ID within tis project.
     * @param id
     * @return
     */
    public Task getTaskById(int id){
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    /**
     * Filters the project's tasks using specific matching strategy
     * The returned list is sorted according to the natural order of Task
     * @param matcher
     * @return
     */
    public List<Task> findTasks(ITaskMatcher matcher){
        List<Task> taskMatcher = new ArrayList<>();
        for(Task task : tasks){
            if(matcher.match(task)){
                taskMatcher.add(task);
            }
        }
        Collections.sort(taskMatcher);
        return taskMatcher;
    }

    /**
     * Creates a new Task, assigns it a unique ID, and adds it to the project
     * @param description
     * @param prio
     * @return
     */
    public Task addTask(String description, TaskPrio prio){

        Task nTask = new Task(description, prio, nextTaskId);
        tasks.add(nTask);
        nextTaskId++;
        return nTask;
    }

    /**
     * Removes a task from the project
     * @param task
     * @return true if the task was removed (or false)
     */

    public boolean removeTask(Task task){
        return tasks.remove(task);
    }


    /**
     *
     * @return The projects title
     */
    public String getTitle(){
        return title;
    }

    /**
     *
     * @return The projects ID
     */
    public int getId(){
        return id;
    }

    /**
     * Determines the current state of the project based on its tasks
     * @return The ProjectState (EMPTY, ONGOING, or COMPLETED)
     */
    public ProjectState getState() {
        if(tasks.isEmpty()){
            return ProjectState.EMPTY;
        }
        boolean allDone = true;
        for(Task task : tasks){
            if(task.getState() != TaskState.DONE){
                allDone = false;
                break;
            }
        }
        if(allDone){
            return ProjectState.COMPLETED;
        }
        return ProjectState.ONGOING;
    }

    /**
     * Calculates the most recent update of the project
     * @return the LocalDate of the most recent activity
     */
    public LocalDate getLastUpdated(){
        if(tasks.isEmpty()){
            return created;
        }
        LocalDate lastUpdated = created;
        for(Task task : tasks){
            LocalDate tasksDate = task.getLastUpdate();
            if(tasksDate.isAfter(lastUpdated)){
                lastUpdated = tasksDate;
            }
        }
        return lastUpdated;
    }

    /**
     * Compares this Project with another Project based on the title for natural order
     * @param o Project to be compared
     * @return A negative, Zero, positive intager as the object is
     * less than, equal to or grater than the specified object
     */
    @Override
    public int compareTo(Project o) {
        return this.title.compareTo(o.title);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(title, project.title);
    }

    @Override
    public int hashCode() { //HashCode Genererar en int för att lättare hantera objekt
        return Objects.hashCode(title);
    }

    @Override
    public String toString() {
        return "Project{" +
                "tasks=" + tasks +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }
}
