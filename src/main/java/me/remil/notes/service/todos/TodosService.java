package me.remil.notes.service.todos;

import me.remil.notes.dto.receive.TodosDTO;
import me.remil.notes.dto.send.TodoTitleDto;
import me.remil.notes.entity.Todos;

import java.util.List;

public interface TodosService {
    enum ACTIONS {SAVE, UPDATE};

    void saveTodos(Todos todos);

    void updateTodos(Todos todos, String usernameFromToken);

    void deleteTodo(String todoId, String usernameFromToken);

    void validateBeforeSaveOrUpdate(TodosDTO todosDTO, String usernameFromToken, ACTIONS type);

    List<TodoTitleDto> fetchTodoTitles(String username, int recordNumber, int recordCount);

    TodosDTO fetchById(String noteId, String username);
}
