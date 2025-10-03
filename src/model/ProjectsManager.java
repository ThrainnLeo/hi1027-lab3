package model;

import exception.TitleNotUniqueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProjectsManager implements Serializable {
        private final List<Project> projects;
        private int nextProjectId;

        public ProjectsManager() {
            this.projects = new ArrayList<>();
            this.nextProjectId = 1;
        }

        public List<Project> getProjects() {
            List<Project> projectCopy = new ArrayList<>(this.projects);
            return projectCopy;
        }

        public void setProjects(List<Project> incomingProjects) {
            projects.clear();
            projects.addAll(incomingProjects);

            nextProjectId = getHighestId() + 1;
        }

        public boolean isTitleUnique(String title){
            for(Project project : projects){
                if(project.getTitle().equals(title)){
                    return false;
                }
            }
            return true;
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
        }

        public Project getProjectById(int id){
            for(Project project : projects){
                if(project.getId() == id){
                    return project;
                }
            }
            return null;
        }

        public List<Project> findProjects(String titleStr){
            List<Project> projectList = new ArrayList<>();
            for(Project project : projects){
                if(project.getTitle().equals(titleStr)){
                    projectList.add(project);
                }
            }
            return projectList;
        }

        public int getHighestId(){
            int highestId = 0;

            if(projects.isEmpty()){
                throw new IllegalStateException("No projects found!");
            }

            for(Project project : projects){
                if(project.getId() > highestId){
                    highestId = project.getId();
                }
            }
            return highestId;
        }
    }
