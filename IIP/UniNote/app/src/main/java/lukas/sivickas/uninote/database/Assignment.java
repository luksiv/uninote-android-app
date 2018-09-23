package lukas.sivickas.uninote.database;

import java.util.Date;

public class Assignment {

    private int id;
    private int moduleId;
    private Date dueDate;
    private String title;
    private String description;
    private boolean reminderOn;

    public Assignment(int id, int moduleId, Date dueDate, String title, String description, boolean reminderOn) {
        this.id = id;
        this.moduleId = moduleId;
        this.dueDate = dueDate;
        this.title = title;
        this.description = description;
        this.reminderOn = reminderOn;
    }

    public Assignment(int id, int moduleId, Date dueDate, String description) {
        this.id = id;
        this.moduleId = moduleId;
        this.dueDate = dueDate;
        this.description = description;
    }

    public Assignment(int moduleId, Date dueDate, String description) {
        this.id = -1;
        this.moduleId = moduleId;
        this.dueDate = dueDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public boolean isReminderOn() {
        return reminderOn;
    }

    public int getId() {
        return id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                '}';
    }
}
