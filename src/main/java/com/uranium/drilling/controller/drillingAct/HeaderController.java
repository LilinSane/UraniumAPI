package com.uranium.drilling.controller.drillingAct;

import com.uranium.drilling.dto.drillingAct.HeaderDTO;
import com.uranium.drilling.entity.drillingAct.Header;
import com.uranium.drilling.service.PageService;
import com.uranium.drilling.service.drillingAct.HeaderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acts/headers")
@CrossOrigin(origins = "http://localhost:4200")
public class HeaderController {

    private final HeaderService headerService;
    private final PageService pageService;

    public HeaderController(HeaderService headerService, PageService pageService) {
        this.headerService = headerService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Header>> getAllHeaders(@RequestParam(required = false) Boolean isActiveOnly) {
        return ResponseEntity.ok(headerService.getAllHeaders(isActiveOnly));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Header>> getHeadersByPage(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        return ResponseEntity.ok(headerService.getAllHeadersByPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Header> getHeaderById(@PathVariable Long id) {
        return ResponseEntity.ok(headerService.getHeaderById(id));
    }

    @PostMapping
    public ResponseEntity<Header> createHeader(@Valid @RequestBody HeaderDTO dto) {
        return ResponseEntity.ok(headerService.createHeader(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Header> updateHeader(@PathVariable Long id, @Valid @RequestBody HeaderDTO dto) {
        return ResponseEntity.ok(headerService.updateHeader(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeader(@PathVariable Long id) {
        headerService.deleteHeader(id);
        return ResponseEntity.noContent().build();
    }
}
