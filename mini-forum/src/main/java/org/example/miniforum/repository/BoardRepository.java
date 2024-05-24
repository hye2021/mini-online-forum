package org.example.miniforum.repository;

import org.example.miniforum.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long>{
}
