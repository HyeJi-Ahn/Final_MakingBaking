package com.ezen.makingbaking.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ezen.makingbaking.entity.Board;

@Transactional
public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 카테고리 코드로 리스트 검색
	Page<Board> findByCateCode(int cateCode, Pageable pageable);

	@Modifying
	@Query(value="UPDATE T_MB_BOARD SET BOARD_CNT = BOARD_CNT + 1 WHERE BOARD_NO = :boardNo", nativeQuery=true)
	void updateBoardCnt(int boardNo);
	
	
	
}
