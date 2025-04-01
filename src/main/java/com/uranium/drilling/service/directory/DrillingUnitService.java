package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.DrillingUnitDTO;
import com.uranium.drilling.entity.directory.DrillingUnit;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.DrillingUnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrillingUnitService {
    private final DrillingUnitRepository drillingUnitRepository;

    public DrillingUnitService(DrillingUnitRepository drillingUnitRepository) {
        this.drillingUnitRepository = drillingUnitRepository;
    }

    public List<DrillingUnit> getAllDrillingUnits() {
        return drillingUnitRepository.findAll();
    }

    public Page<DrillingUnit> getAllDrillingUnitsByPage(Pageable pageable) {
        return drillingUnitRepository.findAll(pageable);
    }

    public DrillingUnit getDrillingUnitById(Long id) {
        return drillingUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));
    }

    public DrillingUnit createDrillingUnit(DrillingUnitDTO drillingUnitDTO) {
        DrillingUnit drillingUnit = new DrillingUnit();
        drillingUnit.setName(drillingUnitDTO.getName());
        drillingUnit.setInventoryNumber(drillingUnitDTO.getInventoryNumber());
        drillingUnit.setIsActive(drillingUnitDTO.getIsActive());
        return drillingUnitRepository.save(drillingUnit);
    }

    public DrillingUnit updateDrillingUnit(Long id, DrillingUnitDTO drillingUnitDTO) {
        DrillingUnit drillingUnit = drillingUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));

        drillingUnit.setName(drillingUnitDTO.getName());
        drillingUnit.setInventoryNumber(drillingUnitDTO.getInventoryNumber());
        drillingUnit.setIsActive(drillingUnitDTO.getIsActive());
        return drillingUnitRepository.save(drillingUnit);
    }

    public void deleteDrillingUnit(Long id) {
        if (!drillingUnitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Объект с кодом " + id + " не найден");
        }
        //TODO
//        if (drillingUnitRepository.hasLinkedDrillHoles(id)) {
//            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
//        }

        drillingUnitRepository.deleteById(id);
    }
}
