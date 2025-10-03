package model;

import matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/*
Representerar ett prijekt och dess meradata och en samling av tasks
Är jämförbar (Comparable) för sortering och serialiserbar för filhantering
 */

public class Project implements Comparable<Project>, Serializable {
    private final List<Task> tasks;
    private final String title;
    private final int id;
    private final String description;
    private LocalDate created; //kan behöva vara final
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
        Optional<Task> foundTask = tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst();
        return foundTask.orElse(null);
    }

    public List<Task> findTasks(ITaskMatcher matcher){
        return tasks.stream()
                .filter(matcher::match)
                .sorted(Comparator.comparing(Task::getPrio))
                .collect(Collectors.toList());
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
        boolean allDone = tasks.stream()
                .allMatch(task -> task.getState() == TaskState.DONE);

        if(allDone){
            return ProjectState.COMPLETED;
        }
        return ProjectState.ONGOING;
    }

    public LocalDate getLastUpdated(){
        if(tasks.isEmpty()){
            return created;
        }
        return tasks.stream()
                .map(Task::getLastUpdate)
                .max(LocalDate::compareTo)
                .orElse(this.created);
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
