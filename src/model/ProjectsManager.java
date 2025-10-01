package model;

import exception.TitleNotUniqueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


    public class ProjectsManager implements Serializable {
        private final List<Project> projects;
        private int nextTaskId;

        public ProjectsManager() {
            this.projects = new ArrayList<>();
            this.nextTaskId = 1;
        }

        public boolean isTitleUnique(String title){
            return projects.stream()
                    .allMatch(project-> project.getTitle().equalsIgnoreCase(title));
        }

        public Project addProject(String title, String description) throws TitleNotUniqueException {

            if(isTitleUnique(title)){
                throw new TitleNotUniqueException("Project with title" + title + "already exists!");
            }

            Project newProject = new Project(title, description, nextTaskId);
            projects.add(newProject);
            nextTaskId++;

            return newProject;

        }

        public void removeProject(Project project){
            projects.remove(project);
            nextTaskId--;
        }

        //lite oklart om den ska vara en "List" eller "Optional" här och vad den ska göra
        public List<Project> getProjectById(int id){
            return projects; //skriva något här
        }

        public void setProjects(List<Project> incomingProjects) {

        }
    }
