package sk.kabum.kabumcare.Objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import sk.kabum.kabumcare.Enums.TaskType;
import sk.kabum.kabumcare.Enums.WorkoutType;

@DatabaseTable
public class Task {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private long userID;
    @DatabaseField
    private String dueDate;
    @DatabaseField
    private TaskType type;
    @DatabaseField
    private String name;
    @DatabaseField
    private WorkoutType workoutType;

    public Task(long userID, String dueDate, TaskType type, String name, WorkoutType workoutType) {
        this.userID = userID;
        this.dueDate = dueDate;
        this.type = type;
        this.name = name;
        this.workoutType = workoutType;

    }
    public Task() {

    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userID=" + userID +
                ", dueDate=" + dueDate +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
