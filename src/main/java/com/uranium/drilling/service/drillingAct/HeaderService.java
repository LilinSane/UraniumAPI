package com.uranium.drilling.service.drillingAct;

import com.uranium.drilling.dto.drillingAct.HeaderDTO;
import com.uranium.drilling.entity.directory.DrillHole;
import com.uranium.drilling.entity.directory.DrillingType;
import com.uranium.drilling.entity.drillingAct.Header;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.DrillHoleRepository;
import com.uranium.drilling.repository.directory.DrillingTypeRepository;
import com.uranium.drilling.repository.drillingAct.HeaderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeaderService {
    private final HeaderRepository headerRepository;
    private final DrillingTypeRepository drillingTypeRepository;
    private final DrillHoleRepository drillHoleRepository;

    public HeaderService(HeaderRepository headerRepository,
                         DrillingTypeRepository drillingTypeRepository,
                         DrillHoleRepository drillHoleRepository) {
        this.headerRepository = headerRepository;
        this.drillingTypeRepository = drillingTypeRepository;
        this.drillHoleRepository = drillHoleRepository;
    }

    public List<Header> getAllHeaders(Boolean isActiveOnly) {
        if (isActiveOnly != null && isActiveOnly) {
            return headerRepository.findByIsActiveTrue();
        }
        return headerRepository.findAll();
    }

    public Page<Header> getAllHeadersByPage(Pageable pageable) {
        return headerRepository.findAll(pageable);
    }

    public Header getHeaderById(Long id) {
        return headerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заголовок с кодом " + id + " не найден"));
    }

    public Header createHeader(HeaderDTO headerDTO) {
        DrillingType drillingType = drillingTypeRepository.findById(headerDTO.getDrillingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид бурения с кодом " + headerDTO.getDrillingTypeId() + " не найден"));

        DrillHole drillHole = drillHoleRepository.findById(headerDTO.getDrillHoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Скважина с кодом " + headerDTO.getDrillHoleId() + " не найдена"));

        Header header = Header.builder()
                .date(headerDTO.getDate())
                .isActive(headerDTO.getIsActive())
                .drillingType(drillingType)
                .drillHole(drillHole)
                .build();

        return headerRepository.save(header);
    }

    public Header updateHeader(Long id, HeaderDTO headerDTO) {
        Header header = headerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заголовок с кодом " + id + " не найден"));

        DrillingType drillingType = drillingTypeRepository.findById(headerDTO.getDrillingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Вид бурения с кодом " + headerDTO.getDrillingTypeId() + " не найден"));

        DrillHole drillHole = drillHoleRepository.findById(headerDTO.getDrillHoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Скважина с кодом " + headerDTO.getDrillHoleId() + " не найдена"));

        header.setDate(headerDTO.getDate());
        header.setIsActive(headerDTO.getIsActive());
        header.setDrillingType(drillingType);
        header.setDrillHole(drillHole);

        return headerRepository.save(header);
    }

    public void deleteHeader(Long id) {
        if (!headerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Заголовок с кодом " + id + " не найден");
        }

        headerRepository.deleteById(id);
    }
}
