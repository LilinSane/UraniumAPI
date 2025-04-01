package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.DrillingTypeDTO;
import com.uranium.drilling.entity.directory.DrillingType;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.repository.directory.DrillingTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrillingTypeService {

    private final DrillingTypeRepository drillingTypeRepository;

    public DrillingTypeService(DrillingTypeRepository drillingTypeRepository) {
        this.drillingTypeRepository = drillingTypeRepository;
    }

    public List<DrillingType> getAllDrillingTypes() {
        return drillingTypeRepository.findAll();
    }

    public Page<DrillingType> getAllDrillingTypesByPage(Pageable pageable) {
        return drillingTypeRepository.findAll(pageable);
    }

    public DrillingType getDrillingTypeById(Long id) {
        return drillingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вид бурения с кодом " + id + " не найден"));
    }

    public DrillingType createDrillingType(DrillingTypeDTO drillingTypeDTO) {
        DrillingType drillingType = DrillingType.builder()
                .name(drillingTypeDTO.getName())
                .build();

        return drillingTypeRepository.save(drillingType);
    }

    public DrillingType updateDrillingType(Long id, DrillingTypeDTO drillingTypeDTO) {
        DrillingType drillingType = drillingTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вид бурения с кодом " + id + " не найден"));

        drillingType.setName(drillingTypeDTO.getName());

        return drillingTypeRepository.save(drillingType);
    }

    public void deleteDrillingType(Long id) {
        if (!drillingTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Вид бурения с кодом " + id + " не найден");
        }
        //TODO
//        if (drillHoleTypeRepository.hasLinkedDrillHoles(id)) {
//            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
//        }
        drillingTypeRepository.deleteById(id);
    }
}
