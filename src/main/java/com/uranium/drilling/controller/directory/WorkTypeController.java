package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.WorkTypeDTO;
import com.uranium.drilling.entity.WorkType;
import com.uranium.drilling.service.WorkTypeService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/work-types")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkTypeController {

    private final WorkTypeService workTypeService;
    private final PageService pageService;

    public WorkTypeController(WorkTypeService workTypeService, PageService pageService) {
        this.workTypeService = workTypeService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<WorkType>> getAllWorkTypes() {
        List<WorkType> workTypes = workTypeService.getAllWorkTypes();
        return ResponseEntity.ok(workTypes);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WorkType>> getWorkTypesByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<WorkType> workTypes = workTypeService.getAllWorkTypesByPage(pageable);
        return ResponseEntity.ok(workTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkType> getWorkTypeById(@PathVariable Long id) {
        WorkType workType = workTypeService.getWorkTypeById(id);
        return ResponseEntity.ok(workType);
    }

    @PostMapping
    public ResponseEntity<WorkType> createWorkType(@RequestBody @Valid WorkTypeDTO workTypeDTO) {
        WorkType createdWorkType = workTypeService.createWorkType(workTypeDTO);
        return ResponseEntity.ok(createdWorkType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkType> updateWorkType(@PathVariable Long id, @RequestBody @Valid WorkTypeDTO workTypeDTO) {
        WorkType updatedWorkType = workTypeService.updateWorkType(id, workTypeDTO);
        return ResponseEntity.ok(updatedWorkType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkType(@PathVariable Long id) {
        workTypeService.deleteWorkType(id);
        return ResponseEntity.noContent().build();
    }
}
