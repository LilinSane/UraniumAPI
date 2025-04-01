package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.directory.WorkDirectionDTO;
import com.uranium.drilling.entity.directory.WorkDirection;
import com.uranium.drilling.service.directory.WorkDirectionService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/work-directions")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkDirectionController {

    private final WorkDirectionService workDirectionService;
    private final PageService pageService;

    public WorkDirectionController(WorkDirectionService workDirectionService, PageService pageService) {
        this.workDirectionService = workDirectionService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<WorkDirection>> getAllWorkDirections() {
        List<WorkDirection> workDirections = workDirectionService.getAllWorkDirections();
        return ResponseEntity.ok(workDirections);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WorkDirection>> getWorkDirectionsByPage(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<WorkDirection> workDirections = workDirectionService.getAllWorkDirectionsByPage(pageable);
        return ResponseEntity.ok(workDirections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDirection> getWorkDirectionById(@PathVariable Long id) {
        WorkDirection workDirection = workDirectionService.getWorkDirectionById(id);
        return ResponseEntity.ok(workDirection);
    }

    @PostMapping
    public ResponseEntity<WorkDirection> createWorkDirection(@RequestBody @Valid WorkDirectionDTO workDirectionDTO) {
        WorkDirection createdWorkDirection = workDirectionService.createWorkDirection(workDirectionDTO);
        return ResponseEntity.ok(createdWorkDirection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDirection> updateWorkDirection(@PathVariable Long id, @RequestBody @Valid WorkDirectionDTO workDirectionDTO) {
        WorkDirection updatedWorkDirection = workDirectionService.updateWorkDirection(id, workDirectionDTO);
        return ResponseEntity.ok(updatedWorkDirection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkDirection(@PathVariable Long id) {
        workDirectionService.deleteWorkDirection(id);
        return ResponseEntity.noContent().build();
    }
}
