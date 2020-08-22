package me.remil.notes.dao;

import me.remil.notes.entity.Notes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotesRepository extends PagingAndSortingRepository<Notes, String> {

    @Query("select noteId, noteHeading from Notes where userId=:userId")
    List<Object[]> fetchAllTitleAndIdByUserId(@Param("userId") String userId, Pageable pageable);

    @Query("select userId from Notes where noteId=:noteId")
    String fetchUserIdByNoteId(@Param("noteId") String noteId);

    Notes findByNoteId(String noteId);
    boolean existsByNoteId(String noteId);
}
