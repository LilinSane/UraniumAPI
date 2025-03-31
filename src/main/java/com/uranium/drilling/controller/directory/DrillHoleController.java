package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.DrillHoleDTO;
import com.uranium.drilling.entity.DrillHole;
import com.uranium.drilling.service.DrillHoleService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/drill-holes")
@CrossOrigin(origins = "http://localhost:4200")
public class DrillHoleController {

    private final DrillHoleService drillHoleService;
    private final PageService pageService;

    public DrillHoleController(DrillHoleService drillHoleService, PageService pageService) {
        this.drillHoleService = drillHoleService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<DrillHole>> getAllDrillHoles(@RequestParam Boolean isActiveOnly) {
        List<DrillHole> drillHoles = drillHoleService.getAllDrillHoles(isActiveOnly);
        return ResponseEntity.ok(drillHoles);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<DrillHole>> getDrillHolesByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<DrillHole> drillHoles = drillHoleService.getAllDrillHolesByPage(pageable);
        return ResponseEntity.ok(drillHoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillHole> getDrillHoleById(@PathVariable Long id) {
        DrillHole drillHole = drillHoleService.getDrillHoleById(id);
        return ResponseEntity.ok(drillHole);
    }

    @PostMapping
    public ResponseEntity<DrillHole> createDrillHole(@RequestBody @Valid DrillHoleDTO drillHoleDTO) {
        DrillHole createdDrillHole = drillHoleService.createDrillHole(drillHoleDTO);
        return ResponseEntity.ok(createdDrillHole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrillHole> updateDrillHole(@PathVariable Long id, @RequestBody @Valid DrillHoleDTO drillHoleDTO) {
        DrillHole updatedDrillHole = drillHoleService.updateDrillHole(id, drillHoleDTO);
        return ResponseEntity.ok(updatedDrillHole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrillHole(@PathVariable Long id) {
        drillHoleService.deleteDrillHole(id);
        return ResponseEntity.noContent().build();
    }
}
