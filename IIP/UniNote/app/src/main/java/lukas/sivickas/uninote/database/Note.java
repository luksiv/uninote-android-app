package lukas.sivickas.uninote.database;

import java.util.Date;

public class Note {

    int id;
    int moduleId;
    Date creationDate;
    Date lastEditDate;
    String text;


    public Note(int id, int moduleId, Date creationDate, String text) {
        this.id = id;
        this.moduleId = moduleId;
        this.creationDate = creationDate;
        this.text = text;
    }

    public Note(int moduleId, Date creationDate, String text) {
        this.id = -1;
        this.moduleId = moduleId;
        this.creationDate = creationDate;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", moduleId=" + moduleId +
                ", creationDate=" + creationDate +
                ", text='" + text + '\'' +
                '}';
    }
}
