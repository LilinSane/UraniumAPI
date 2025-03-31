package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.DrillingUnitDTO;
import com.uranium.drilling.entity.DrillingUnit;
import com.uranium.drilling.service.DrillingUnitService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/drilling-units")
@CrossOrigin(origins = "http://localhost:4200")
public class DrillingUnitController {

    private final DrillingUnitService drillingUnitService;
    private final PageService pageService;

    public DrillingUnitController(DrillingUnitService drillingUnitService, PageService pageService) {
        this.drillingUnitService = drillingUnitService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<DrillingUnit>> getAllDrillingUnits() {
        List<DrillingUnit> drillingUnits = drillingUnitService.getAllDrillingUnits();
        return ResponseEntity.ok(drillingUnits);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<DrillingUnit>> getDrillingUnitsByPage(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<DrillingUnit> drillingUnits = drillingUnitService.getAllDrillingUnitsByPage(pageable);
        return ResponseEntity.ok(drillingUnits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillingUnit> getDrillingUnitById(@PathVariable Long id) {
        DrillingUnit drillingUnit = drillingUnitService.getDrillingUnitById(id);
        return ResponseEntity.ok(drillingUnit);
    }

    @PostMapping
    public ResponseEntity<DrillingUnit> createDrillingUnit(@RequestBody @Valid DrillingUnitDTO drillingUnitDTO) {
        DrillingUnit createdDrillingUnit = drillingUnitService.createDrillingUnit(drillingUnitDTO);
        return ResponseEntity.ok(createdDrillingUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrillingUnit> updateDrillingUnit(@PathVariable Long id, @RequestBody @Valid DrillingUnitDTO drillingUnitDTO) {
        DrillingUnit updatedDrillingUnit = drillingUnitService.updateDrillingUnit(id, drillingUnitDTO);
        return ResponseEntity.ok(updatedDrillingUnit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrillingUnit(@PathVariable Long id) {
        drillingUnitService.deleteDrillingUnit(id);
        return ResponseEntity.noContent().build();
    }
}
