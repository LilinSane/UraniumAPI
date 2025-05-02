package com.uranium.drilling.service.drillingAct;

import com.uranium.drilling.dto.drillingAct.DetailDTO;
import com.uranium.drilling.entity.directory.DrillingUnit;
import com.uranium.drilling.entity.directory.WorkSubType;
import com.uranium.drilling.entity.drillingAct.Detail;
import com.uranium.drilling.entity.drillingAct.Header;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.DrillingUnitRepository;
import com.uranium.drilling.repository.directory.WorkSubTypeRepository;
import com.uranium.drilling.repository.drillingAct.DetailRepository;
import com.uranium.drilling.repository.drillingAct.HeaderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailService {
    private final DetailRepository detailRepository;
    private final HeaderRepository headerRepository;
    private final DrillingUnitRepository drillingUnitRepository;
    private final WorkSubTypeRepository workSubTypeRepository;

    public DetailService(DetailRepository detailRepository,
                         HeaderRepository headerRepository,
                         DrillingUnitRepository drillingUnitRepository,
                         WorkSubTypeRepository workSubTypeRepository) {
        this.detailRepository = detailRepository;
        this.headerRepository = headerRepository;
        this.drillingUnitRepository = drillingUnitRepository;
        this.workSubTypeRepository = workSubTypeRepository;
    }

    public List<Detail> getAllDetails() {
        return detailRepository.findAll();
    }

    public Page<Detail> getAllDetailByPage(Long headerId, Pageable pageable) {
        return detailRepository.findByHeaderId(headerId, pageable);
    }

    public Detail getDetailById(Long id) {
        return detailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Деталь с кодом " + id + " не найдена"));
    }

    public Detail createDetail(DetailDTO dto) {
        Header header = headerRepository.findById(dto.getHeaderId())
                .orElseThrow(() -> new ResourceNotFoundException("Заголовок с кодом " + dto.getHeaderId() + " не найден"));

        DrillingUnit drillingUnit = drillingUnitRepository.findById(dto.getDrillingUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Агрегат с кодом " + dto.getDrillingUnitId() + " не найдена"));

        WorkSubType workSubType = workSubTypeRepository.findById(dto.getWorkSubTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Подвид работ с кодом " + dto.getWorkSubTypeId() + " не найден"));

        Detail detail = Detail.builder()
                .shift(dto.getShift())
                .rotation(dto.getRotation())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .depth(dto.getDepth())
                .header(header)
                .drillingUnit(drillingUnit)
                .workSubType(workSubType)
                .resultGIS(dto.getResultGIS())
                .drillHoleState(dto.getDrillHoleState())
                .acted(dto.getActed())
                .build();

        return detailRepository.save(detail);
    }

    public Detail updateDetail(Long id, DetailDTO dto) {
        Detail detail = detailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись с кодом " + id + " не найдена"));

        Header header = headerRepository.findById(dto.getHeaderId())
                .orElseThrow(() -> new ResourceNotFoundException("Заголовок с кодом " + dto.getHeaderId() + " не найден"));

        DrillingUnit drillingUnit = drillingUnitRepository.findById(dto.getDrillingUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Агрегат с кодом " + dto.getDrillingUnitId() + " не найдена"));

        WorkSubType workSubType = workSubTypeRepository.findById(dto.getWorkSubTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Подвид работ с кодом " + dto.getWorkSubTypeId() + " не найден"));

        detail.setShift(dto.getShift());
        detail.setRotation(dto.getRotation());
        detail.setDate(dto.getDate());
        detail.setStartTime(dto.getStartTime());
        detail.setEndTime(dto.getEndTime());
        detail.setDepth(dto.getDepth());
        detail.setHeader(header);
        detail.setDrillingUnit(drillingUnit);
        detail.setWorkSubType(workSubType);
        detail.setResultGIS(dto.getResultGIS());
        detail.setDrillHoleState(dto.getDrillHoleState());
        detail.setActed(dto.getActed());

        return detailRepository.save(detail);
    }

    public void deleteDetail(Long id) {
        if (!detailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Запись с кодом " + id + " не найдена");
        }
        detailRepository.deleteById(id);
    }
}
