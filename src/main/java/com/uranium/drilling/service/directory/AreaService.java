package com.uranium.drilling.service.directory;

import com.uranium.drilling.dto.directory.AreaDTO;
import com.uranium.drilling.entity.directory.Area;
import com.uranium.drilling.entity.directory.Customer;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.repository.directory.AreaRepository;
import com.uranium.drilling.repository.directory.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    private final AreaRepository areaRepository;
    private final CustomerRepository customerRepository;

    public AreaService(AreaRepository areaRepository, CustomerRepository customerRepository) {
        this.areaRepository = areaRepository;
        this.customerRepository = customerRepository;
    }

    public List<Area> getAllAreas(Boolean isActiveOnly) {
        if (isActiveOnly != null && isActiveOnly) {
            return areaRepository.findByIsActiveTrue();
        }
        return areaRepository.findAll();
    }

    public Page<Area> getAllAreasByPage(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    public Area getAreaById(Long id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));
    }

    public Area createArea(AreaDTO areaDTO) {
        Customer customer = customerRepository.findById(areaDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Заказчик с кодом " + areaDTO.getCustomerId() + " не найден"));

        Area area = new Area();
        area.setName(areaDTO.getName());
        area.setIsActive(areaDTO.getIsActive());
        area.setCustomer(customer);

        return areaRepository.save(area);
    }

    public Area updateArea(Long id, AreaDTO areaDTO) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));

        Customer customer = customerRepository.findById(areaDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Заказчик с кодом " + areaDTO.getCustomerId() + " не найден"));

        area.setName(areaDTO.getName());
        area.setIsActive(areaDTO.getIsActive());
        area.setCustomer(customer);

        return areaRepository.save(area);
    }

    public void deleteArea(Long id) {
        if (!areaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Объект с кодом " + id + " не найден");
        }

        if (areaRepository.hasLinkedDrillHole(id)) {
            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
        }

        areaRepository.deleteById(id);
    }
}
