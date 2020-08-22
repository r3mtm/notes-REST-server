package me.remil.notes.service.todos;

import me.remil.notes.dto.receive.TodosDTO;
import me.remil.notes.entity.Todos;

public interface TodosService {
    enum ACTIONS {SAVE, UPDATE};

    void saveTodos(Todos todos);

    void updateTodos(Todos todos, String usernameFromToken);

    void deleteTodo(String todoId, String usernameFromToken);

    void validateBeforeSaveOrUpdate(TodosDTO todosDTO, String usernameFromToken, ACTIONS type);
}
