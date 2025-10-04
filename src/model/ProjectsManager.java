package model;

import exception.TitleNotUniqueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Managees all projects in the system
 * Responsible for creating new projects and handling serialization/deserialization
 */

public class ProjectsManager implements Serializable {
        private final List<Project> projects;
        private int nextProjectId;

    /**
     *  Initializes a new ProjectsManager with an empty list of projects.
     */
    public ProjectsManager() {
            this.projects = new ArrayList<>();
            this.nextProjectId = 1;
        }

    /**
     *
     * @return A copy of the list of all projects
     */
    public List<Project> getProjects() {
            List<Project> projectCopy = new ArrayList<>(this.projects);
            return projectCopy;
        }

    /**
     * Clears the current list and replaces it with an incoming list of projects
     *          * (used during deserialization). Updates the nextProjectId accordingly.
     * @param incomingProjects
     */
    public void setProjects(List<Project> incomingProjects) {
            projects.clear();
            projects.addAll(incomingProjects);

        // nextProjectId is set to 1 + the highest ID found (or 0 if list is empty)
        nextProjectId = getHighestId() + 1;
        }

    /**
     * Checks if a given project title is currently unique in the system.
     * @param title
     * @return true if the title is unique (or false)
     */
    public boolean isTitleUnique(String title){
            for(Project project : projects){
                if(project.getTitle().equals(title)){
                    return false;
                }
            }
            return true;
        }

    /**
     * Creates a new project, assigns it a unique ID and adds it
     * @param title
     * @param description
     * @return
     * @throws TitleNotUniqueException if the project wit the same title already exists
     */
    public Project addProject(String title, String description) throws TitleNotUniqueException {

        if(!isTitleUnique(title)){
            throw new TitleNotUniqueException("Project with title " + title + "already exists!");
        }

        Project newProject = new Project(title, description, nextProjectId);
        projects.add(newProject);
        nextProjectId++;

        return newProject;
    }

    /**
     * Removes a specific project from the manager
     * @param project
     */
    public void removeProject(Project project){
            projects.remove(project);
        }

    /**
     * Retrives a project by its unique ID
     * @param id
     * @return The Project object, or null is not found
     */
    public Project getProjectById(int id){
            for(Project project : projects){
                if(project.getId() == id){
                    return project;
                }
            }
            return null;
        }

    /**
     * Searches for projects with a specific title
     * @param titleStr
     * @return A List<Project> matching the title
     */
    public List<Project> findProjects(String titleStr){
            List<Project> projectList = new ArrayList<>();
            for(Project project : projects){
                if(project.getTitle().equals(titleStr)){
                    projectList.add(project);
                }
            }
            return projectList;
        }

    /**
     * Finds the highest exsisting project-ID used for nextProjectId after deserialization
     * @return The highers ID found (or 0 if the list is empty)
     */
    public int getHighestId(){
        int highestId = 0;

        for(Project project : projects){
            if(project.getId() > highestId){
                highestId = project.getId();
            }
        }
        return highestId;
    }
}
