package me.remil.notes.controller;

import me.remil.notes.dto.receive.TodosDto;
import me.remil.notes.dto.send.TodoTitleDto;
import me.remil.notes.exception.BadParameterException;
import me.remil.notes.jwt.util.JwtTokenUtil;
import me.remil.notes.service.todos.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"}, allowCredentials = "true")
@RequestMapping("/api")
public class TodosController {

    final private TodosService todosService;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TodosController(TodosService todosService) {
        this.todosService = todosService;
    }

    @PostMapping("/todos")
    public void saveTodo(@RequestHeader("cookie") String token, @RequestBody TodosDto todos) {
        token = token.substring(6);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.validateBeforeSaveOrUpdate(todos, username, TodosService.ACTIONS.SAVE);
    }

    @GetMapping("/todos/page/{record}/{count}")
    public List<TodoTitleDto> fetchTodoTitles(@PathVariable String record,
                                              @PathVariable String count,
                                              @RequestHeader("cookie")
                                              String token
                                              ) {
        token = token.substring(6);
        int recordNumber, recordCount;
        try {
            recordNumber = Integer.parseInt(record);
            recordCount = Integer.parseInt(count);
        } catch(NumberFormatException e) {
            throw new BadParameterException("Record number and count must be a number");
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        return todosService.fetchTodoTitles(username, recordNumber, recordCount);
    }

    @GetMapping("/todos/{todoId}")
    public TodosDto fetchById(@RequestHeader("cookie") String token, @PathVariable String todoId) {
        token = token.substring(6);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return todosService.fetchById(todoId, username);
    }

    @PutMapping("/todos")
    public void updateTodo(@RequestHeader("cookie") String token, @RequestBody TodosDto todos) {
        token = token.substring(6);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.validateBeforeSaveOrUpdate(todos, username, TodosService.ACTIONS.UPDATE);
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodoById(@RequestHeader("cookie") String token, @PathVariable String todoId) {
        token = token.substring(6);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.deleteTodo(todoId, username);
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
