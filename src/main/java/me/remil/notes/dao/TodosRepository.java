package me.remil.notes.dao;

import me.remil.notes.dto.send.TodoTitleDto;
import me.remil.notes.entity.Todos;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodosRepository extends PagingAndSortingRepository<Todos, Integer> {

    @Query("select username from Todos where todoId=:todoId")
    String fetchUserNameByTodoId(@Param("todoId") String todoId);

    @Query("select todoId,title from Todos where username=:username")
    List<Object[]> fetchNoteIdAndTitles(@Param("username") String username, Pageable pageable);

    boolean existsByTodoId(String todoId);

    void deleteByTodoId(String todoId);
}
