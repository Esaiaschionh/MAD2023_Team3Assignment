package sg.edu.np.mad.logintest;

public class Goal {
    private long id;
    private String name;
    private String description;
    private long dueDateMillis;
    private int progress;

    public Goal(String name, String description, long dueDateMillis, int progress) {
        this.name = name;
        this.description = description;
        this.dueDateMillis = dueDateMillis;
        this.progress = progress;
    }

    // Add the ID field to the constructor and getter/setter methods
    public Goal(long id, String name, String description, long dueDateMillis, int progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDateMillis = dueDateMillis;
        this.progress = progress;
    }

    // Getter and setter methods for ID
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public long getDueDateMillis(){
        return dueDateMillis;
    }
    public int getProgress() {
        return progress;
    }

}