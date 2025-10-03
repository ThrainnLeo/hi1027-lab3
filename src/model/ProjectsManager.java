package model;

import exception.TitleNotUniqueException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The main class responsible for managing all {@code Project} objects in the application.
 * It serves as a container and is responsible for ensuring unique IDs and titles
 * for the projects. The class is serializable for file handling.
 */

public class ProjectsManager implements Serializable {
        private final List<Project> projects;
        private int nextProjectId;

    /**
     * Creates a new instance of {@code ProjectsManager} with an empty list of projects
     * and sets the next project ID to 1.
     */

        public ProjectsManager() {
            this.projects = new ArrayList<>();
            this.nextProjectId = 1;
        }

    /**
     * Returns a copy of the list of all projects managed by the manager.
     *
     * @return A {@code List<Project>} containing all projects.
     */

        public List<Project> getProjects() {
            List<Project> projectCopy = new ArrayList<>(this.projects);
            return projectCopy;
        }

    /**
     * Sets the list of projects and updates {@code nextProjectId} based on the
     * highest ID in the incoming list.
     * This method is primarily used for deserialization.
     *
     * @param incomingProjects The list of projects to set.
     */

        public void setProjects(List<Project> incomingProjects) {
            projects.clear();
            projects.addAll(incomingProjects);

            nextProjectId = incomingProjects.stream()
                    .mapToInt(Project::getId)
                    .max()
                    .orElse(0)
                    + 1;
        }

    /**
     * Checks if a given project title is unique among the existing projects.
     * The comparison is case-insensitive.
     *
     * @param title The title to check.
     * @return {@code true} if the title is unique, otherwise {@code false}.
     */

        public boolean isTitleUnique(String title){
            return projects.stream()
                    .noneMatch(project-> project.getTitle().equalsIgnoreCase(title));
        }

    /**
     * Adds a new project to the manager. The title must be unique.
     * The new project is assigned the next available unique ID.
     *
     * @param title The title of the project.
     * @param description The description of the project.
     * @return The newly created {@code Project} object.
     * @throws TitleNotUniqueException If a project with the same title already exists.
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
     * Removes a specified project from the manager.
     *
     * @param project The project to be removed.
     */

        public void removeProject(Project project){
            projects.remove(project);
        }

    /**
     * Searches for a project based on its unique ID.
     *
     * @param id The ID of the project to find.
     * @return The found {@code Project} object, or {@code null} if no project
     * with the specified ID was found.
     */

        public Project getProjectById(int id){
            return projects.stream()
                    .filter(project-> project.getId() == id)
                    .findFirst().orElse(null);
        }

    /**
     * Searches for projects whose titles contain the given string.
     *
     * @param titleStr The string to match against the project titles.
     * @return A {@code List<Project>} of the matching projects.
     */

        public List<Project> findProjects(String titleStr){
            return projects.stream()
                    .filter(project -> project.getTitle().contains(titleStr))
                    .collect(Collectors.toList());
        }

    /**
     * Returns the highest ID among all projects.
     *
     * @return The highest project ID.
     * @throws IllegalStateException If no projects are found in the manager.
     */

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
