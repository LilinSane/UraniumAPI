package com.uranium.drilling.service;

import com.uranium.drilling.dto.WorkDirectionDTO;
import com.uranium.drilling.entity.WorkDirection;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.repository.WorkDirectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkDirectionService {
    private final WorkDirectionRepository workDirectionRepository;

    public WorkDirectionService(WorkDirectionRepository workDirectionRepository) {
        this.workDirectionRepository = workDirectionRepository;
    }

    public List<WorkDirection> getAllWorkDirections() {
        return workDirectionRepository.findAll();
    }

    public Page<WorkDirection> getAllWorkDirectionsByPage(Pageable pageable) {
        return workDirectionRepository.findAll(pageable);
    }

    public WorkDirection getWorkDirectionById(Long id) {
        return workDirectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));
    }

    public WorkDirection createWorkDirection(WorkDirectionDTO workDirectionDTO) {
        WorkDirection workDirection = new WorkDirection();
        workDirection.setName(workDirectionDTO.getName());
        return workDirectionRepository.save(workDirection);
    }

    public WorkDirection updateWorkDirection(Long id, WorkDirectionDTO workDirectionDTO) {
        WorkDirection workDirection = workDirectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));

        workDirection.setName(workDirectionDTO.getName());
        return workDirectionRepository.save(workDirection);
    }

    public void deleteWorkDirection(Long id) {
        if (!workDirectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Объект с кодом " + id + " не найден");
        }

        if (workDirectionRepository.hasLinkedWorkTypes(id)) {
            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
        }

        workDirectionRepository.deleteById(id);
    }
}
