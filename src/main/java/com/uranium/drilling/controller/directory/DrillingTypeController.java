package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.DrillingTypeDTO;
import com.uranium.drilling.entity.DrillingType;
import com.uranium.drilling.service.DrillingTypeService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/drilling-types")
@CrossOrigin(origins = "http://localhost:4200")
public class DrillingTypeController {

    private final DrillingTypeService drillingTypeService;
    private final PageService pageService;

    public DrillingTypeController(DrillingTypeService drillingTypeService, PageService pageService) {
        this.drillingTypeService = drillingTypeService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<DrillingType>> getAllDrillingTypes() {
        List<DrillingType> drillingTypes = drillingTypeService.getAllDrillingTypes();
        return ResponseEntity.ok(drillingTypes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<DrillingType>> getDrillingTypesByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<DrillingType> drillingTypes = drillingTypeService.getAllDrillingTypesByPage(pageable);
        return ResponseEntity.ok(drillingTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillingType> getDrillingTypeById(@PathVariable Long id) {
        DrillingType drillingType = drillingTypeService.getDrillingTypeById(id);
        return ResponseEntity.ok(drillingType);
    }

    @PostMapping
    public ResponseEntity<DrillingType> createDrillingType(@RequestBody @Valid DrillingTypeDTO drillingTypeDTO) {
        DrillingType createdDrillingType = drillingTypeService.createDrillingType(drillingTypeDTO);
        return ResponseEntity.ok(createdDrillingType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrillingType> updateDrillingType(@PathVariable Long id, @RequestBody @Valid DrillingTypeDTO drillingTypeDTO) {
        DrillingType updatedDrillingType = drillingTypeService.updateDrillingType(id, drillingTypeDTO);
        return ResponseEntity.ok(updatedDrillingType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrillingType(@PathVariable Long id) {
        drillingTypeService.deleteDrillingType(id);
        return ResponseEntity.noContent().build();
    }
}
