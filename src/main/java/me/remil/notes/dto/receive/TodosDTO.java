package me.remil.notes.dto.receive;

import java.sql.Timestamp;
import java.util.List;

public class TodosDTO {
    private String todoId;
    private String todoTitle;
    private String username;
    private Timestamp lastUpdated;
    private List<TodoItemDTO> todoItems;

    public TodosDTO(String todoId, String todoTitle, String username, Timestamp lastUpdated, List<TodoItemDTO> todoItems) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.username = username;
        this.lastUpdated = lastUpdated;
        this.todoItems = todoItems;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<TodoItemDTO> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItemDTO> todoItems) {
        this.todoItems = todoItems;
    }

    @Override
    public String toString() {
        return "TodosDTO{" +
                "todoId='" + todoId + '\'' +
                ", todoTitle='" + todoTitle + '\'' +
                ", username='" + username + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", todoItems=" + todoItems +
                '}';
    }
}
