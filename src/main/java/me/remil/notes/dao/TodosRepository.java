package me.remil.notes.dao;

import me.remil.notes.entity.Todos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TodosRepository extends PagingAndSortingRepository<Todos, Integer> {

    @Query("select username from Todos where todoId=:todoId")
    String fetchUserNameByTodoId(@Param("todoId") String todoId);

    boolean existsByTodoId(String todoId);

    void deleteByTodoId(String todoId);
}
