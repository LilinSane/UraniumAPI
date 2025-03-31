package com.uranium.drilling.service;

import com.uranium.drilling.dto.DrillHoleDTO;
import com.uranium.drilling.entity.Area;
import com.uranium.drilling.entity.DrillHole;
import com.uranium.drilling.entity.DrillHoleType;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.repository.AreaRepository;
import com.uranium.drilling.repository.DrillHoleRepository;
import com.uranium.drilling.repository.DrillHoleTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrillHoleService {
    private final DrillHoleRepository drillHoleRepository;
    private final AreaRepository areaRepository;
    private final DrillHoleTypeRepository drillHoleTypeRepository;

    public DrillHoleService(DrillHoleRepository drillHoleRepository, AreaRepository areaRepository, DrillHoleTypeRepository drillHoleTypeRepository) {
        this.drillHoleRepository = drillHoleRepository;
        this.areaRepository = areaRepository;
        this.drillHoleTypeRepository = drillHoleTypeRepository;
    }

    public List<DrillHole> getAllDrillHoles(Boolean isActiveOnly) {
        if (isActiveOnly != null && isActiveOnly) {
            return drillHoleRepository.findByIsActiveTrue();
        }
        return drillHoleRepository.findAll();
    }

    public Page<DrillHole> getAllDrillHolesByPage(Pageable pageable) {
        return drillHoleRepository.findAll(pageable);
    }

    public DrillHole getDrillHoleById(Long id) {
        return drillHoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Скважина с кодом " + id + " не найдена"));
    }

    public DrillHole createDrillHole(DrillHoleDTO drillHoleDTO) {
        Area area = areaRepository.findById(drillHoleDTO.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Участок с кодом " + drillHoleDTO.getAreaId() + " не найден"));

        DrillHoleType drillHoleType = drillHoleTypeRepository.findById(drillHoleDTO.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид скважины с кодом " + drillHoleDTO.getTypeId() + " не найден"));

        DrillHole drillHole = DrillHole.builder()
                .systemId(drillHoleDTO.getSystemId())
                .name(drillHoleDTO.getName())
                .isActive(drillHoleDTO.getIsActive())
                .area(area)
                .drillHoleType(drillHoleType)
                .build();

        return drillHoleRepository.save(drillHole);
    }

    public DrillHole updateDrillHole(Long id, DrillHoleDTO drillHoleDTO) {
        DrillHole drillHole = drillHoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Скважина с кодом " + id + " не найдена"));

        Area area = areaRepository.findById(drillHoleDTO.getAreaId())
                .orElseThrow(() -> new ResourceNotFoundException("Участок с кодом " + drillHoleDTO.getAreaId() + " не найден"));

        DrillHoleType drillHoleType = drillHoleTypeRepository.findById(drillHoleDTO.getTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид скважины с кодом " + drillHoleDTO.getTypeId() + " не найден"));

        drillHole.setSystemId(drillHoleDTO.getSystemId());
        drillHole.setName(drillHoleDTO.getName());
        drillHole.setIsActive(drillHoleDTO.getIsActive());
        drillHole.setArea(area);
        drillHole.setDrillHoleType(drillHoleType);

        return drillHoleRepository.save(drillHole);
    }

    public void deleteDrillHole(Long id) {
        if (!drillHoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Скважина с кодом " + id + " не найдена");
        }
        //TODO
//        if (drillHoleRepository.hasLinkedEntities(id)) {
//            throw new ForeignKeyConstraintException("Невозможно удалить скважину, так как на нее ссылаются другие объекты");
//        }

        drillHoleRepository.deleteById(id);
    }
}
