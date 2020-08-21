package me.remil.notes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private String title;

    @Column(name = "user_id", nullable = false)
    private String username;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoItem> todoItems;

    public Todos() {
    }

    public Todos(String todoId, String title, String username, Timestamp lastUpdated, List<TodoItem> todoItems) {
        this.todoId = todoId;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", title='" + title + '\'' +
                ", userId='" + username + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", todoItems=" + todoItems +
                '}';
    }
}
