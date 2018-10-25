package lukas.sivickas.uninote.database;

import java.util.Date;

public class Assignment {

    private int id;
    private Module module;
    private Date dueDate;
    private String title;
    private String description;
    private boolean isDone;

    public Assignment(int id, Module moduleId, Date dueDate, String title, String description, boolean isDone) {
        this.id = id;
        this.module = moduleId;
        this.dueDate = dueDate;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }

    public Assignment(Module moduleId, Date dueDate, String title, String description, boolean isDone) {
        this.id = -1;
        this.module = moduleId;
        this.dueDate = dueDate;
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }


    public int getId() {
        return id;
    }

    public int getModuleId() {
        return module.getId();
    }

    public String getModuleDesc() {
        return "[" + module.getCode() + "] " + module.getName();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Module getModule() {
        return module;
    }

    public long getDueDateTimestamp() {
        return dueDate.getTime();
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", moduleId=" + module +
                ", dueDate=" + dueDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
