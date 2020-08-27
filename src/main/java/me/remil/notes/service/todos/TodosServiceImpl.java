package me.remil.notes.service.todos;

import me.remil.notes.dao.TodosRepository;
import me.remil.notes.dto.receive.TodosDTO;
import me.remil.notes.dto.receive.TodoItemDTO;
import me.remil.notes.dto.send.TodoTitleDto;
import me.remil.notes.entity.TodoItem;
import me.remil.notes.entity.Todos;
import me.remil.notes.exception.*;
import me.remil.notes.service.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public void saveTodos(Todos todos) {
        todosRepository.save(todos);
    }

    @Override
    public void updateTodos(Todos todos, String usernameFromToken) {
        String usernameFromDb = todosRepository.fetchUserNameByTodoId(todos.getTodoId());
        if(!usernameFromDb.equals(usernameFromToken)) {
            throw new UnauthorizedRequestException("UNAUTHORIZED: Request cannot be fulfilled");
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

    @Override
    public void validateBeforeSaveOrUpdate(TodosDTO todosDto, String usernameFromToken, ACTIONS type) {
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
        if (todoItems == null || todoItems.size() == 0) {
            throw new MissingItemException("No todos found");
        }

        Set<Integer> checkIndex = new HashSet<>();
        Todos todos = new Todos();
        todos.setTodoId(todoId);
        todos.setTodoTitle(title);
        todos.setUsername(username);
        todos.setLastUpdated(lastUpdated);
        todosDto.getTodoItems().forEach(todoItemDTO -> {
            checkIndex.add(todoItemDTO.getTodoIndex());
            if (todoItemDTO.getTodoItem() == null) {
                throw new MissingItemException("Missing essential fields in todo item");
            }
            TodoItem todoItem = new TodoItem(todoId, todoItemDTO.getTodoIndex(), todoItemDTO.getTodoItem(), todoItemDTO.isStrike(), todos);
            todos.addTodoItems(todoItem);
        });

        // Checking if todo items has indexes from 0 to size-1
        for (int index = 0; index < todoItems.size(); ++index) {
            if(!checkIndex.contains(index)) {
                throw new BadParameterException("Malformed request");
            }
        }
        boolean todoExists = todosRepository.existsByTodoId(todoId);
        if (type.equals(ACTIONS.SAVE)) {
            if (todoExists) {
                throw new IdAlreadyExistsException("Note with id "+todoId+" already exists");
            }
            saveTodos(todos);
        } else if (!todoExists) {
            throw new NotFoundException("No todo found with id "+todoId);
        } else {
            updateTodos(todos, usernameFromToken);
        }
    }

    @Override
    public List<TodoTitleDto> fetchTodoTitles(String username, int recordNumber, int recordCount) {
        Pageable pageable = new OffsetBasedPageRequest(recordCount, recordNumber);
        List<Object[]> titles = todosRepository.fetchNoteIdAndTitles(username, pageable);
        List<TodoTitleDto> todoTitles = new ArrayList<>();
        titles.forEach(object -> {
            todoTitles.add(new TodoTitleDto((String)object[0], (String)object[1]));
        });
        return todoTitles;
    }

    @Override
    public TodosDTO fetchById(String todoId, String username) {
        Todos todos = todosRepository.findByTodoIdAndUsername(todoId, username);
        if (todos == null) {
            throw new NotFoundException("No todo found with the id "+todoId);
        }
        List<TodoItemDTO> todoItemsList = new ArrayList<>();
        todos.getTodoItems().forEach(todoItem -> {
            TodoItemDTO todoItemDTO = new TodoItemDTO(
                    todoItem.getTodoIndex(),
                    todoItem.getTodoItem(),
                    todoItem.isStrike());
            todoItemsList.add(todoItemDTO);
        });

        return new TodosDTO(
                todos.getTodoId(), todos.getTodoTitle(),
                todos.getUsername(), todos.getLastUpdated(),
                todoItemsList
        );
    }

}
