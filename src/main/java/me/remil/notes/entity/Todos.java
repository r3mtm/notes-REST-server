package me.remil.notes.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Todos {

    @Id
    @Column(name = "todo_id", nullable = false)
    private String todoId;

    @Column(name = "todo_title", nullable = false)
    private String todoTitle;

    @Column(name = "user_id", nullable = false)
    private String username;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> todoItems;

    public Todos() {
    }

    public Todos(String todoId, String todoTitle, String username, Timestamp lastUpdated, List<TodoItem> todoItems) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.username = username;
        this.lastUpdated = lastUpdated;
        this.todoItems = todoItems;
    }

    public void addTodoItems(TodoItem todoItem) {
        if (todoItem != null) {
            if (todoItems == null) {
                todoItems = new ArrayList<>();
            }
            todoItem.setTodo(this);
            todoItems.add(todoItem);
        }
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

    public void setTodoTitle(String title) {
        this.todoTitle = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userId) {
        this.username = userId;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItem> todoItem) {
        this.todoItems = todoItem;
    }

    @Override
    public String toString() {
        return "Todos{" +
                "todoId='" + todoId + '\'' +
                ", title='" + todoTitle + '\'' +
                ", userId='" + username + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", todoItems=" + todoItems +
                '}';
    }
}
