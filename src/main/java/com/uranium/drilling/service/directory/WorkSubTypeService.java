package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.WorkSubTypeDTO;
import com.uranium.drilling.entity.directory.WorkSubType;
import com.uranium.drilling.entity.directory.WorkType;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.WorkSubTypeRepository;
import com.uranium.drilling.repository.directory.WorkTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkSubTypeService {
    private final WorkSubTypeRepository workSubTypeRepository;
    private final WorkTypeRepository workTypeRepository;

    public WorkSubTypeService(WorkSubTypeRepository workSubTypeRepository, WorkTypeRepository workTypeRepository) {
        this.workSubTypeRepository = workSubTypeRepository;
        this.workTypeRepository = workTypeRepository;
    }

    public List<WorkSubType> getAllWorkSubTypes() {
        return workSubTypeRepository.findAll();
    }

    public Page<WorkSubType> getAllWorkSubTypesByPage(Pageable pageable) {
        return workSubTypeRepository.findAll(pageable);
    }

    public WorkSubType getWorkSubTypeById(Long id) {
        return workSubTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Подвид работы с кодом " + id + " не найден"));
    }

    public WorkSubType createWorkSubType(WorkSubTypeDTO workSubTypeDTO) {
        WorkType workType = workTypeRepository.findById(workSubTypeDTO.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид работы с кодом " + workSubTypeDTO.getTypeId() + " не найден"));

        WorkSubType workSubType = new WorkSubType();
        workSubType.setName(workSubTypeDTO.getName());
        workSubType.setWorkType(workType);

        return workSubTypeRepository.save(workSubType);
    }

    public WorkSubType updateWorkSubType(Long id, WorkSubTypeDTO workSubTypeDTO) {
        WorkSubType workSubType = workSubTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Подвид работы с кодом " + id + " не найден"));

        WorkType workType = workTypeRepository.findById(workSubTypeDTO.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид работы с кодом " + workSubTypeDTO.getTypeId() + " не найден"));

        workSubType.setName(workSubTypeDTO.getName());
        workSubType.setWorkType(workType);

        return workSubTypeRepository.save(workSubType);
    }

    public void deleteWorkSubType(Long id) {
        if (!workSubTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Подвид работы с кодом " + id + " не найден");
        }
        //TODO
//        if (workSubTypeRepository.hasLinkedEntities(id)) {
//            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылаются другие объекты");
//        }

        workSubTypeRepository.deleteById(id);
    }
}
