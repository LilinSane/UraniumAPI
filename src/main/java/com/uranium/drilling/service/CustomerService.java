package com.uranium.drilling.service;

import com.uranium.drilling.dto.CustomerDTO;
import com.uranium.drilling.entity.Customer;
import com.uranium.drilling.exception.ResourceNotFoundException;
import com.uranium.drilling.exception.ForeignKeyConstraintException;
import com.uranium.drilling.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(Boolean isActiveOnly) {
        if(isActiveOnly != null && isActiveOnly){
           return customerRepository.findByIsActiveTrue();
        }
        return customerRepository.findAll();
    }

    public Page<Customer> getAllCustomersByPage(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));
    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setIsActive(customerDTO.getIsActive());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Объект с кодом " + id + " не найден"));

        customer.setName(customerDTO.getName());
        customer.setIsActive(customerDTO.getIsActive());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Объект с кодом " + id + " не найден");
        }

        if (customerRepository.hasLinkedAreas(id)) {
            throw new ForeignKeyConstraintException("Невозможно удалить объект, так как на него ссылается другой объект");
        }

        customerRepository.deleteById(id);
    }
}

