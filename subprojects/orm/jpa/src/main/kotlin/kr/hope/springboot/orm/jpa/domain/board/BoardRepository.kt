package kr.hope.springboot.orm.jpa.domain.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Int>, BoardDslRepository
