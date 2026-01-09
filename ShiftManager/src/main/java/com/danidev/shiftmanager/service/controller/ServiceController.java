package com.danidev.shiftmanager.service.controller;

import com.danidev.shiftmanager.service.entity.ServiceEntity;
import com.danidev.shiftmanager.service.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@PreAuthorize("isAuthenticated()")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<ServiceEntity> create(@RequestBody ServiceEntity service) {
        return ResponseEntity.ok(serviceService.create(service));
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAll() {
        return ResponseEntity.ok(serviceService.getAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getById(id));
    }
}
