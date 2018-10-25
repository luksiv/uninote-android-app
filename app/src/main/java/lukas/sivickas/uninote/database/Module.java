package lukas.sivickas.uninote.database;

public class Module {

    private int id;
    private String name;
    private String code;
    private String lead;
    private Assignment nextAssignment;

    public Module(int id, String name, String code, String lead) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.lead = lead;
        this.nextAssignment = null;
    }

    public Module(int id, String name, String code, String lead, Assignment nextAssignment) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.lead = lead;
        this.nextAssignment = nextAssignment;
    }

    public Module(String name, String code, String lead) {
        this.id = -1;
        this.name = name;
        this.code = code;
        this.lead = lead;
        this.nextAssignment = null;
    }

    public Module(String name, String code, String lead, Assignment nextAssignment) {
        this.id = -1;
        this.name = name;
        this.code = code;
        this.lead = lead;
        this.nextAssignment = nextAssignment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getLead() {
        return lead;
    }

    public Assignment getNextAssignment() {
        return nextAssignment;
    }

    public boolean hasNextAssignment(){
        return nextAssignment != null;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", lead='" + lead + '\'' +
                '}';
    }
}
