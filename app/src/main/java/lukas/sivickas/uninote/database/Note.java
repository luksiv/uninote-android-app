package lukas.sivickas.uninote.database;

import java.util.Date;

public class Note {

    int id;
    Module module;
    Date creationDate;
    Date lastEditDate;
    String title;
    String text;

    public Note(int id, Module module, Date creationDate, Date lastEditDate, String title, String text) {
        this.id = id;
        this.module = module;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.title = title;
        this.text = text;
    }

    public Note(Module module, Date creationDate, Date lastEditDate, String title, String text) {
        this.id = -1;
        this.module = module;
        this.creationDate = creationDate;
        this.lastEditDate = lastEditDate;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Module getModule() {
        return module;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastEditDate() {
        return lastEditDate;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", module=" + module +
                ", creationDate=" + creationDate +
                ", lastEditDate=" + lastEditDate +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
