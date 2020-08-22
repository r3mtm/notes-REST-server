package me.remil.notes.controller;

import me.remil.notes.dto.receive.TodosDTO;
import me.remil.notes.jwt.util.JwtTokenUtil;
import me.remil.notes.service.todos.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"})
@RequestMapping("/api")
public class TodosController {

    final private TodosService todosService;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TodosController(TodosService todosService) {
        this.todosService = todosService;
    }

    @PostMapping("/todos")
    public void saveTodo(@RequestHeader("Authorization") String token, @RequestBody TodosDTO todos) {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.saveTodos(todos, username);
    }

    @PutMapping("/todos")
    public void updateTodo(@RequestHeader("Authorization") String token, @RequestBody TodosDTO todos) {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.updateTodos(todos, username);
    }

    @DeleteMapping("/todos/{todoId}")
    public void delteTodoById(@RequestHeader("Authorization") String token, @PathVariable String todoId) {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        todosService.deleteTodo(todoId, username);
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }
}
