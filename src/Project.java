import java.io.Serializable;
import java.time.LocalDate;

public class Project implements Comparable<Task>, Serializable {

    private String title;
    private String description;
    private LocalDate created;
    private int id;
    private int nextTaskId;

    public Project(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public Task getTaskById(int id){

    }


    @Override
    public int compareTo(Task o) {
        return 0;
        //Lägger till kod till senare
    }
}
