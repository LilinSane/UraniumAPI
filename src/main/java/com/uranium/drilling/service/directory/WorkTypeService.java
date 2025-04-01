package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.WorkTypeDTO;
import com.uranium.drilling.entity.directory.WorkType;
import com.uranium.drilling.entity.directory.WorkDirection;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.WorkTypeRepository;
import com.uranium.drilling.repository.directory.WorkDirectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTypeService {
    private final WorkTypeRepository workTypeRepository;
    private final WorkDirectionRepository workDirectionRepository;

    public WorkTypeService(WorkTypeRepository workTypeRepository, WorkDirectionRepository workDirectionRepository) {
        this.workTypeRepository = workTypeRepository;
        this.workDirectionRepository = workDirectionRepository;
    }

    public List<WorkType> getAllWorkTypes() {
        return workTypeRepository.findAll();
    }

    public Page<WorkType> getAllWorkTypesByPage(Pageable pageable) {
        return workTypeRepository.findAll(pageable);
    }

    public WorkType getWorkTypeById(Long id) {
        return workTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип работы с кодом " + id + " не найден"));
    }

    public WorkType createWorkType(WorkTypeDTO workTypeDTO) {
        WorkDirection direction = workDirectionRepository.findById(workTypeDTO.getDirectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Направление работы с кодом " + workTypeDTO.getDirectionId() + " не найдено"));

        WorkType workType = new WorkType();
        workType.setName(workTypeDTO.getName());
        workType.setWorkDirection(direction);

        return workTypeRepository.save(workType);
    }

    public WorkType updateWorkType(Long id, WorkTypeDTO workTypeDTO) {
        WorkType workType = workTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тип работы с кодом " + id + " не найден"));

        WorkDirection direction = workDirectionRepository.findById(workTypeDTO.getDirectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Направление работы с кодом " + workTypeDTO.getDirectionId() + " не найдено"));

        workType.setName(workTypeDTO.getName());
        workType.setWorkDirection(direction);

        return workTypeRepository.save(workType);
    }

    public void deleteWorkType(Long id) {
        if (!workTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Тип работы с кодом " + id + " не найден");
        }

        if (workTypeRepository.hasLinkedWorkSubTypes(id)) {
            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
        }

        workTypeRepository.deleteById(id);
    }
}
