package de.slippert.quickstart.controller;

import de.slippert.quickstart.controller.dto.CustomerDto;
import de.slippert.quickstart.data.Customer;
import de.slippert.quickstart.data.repo.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CustomerController {

    private CustomerRepo customerRepo;

    private ModelMapper modelMapper;

    public CustomerController(@Autowired CustomerRepo customerRepo, @Autowired ModelMapper modelMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/customers")
    public List<CustomerDto> getCustomers() {
        return StreamSupport.stream(customerRepo.findAll().spliterator(), false)
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @PostMapping("/customers")
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer = customerRepo.save(customer);
        return modelMapper.map(customer, CustomerDto.class);
    }

    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Customer customer = customerRepo.findById(id)
                .map(c -> {
                    c.setFirstname(customerDto.getFirstname());
                    c.setLastname(customerDto.getLastname());
                    return customerRepo.save(c);
                })
                .orElseGet(() -> customerRepo.save(modelMapper.map(customerDto, Customer.class)));

        return modelMapper.map(customer, CustomerDto.class);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
