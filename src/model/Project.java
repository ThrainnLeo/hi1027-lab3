package model;

import matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a project, its metadata, and a collection of tasks.
 * The class is comparable ({@code Comparable}) for sorting based on the title
 * and serializable for file handling.
 */


public class Project implements Comparable<Project>, Serializable {
    private final List<Task> tasks;
    private final String title;
    private final int id;
    private final String description;
    private LocalDate created; //kan behöva vara final
    private int nextTaskId;

    /**
     * Creates a new instance of {@code Project}.
     *
     * @param title The title of the project.
     * @param description The description of the project.
     * @param id The unique ID of the project.
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
     * Searches for a task within the project based on its unique ID.
     *
     * @param id The ID of the task to find.
     * @return The found {@code Task} object, or {@code null} if no task
     * with the specified ID was found in the project.
     */

    public Task getTaskById(int id){
        Optional<Task> foundTask = tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
        return foundTask.orElse(null);
    }

    /**
     * Searches for tasks in the project that match the given matching criteria.
     * The result is sorted by task priority ({@code TaskPrio}).
     *
     * @param matcher An {@code ITaskMatcher} object that defines the matching criteria.
     * @return A sorted {@code List<Task>} of the matching tasks.
     */

    public List<Task> findTasks(ITaskMatcher matcher){
        return tasks.stream()
                .filter(task -> matcher.match(task))
                .sorted(Comparator.comparing(task -> task.getPrio()))
                .collect(Collectors.toList());
    }

    /**
     * Adds a new task to the project.
     * The new task is assigned the next available unique ID within the project.
     *
     * @param description The description of the task.
     * @param prio The priority of the task.
     * @return The newly created {@code Task} object.
     */

    public Task addTask(String description, TaskPrio prio){

        Task nTask = new Task(description, prio, nextTaskId);
        tasks.add(nTask);
        nextTaskId++;
        return nTask;
    }

    /**
     * Removes a specified task from the project.
     *
     * @param task The task to be removed.
     * @return {@code true} if the task was removed, otherwise {@code false}.
     */

    public boolean removeTask(Task task){
        return tasks.remove(task);
    }

    /**
     * Returns the title of the project.
     *
     * @return The project's title as a {@code String}.
     */

    public String getTitle(){
        return title;
    }

    /**
     * Returns the unique ID of the project.
     *
     * @return The project's ID as an {@code int}.
     */

    public int getId(){
        return id;
    }

    /**
     * Calculates and returns the current state of the project.
     *
     * @return {@code ProjectState.EMPTY} if the project has no tasks,
     * {@code ProjectState.COMPLETED} if all tasks are done,
     * otherwise {@code ProjectState.ONGOING}.
     */

    public ProjectState getState() {
        if(tasks.isEmpty()){
            return ProjectState.EMPTY;
        }
        boolean allDone = tasks.stream()
                .allMatch(task -> task.getState() == TaskState.DONE);

        if(allDone){
            return ProjectState.COMPLETED;
        }
        return ProjectState.ONGOING;
    }

    /**
     * Returns the latest update date among all tasks in the project.
     * If the project is empty, the creation date is returned.
     *
     * @return The last update date as a {@code LocalDate}.
     */

    public LocalDate getLastUpdated(){
        if(tasks.isEmpty()){
            return created;
        }
        return tasks.stream()
                .map(Task::getLastUpdate)
                .max(LocalDate::compareTo)
                .orElse(this.created);
    }

    /**
     * Compares this project with another project based on the title.
     * Used for sorting.
     *
     * @param o The project to compare with.
     * @return A negative integer, zero, or a positive integer as this project's title
     * is lexicographically less than, equal to, or greater than the title of the
     * specified project.
     */

    @Override
    public int compareTo(Project o) {
        return this.title.compareTo(o.title);
    }

    /**
     * Indicates whether some other object is "equal to" this one. Two projects are
     * considered equal if they have the same title (case-sensitive).
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(title, project.title);
    }

    /**
     * Returns a hash code value for the object. The hash code is based on the title.
     *
     * @return A hash code for this object.
     */

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }

    /**
     * Returns a string representation of the project.
     *
     * @return A {@code String} containing the project's metadata and tasks.
     */

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
