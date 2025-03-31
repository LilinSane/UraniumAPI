package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.DrillHoleTypeDTO;
import com.uranium.drilling.entity.DrillHoleType;
import com.uranium.drilling.service.DrillHoleTypeService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/drill-hole-types")
@CrossOrigin(origins = "http://localhost:4200")
public class DrillHoleTypeController {

    private final DrillHoleTypeService drillHoleTypeService;
    private final PageService pageService;

    public DrillHoleTypeController(DrillHoleTypeService drillHoleTypeService, PageService pageService) {
        this.drillHoleTypeService = drillHoleTypeService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<DrillHoleType>> getAllDrillHoleTypes() {
        List<DrillHoleType> drillHoleTypes = drillHoleTypeService.getAllDrillHoleTypes();
        return ResponseEntity.ok(drillHoleTypes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<DrillHoleType>> getDrillHoleTypesByPage(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<DrillHoleType> drillHoleTypes = drillHoleTypeService.getAllDrillHoleTypesByPage(pageable);
        return ResponseEntity.ok(drillHoleTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillHoleType> getDrillHoleTypeById(@PathVariable Long id) {
        DrillHoleType drillHoleType = drillHoleTypeService.getDrillHoleTypeById(id);
        return ResponseEntity.ok(drillHoleType);
    }

    @PostMapping
    public ResponseEntity<DrillHoleType> createDrillHoleType(@RequestBody @Valid DrillHoleTypeDTO drillHoleTypeDTO) {
        DrillHoleType createdDrillHoleType = drillHoleTypeService.createDrillHoleType(drillHoleTypeDTO);
        return ResponseEntity.ok(createdDrillHoleType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrillHoleType> updateDrillHoleType(@PathVariable Long id, @RequestBody @Valid DrillHoleTypeDTO drillHoleTypeDTO) {
        DrillHoleType updatedDrillHoleType = drillHoleTypeService.updateDrillHoleType(id, drillHoleTypeDTO);
        return ResponseEntity.ok(updatedDrillHoleType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrillHoleType(@PathVariable Long id) {
        drillHoleTypeService.deleteDrillHoleType(id);
        return ResponseEntity.noContent().build();
    }
}

