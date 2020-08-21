package me.remil.notes.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "todo_items")
@IdClass(TodoItemId.class)
public class TodoItem implements Serializable {

    private static final long serialVersionUID = -6894319459754701672L;
    @Id
    @Column(name = "todo_id", nullable = false)
    private String todoId;

    @Id
    @Column(name = "todo_index", nullable = false)
    private int todoIndex;

    @Column(name = "todo_item", nullable = false)
    private String todoItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", referencedColumnName = "todo_id", insertable = false, updatable = false)
    private Todos todo;

    public TodoItem() {
    }

    public TodoItem(String todoId, int todoIndex, String todoItem, Todos todo) {
        this.todoId = todoId;
        this.todoIndex = todoIndex;
        this.todoItem = todoItem;
        this.todo = todo;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public int getTodoIndex() {
        return todoIndex;
    }

    public void setTodoIndex(int todoIndex) {
        this.todoIndex = todoIndex;
    }

    public String getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(String todoItem) {
        this.todoItem = todoItem;
    }

    public Todos getTodo() {
        return todo;
    }

    public void setTodo(Todos todo) {
        this.todo = todo;
    }
}
