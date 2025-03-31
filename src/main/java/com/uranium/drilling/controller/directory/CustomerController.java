package com.uranium.drilling.controller.directory;

import com.uranium.drilling.dto.CustomerDTO;
import com.uranium.drilling.entity.Customer;
import com.uranium.drilling.service.CustomerService;
import com.uranium.drilling.service.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directories/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    private final CustomerService customerService;
    private final PageService pageService;

    public CustomerController(CustomerService customerService, PageService pageService) {
        this.customerService = customerService;
        this.pageService = pageService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(
            @RequestParam() Boolean isActiveOnly
    ) {
        List<Customer> customers = customerService.getAllCustomers(isActiveOnly);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Customer>> getCustomersByPage(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) List<String> sort
    ) {
        Pageable pageable = pageService.createPageRequest(page, size, sort);
        Page<Customer> customers = customerService.getAllCustomersByPage(pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
