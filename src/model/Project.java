package model;

import matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class Project implements Comparable<Project>, Serializable {
    private final List<Task> tasks;
    private final String title;
    private final int id;
    private final String description;
    private final LocalDate created;
    private int nextTaskId;

    Project(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;

        this.tasks = new ArrayList<>();
        this.created = LocalDate.now();
        this.nextTaskId = 1;
    }

    public Task getTaskById(int id){
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    public List<Task> findTasks(ITaskMatcher matcher){//kolla om den funkar
        List<Task> taskMatcher = new ArrayList<>();
        for(Task task : tasks){
            if(matcher.match(task)){
                taskMatcher.add(task);
            }
        }
        Collections.sort(taskMatcher);
        return taskMatcher;
    }

    public Task addTask(String description, TaskPrio prio){

        Task nTask = new Task(description, prio, nextTaskId);
        tasks.add(nTask);
        nextTaskId++;
        return nTask;
    }

    public boolean removeTask(Task task){
        return tasks.remove(task);
    }


    public String getTitle(){
        return title;
    }

    public int getId(){
        return id;
    }

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
    public int hashCode() {
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
