package model;

import matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
                .filter(task -> task.getId() == id)    //hittar ett unikt id
                .findFirst();                               // Returnerar första resultatet
        //Returnera Task-objektet, annars null om sökningen misslyckas
        return foundTask.orElse(null);
    }

    public List<Task> findTasks(ITaskMatcher matcher){
        List<Task> matchTask = tasks.stream()

                .filter(matcher::match)
                .collect(Collectors.toList());
        //.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        /*
            Eftersom Task implementerar Comparable<Task> och har compareTo() metoden,
            kommer Collections.sort() att sortera listan baserat på Prio (första nyckel)
            och Description (andra nyckel)
         */
        Collections.sort(matchTask);
        return matchTask;
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

    public String getTitle(){ //Detta kan vara helt fel (för att addProject ska funka i ProjectsManager ska funka)
        return title;
    }

    public int getId(){ //denna kan vara fel samma som getTitle
        return id;
    }

    public ProjectState getState() {
        if(tasks.isEmpty()){
            return ProjectState.EMPTY;
        }
        //kolla noggrannare på denna rad, Den ska bara kolla igenon task-objekten och sen kolla viket state den är i
        boolean allDone = tasks.stream().allMatch(task -> task.getState() == TaskState.DONE);

        if(allDone){
            return ProjectState.COMPLETED;
        }
        return ProjectState.ONGOING;
    }

    public LocalDate getLastUpdated(){
        if(tasks.isEmpty()){
            return created;
        }
        //kolla noga på denna rad jag förstår inte riktigt, detta har något med task-objekten att göra
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
                ", nextTaskId=" + nextTaskId +
                '}';
    }
}
