package lukas.sivickas.uninote.helpers;

public class Module {

    private int id;
    private String name;
    private String code;
    private String lead;

    public Module(int id, String name, String code, String lead) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.lead = lead;
    }

    public Module(String name, String code, String lead) {
        this.id = -1;
        this.name = name;
        this.code = code;
        this.lead = lead;
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
