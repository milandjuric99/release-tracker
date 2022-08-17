package com.example.releasetracker.service;

import com.example.releasetracker.entity.ReleaseEntity;

import java.util.Date;
import java.util.List;

public interface ReleaseService {

    List<ReleaseEntity> findAll();
    List<ReleaseEntity> findAll(String name, String status, Date releaseDate);
    ReleaseEntity findById(Long id);
    ReleaseEntity save(ReleaseEntity releaseEntity);
    void delete(Long id);
}
