package me.remil.notes.dto.receive;

public class TodoItemDTO {
    private int todoIndex;
    private String todoItem;


    public TodoItemDTO(int todoIndex, String todoItem) {
        this.todoIndex = todoIndex;
        this.todoItem = todoItem;
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

    @Override
    public String toString() {
        return "TodoItemDTO{" +
                "todoIndex=" + todoIndex +
                ", todoItem='" + todoItem + '\'' +
                '}';
    }
}
