package com.example.releasetracker.repository;

import com.example.releasetracker.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {

    @Query("SELECT r FROM ReleaseEntity r WHERE "
            + "r.name = :name "
            + "AND r.status = :status "
            + "AND r.releaseDate = :releaseDate")
    List<ReleaseEntity> findAll(@Param("name") String name,
                                @Param("status") String status,
                                @Param("releaseDate")Date date);
}
