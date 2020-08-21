package me.remil.notes.service.todos;

import me.remil.notes.dto.receive.TodosDTO;

public interface TodosService {
    void saveTodos(TodosDTO todos, String username);

    void updateTodos(TodosDTO todosDto, String username);

    void deleteTodo(String todoId, String username);
}
