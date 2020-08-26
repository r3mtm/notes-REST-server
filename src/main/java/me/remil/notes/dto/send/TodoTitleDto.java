package me.remil.notes.dto.send;

public class TodoTitleDto {
    private String todoId;
    private String todoTitle;

    public TodoTitleDto() {
    }

    public TodoTitleDto(String todoId, String todoTitle) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
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

    @Override
    public String toString() {
        return "TodoTitleDto{" +
                "todoId='" + todoId + '\'' +
                ", todoTitle='" + todoTitle + '\'' +
                '}';
    }
}
