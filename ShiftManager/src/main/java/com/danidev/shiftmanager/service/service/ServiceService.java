package com.danidev.shiftmanager.service.service;

import com.danidev.shiftmanager.service.entity.ServiceEntity;
import com.danidev.shiftmanager.service.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    public ServiceEntity create(ServiceEntity service) {
        return repository.save(service);
    }

    public List<ServiceEntity> getAllActive() {
        return repository.findByActiveTrue();
    }

    public ServiceEntity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }
}
