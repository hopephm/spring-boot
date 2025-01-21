package kr.hope.springboot.jpa.domain.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Int>, BoardDslRepository
