import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Project implements Comparable<Task>, Serializable {

    private List<Task> tasks; //vet ej om jag har implementerat denna rätt
    private String title;
    private int id;
    private String description;
    private LocalDate created;
    private int nextTaskId;

    public Project(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public Task getTaskById(int id){
        //använder stream för att hitta första matchningen av element
        Optional<Task> foundTask = tasks.stream()
                .filter(task -> task.getId() == id) //hittar ett unikt id
                .findFirst(); // Returnerar första resultatet
            //Returnera Task-objektet, annars null om sökningen misslyckas
        return foundTask.orElse(null);
    }

    public List<Task> findTasks(ITaskMatcher matcher){

        /*
            a) Använd tasks.stream() för att börja bearbeta listan.
            b) Använd .filter(matcher::match) för att endast behålla de tasks där matchern returnerar true.
            c) Använd .collect(Collectors.toList()) (eller motsvarande) för att samla resultatet i en ny lista
         */

        List<Task> matchTask = tasks.stream()

                .filter(matcher::match)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
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

    public ProjectState getState() {
        if(tasks.isEmpty()){
            return ProjectState.EMPTY;
        }
        //Stream() används ist för for-loop för att gå igenom alla element i listan
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
        //----Så ska detta funka-------
        /*
        // 2. Använd Streams för att hitta det senaste datumet (det "högsta" datumet).
        // Flöde:
            // a) tasks.stream(): Skapa en ström av alla Task-objekt.
            // b) .map(Task::getLastUpdate): Hämta LastUpdate-datumet från varje task.
            // c) .max(LocalDate::compareTo): Hitta det maximala datumet i strömmen.
            // d) .orElse(this.created): Om listan av datum är tom (skydd mot null),
         */

        //kolla noga på denna rad jag förstår inte riktigt, detta har något med task-objekten att göra
        return tasks.stream()
                .map(Task::getLastUpdate)
                .max(LocalDate::compareTo)
                .orElse(this.created);
    }


    @Override
    public int compareTo(Task other) {
        return 0;
        //Lägger till kod till senare
    }
}
