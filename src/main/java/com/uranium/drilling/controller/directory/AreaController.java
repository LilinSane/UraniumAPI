package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.directory.AreaDTO;
import com.uranium.drilling.entity.directory.Area;
import com.uranium.drilling.service.directory.AreaService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/areas")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    private final AreaService areaService;
    private final PageService pageService;

    public AreaController(AreaService areaService, PageService pageService) {
        this.areaService = areaService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas(@RequestParam Boolean isActiveOnly) {
        List<Area> areas = areaService.getAllAreas(isActiveOnly);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Area>> getAreasByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<Area> areas = areaService.getAllAreasByPage(pageable);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Long id) {
        Area area = areaService.getAreaById(id);
        return ResponseEntity.ok(area);
    }

    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody @Valid AreaDTO areaDTO) {
        Area createdArea = areaService.createArea(areaDTO);
        return ResponseEntity.ok(createdArea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(@PathVariable Long id, @RequestBody @Valid AreaDTO areaDTO) {
        Area updatedArea = areaService.updateArea(id, areaDTO);
        return ResponseEntity.ok(updatedArea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity.noContent().build();
    }
}
