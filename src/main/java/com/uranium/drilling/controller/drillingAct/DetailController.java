package com.uranium.drilling.controller.drillingAct;

import com.uranium.drilling.dto.drillingAct.DetailDTO;
import com.uranium.drilling.entity.drillingAct.Detail;
import com.uranium.drilling.service.PageService;
import com.uranium.drilling.service.drillingAct.DetailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acts/details")
@CrossOrigin(origins = "http://localhost:4200")
public class DetailController {

    private final DetailService detailService;
    private final PageService pageService;

    public DetailController(DetailService detailService, PageService pageService) {
        this.detailService = detailService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Detail>> getAllDetails() {
        return ResponseEntity.ok(detailService.getAllDetails());
    }

    @GetMapping("/{headerId}/paged")
    public ResponseEntity<Page<Detail>> getDetailsByPage(
            @PathVariable Long headerId,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        return ResponseEntity.ok(detailService.getAllDetailByPage(headerId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detail> getDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(detailService.getDetailById(id));
    }

    @PostMapping
    public ResponseEntity<Detail> createDetail(@Valid @RequestBody DetailDTO dto) {
        return ResponseEntity.ok(detailService.createDetail(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Detail> updateDetail(@PathVariable Long id, @Valid @RequestBody DetailDTO dto) {
        return ResponseEntity.ok(detailService.updateDetail(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        detailService.deleteDetail(id);
        return ResponseEntity.noContent().build();
    }
}
