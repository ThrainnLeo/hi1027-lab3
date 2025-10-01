package model;

import exception.TitleNotUniqueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectsManager implements Serializable {
        private final List<Project> projects;
        private int nextProjectId;

        public ProjectsManager() {
            this.projects = new ArrayList<>();
            this.nextProjectId = 1;
        }

        public List<Project> getProjects() {
            return projects;
        }

        public void setProjects(List<Project> incomingProjects) {
            projects.clear();
            projects.addAll(incomingProjects);
            //Detta kan vara en lösning har ej testat
            nextProjectId = incomingProjects.stream()
                    .mapToInt(Project::getId)
                    .max()
                    .orElse(0) //om listan är tom returnera 0
                    + 1;
        }

        public boolean isTitleUnique(String title){
            return projects.stream()
                    .noneMatch(project-> project.getTitle().equalsIgnoreCase(title));
        }

        public Project addProject(String title, String description) throws TitleNotUniqueException {

            if(!isTitleUnique(title)){
                throw new TitleNotUniqueException("Project with title " + title + "already exists!");
            }

            Project newProject = new Project(title, description, nextProjectId);
            projects.add(newProject);
            nextProjectId++;

            return newProject;
        }

        public void removeProject(Project project){
            projects.remove(project);
            nextProjectId--;
        }

        public Project getProjectById(int id){
            return projects.stream()
                    .filter(project-> project.getId() == id)
                    .findFirst().orElse(null);
        }

        public List<Project> findProjects(String titleStr){
            return projects.stream()
                    .filter(project -> project.getTitle().contains(titleStr))
                    .collect(Collectors.toList());
        }

        public int getHighestId(){
            if(projects.isEmpty()){
                throw new IllegalStateException("No projects found!");
            }
            return projects.stream()
                    .mapToInt(Project::getId)
                    .max()
                    .orElse(0);
        }
    }
