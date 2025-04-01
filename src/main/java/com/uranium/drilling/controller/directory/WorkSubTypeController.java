package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.directory.WorkSubTypeDTO;
import com.uranium.drilling.entity.directory.WorkSubType;
import com.uranium.drilling.service.directory.WorkSubTypeService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/work-sub-types")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkSubTypeController {

    private final WorkSubTypeService workSubTypeService;
    private final PageService pageService;

    public WorkSubTypeController(WorkSubTypeService workSubTypeService, PageService pageService) {
        this.workSubTypeService = workSubTypeService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<WorkSubType>> getAllWorkSubTypes() {
        List<WorkSubType> workSubTypes = workSubTypeService.getAllWorkSubTypes();
        return ResponseEntity.ok(workSubTypes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WorkSubType>> getWorkSubTypesByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<WorkSubType> workSubTypes = workSubTypeService.getAllWorkSubTypesByPage(pageable);
        return ResponseEntity.ok(workSubTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkSubType> getWorkSubTypeById(@PathVariable Long id) {
        WorkSubType workSubType = workSubTypeService.getWorkSubTypeById(id);
        return ResponseEntity.ok(workSubType);
    }

    @PostMapping
    public ResponseEntity<WorkSubType> createWorkSubType(@RequestBody @Valid WorkSubTypeDTO workSubTypeDTO) {
        WorkSubType createdWorkSubType = workSubTypeService.createWorkSubType(workSubTypeDTO);
        return ResponseEntity.ok(createdWorkSubType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkSubType> updateWorkSubType(@PathVariable Long id, @RequestBody @Valid WorkSubTypeDTO workSubTypeDTO) {
        WorkSubType updatedWorkSubType = workSubTypeService.updateWorkSubType(id, workSubTypeDTO);
        return ResponseEntity.ok(updatedWorkSubType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkSubType(@PathVariable Long id) {
        workSubTypeService.deleteWorkSubType(id);
        return ResponseEntity.noContent().build();
    }
}
