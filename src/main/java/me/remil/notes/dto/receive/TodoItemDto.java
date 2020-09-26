package me.remil.notes.dto.receive;

public class TodoItemDto {
    private int todoIndex;
    private String todoItem;
    private boolean strike;

    public TodoItemDto() {
    }

    public TodoItemDto(int todoIndex, String todoItem, boolean strike) {
        this.todoIndex = todoIndex;
        this.todoItem = todoItem;
        this.strike = strike;
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

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }

    @Override
    public String toString() {
        return "TodoItemDto{" +
                "todoIndex=" + todoIndex +
                ", todoItem='" + todoItem + '\'' +
                ", strike=" + strike +
                '}';
    }
}