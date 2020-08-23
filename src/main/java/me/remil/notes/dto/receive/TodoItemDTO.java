package me.remil.notes.dto.receive;

public class TodoItemDTO {
    private int todoIndex;
    private String todoItem;
    private boolean strike;

    public TodoItemDTO() {
    }

    public TodoItemDTO(int todoIndex, String todoItem, boolean strike) {
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
        return "TodoItemDTO{" +
                "todoIndex=" + todoIndex +
                ", todoItem='" + todoItem + '\'' +
                ", strike=" + strike +
                '}';
    }
}
