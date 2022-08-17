package com.example.releasetracker.service.impl;

import com.example.releasetracker.entity.ReleaseEntity;
import com.example.releasetracker.repository.ReleaseRepository;
import com.example.releasetracker.service.ReleaseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {

    private ReleaseRepository releaseRepository;

    public ReleaseServiceImpl(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public List<ReleaseEntity> findAll(String name, String status, Date releaseDate) {
        return this.releaseRepository.findAll(name, status, releaseDate);
    }

    @Override
    public List<ReleaseEntity> findAll() {
        return this.releaseRepository.findAll();
    }

    @Override
    public ReleaseEntity findById(Long id) {
        return this.releaseRepository.findById(id).get();
    }

    @Override
    public ReleaseEntity save(ReleaseEntity releaseEntity) {
        this.releaseRepository.save(releaseEntity);
        return releaseEntity;
    }

    @Override
    public void delete(Long id) {
        this.releaseRepository.deleteById(id);
    }
}
