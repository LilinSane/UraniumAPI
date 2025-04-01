package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.DrillHoleTypeDTO;
import com.uranium.drilling.entity.directory.DrillHoleType;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.repository.directory.DrillHoleTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrillHoleTypeService {
    private final DrillHoleTypeRepository drillHoleTypeRepository;

    public DrillHoleTypeService(DrillHoleTypeRepository drillHoleTypeRepository) {
        this.drillHoleTypeRepository = drillHoleTypeRepository;
    }

    public List<DrillHoleType> getAllDrillHoleTypes() {
        return drillHoleTypeRepository.findAll();
    }

    public Page<DrillHoleType> getAllDrillHoleTypesByPage(Pageable pageable) {
        return drillHoleTypeRepository.findAll(pageable);
    }

    public DrillHoleType getDrillHoleTypeById(Long id) {
        return drillHoleTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));
    }

    public DrillHoleType createDrillHoleType(DrillHoleTypeDTO drillHoleTypeDTO) {
        DrillHoleType drillHoleType = new DrillHoleType();
        drillHoleType.setName(drillHoleTypeDTO.getName());
        return drillHoleTypeRepository.save(drillHoleType);
    }

    public DrillHoleType updateDrillHoleType(Long id, DrillHoleTypeDTO drillHoleTypeDTO) {
        DrillHoleType drillHoleType = drillHoleTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));

        drillHoleType.setName(drillHoleTypeDTO.getName());
        return drillHoleTypeRepository.save(drillHoleType);
    }

    public void deleteDrillHoleType(Long id) {
        if (!drillHoleTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Объект с кодом " + id + " не найден");
        }

        if (drillHoleTypeRepository.hasLinkedDrillHoles(id)) {
            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
        }

        drillHoleTypeRepository.deleteById(id);
    }
}

