package me.remil.notes.entity;

import java.io.Serializable;
import java.util.Objects;

public class TodoItemId implements Serializable {
    private static final long serialVersionUID = -6723227929309590899L;

    private String todoId;

    private int todoIndex;

    public TodoItemId() {
    }

    public TodoItemId(String todoId, int todoIndex) {
        this.todoId = todoId;
        this.todoIndex = todoIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItemId that = (TodoItemId) o;
        return todoIndex == that.todoIndex &&
                Objects.equals(todoId, that.todoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todoId, todoIndex);
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

    public void setTodoIndex(int index) {
        this.todoIndex = index;
    }

    @Override
    public String toString() {
        return "TodoItemId{" +
                "todoId='" + todoId + '\'' +
                ", todoIndex=" + todoIndex +
                '}';
    }
}
