package me.remil.notes.service.todos;

import me.remil.notes.dto.receive.TodosDto;
import me.remil.notes.dto.send.TodoTitleDto;
import me.remil.notes.entity.Todos;

import java.util.List;

public interface TodosService {
    enum ACTIONS {SAVE, UPDATE};

    void saveTodos(Todos todos);

    void updateTodos(Todos todos, String usernameFromToken);

    void deleteTodo(String todoId, String usernameFromToken);

    void validateBeforeSaveOrUpdate(TodosDto todosDTO, String usernameFromToken, ACTIONS type);

    List<TodoTitleDto> fetchTodoTitles(String username, int recordNumber, int recordCount);

    TodosDto fetchById(String noteId, String username);
}
