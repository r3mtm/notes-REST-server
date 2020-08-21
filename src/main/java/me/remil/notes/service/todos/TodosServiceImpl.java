package me.remil.notes.service.todos;

import me.remil.notes.dao.TodosRepository;
import me.remil.notes.dto.receive.TodosDTO;
import me.remil.notes.dto.receive.TodoItemDTO;
import me.remil.notes.entity.TodoItem;
import me.remil.notes.entity.Todos;
import me.remil.notes.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TodosServiceImpl implements TodosService {

    final private TodosRepository todosRepository;

    @Autowired
    public TodosServiceImpl(TodosRepository todosRepository) {
        this.todosRepository = todosRepository;
    }

    @Override
    public void saveTodos(TodosDTO todosDto, String usernameFromToken) {
        String todoId = todosDto.getTodoId();
        String title = todosDto.getTodoTitle();
        String username = todosDto.getUsername();
        Timestamp lastUpdated = todosDto.getLastUpdated();
        List<TodoItemDTO> todoItems = todosDto.getTodoItems();

        if (username == null) {
            throw new MissingItemException("Username missing in request");
        } else if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }

        if (todoId == null || todoId.trim().length() == 0) {
            throw new MissingItemException("Todo id is missing.");
        }
        if (title == null || title.trim().length() == 0) {
            throw new MissingItemException("Missing title from todo");
        }
        if(lastUpdated == null) {
            throw new MissingItemException("Missing last updated time");
        }
        if (todoItems.size() == 0) {
            throw new MissingItemException("No todos found");
        }

        if (todosRepository.existsByTodoId(todoId)) {
            throw new IdAlreadyExistsException("Todo with id "+todoId + " already exists in database");
        }

        Set<Integer> checkingIndexes = new HashSet<>();
        Todos todos = new Todos();
        todos.setTodoId(todoId);
        todos.setTitle(title);
        todos.setUsername(username);
        todos.setLastUpdated(lastUpdated);
        todosDto.getTodoItems().forEach(todoItemDTO -> {
            checkingIndexes.add(todoItemDTO.getTodoIndex());
            TodoItem todoItem = new TodoItem(todoId, todoItemDTO.getTodoIndex(), todoItemDTO.getTodoItem(), todos);
            todos.addTodoItems(todoItem);
        });

        for (int index = 0; index < todoItems.size(); ++index) {
            if(!checkingIndexes.contains(index)) {
                throw new BadParameterException("Malformed request");
            }
        }

        todosRepository.save(todos);
    }

    @Override
    public void updateTodos(TodosDTO todosDto, String usernameFromToken) {
        String todoId = todosDto.getTodoId();
        String title = todosDto.getTodoTitle();
        String username = todosDto.getUsername();
        Timestamp lastUpdated = todosDto.getLastUpdated();
        List<TodoItemDTO> todoItems = todosDto.getTodoItems();

        if (username == null) {
            throw new MissingItemException("Username missing in request");
        } else if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }

        if (todoId == null || todoId.trim().length() == 0) {
            throw new MissingItemException("Todo id is missing.");
        }
        if (title == null || title.trim().length() == 0) {
            throw new MissingItemException("Missing title from todo");
        }
        if(lastUpdated == null) {
            throw new MissingItemException("Missing last updated time");
        }
        if (todoItems.size() == 0) {
            throw new MissingItemException("No todos found");
        }

        if (todosRepository.existsByTodoId(todoId)) {
            if (!username.equals(todosRepository.fetchUserNameByTodoId(todoId))) {
                throw new UnauthorizedRequestException("Unauthorized: Request cannot be fulfilled");
            }
        } else {
            throw new MissingItemException("Todo with id "+ todoId + " not found");
        }

        Set<Integer> checkingIndexes = new HashSet<>();
        Todos todos = new Todos();
        todos.setTodoId(todoId);
        todos.setTitle(title);
        todos.setUsername(username);
        todos.setLastUpdated(lastUpdated);
        todosDto.getTodoItems().forEach(todoItemDTO -> {
            checkingIndexes.add(todoItemDTO.getTodoIndex());
            TodoItem todoItem = new TodoItem(todoId, todoItemDTO.getTodoIndex(), todoItemDTO.getTodoItem(), todos);
            todos.addTodoItems(todoItem);
        });

        /*
            Checking if todo items has indexes from 0 to size-1
         */
        for (int index = 0; index < todoItems.size(); ++index) {
            if(!checkingIndexes.contains(index)) {
                throw new BadParameterException("Malformed request");
            }
        }
        todosRepository.save(todos);
    }

    @Override
    @Transactional
    public void deleteTodo(String todoId, String usernameFromToken) {
        String username = todosRepository.fetchUserNameByTodoId(todoId);

        if (username == null) {
            throw new NotFoundException("Request failed. No Todo with id "+todoId+" exist for the user");
        }

        if (!username.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
        }
        todosRepository.deleteByTodoId(todoId);
    }
}
