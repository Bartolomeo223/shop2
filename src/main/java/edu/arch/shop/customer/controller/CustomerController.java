package edu.arch.shop.customer.controller;

import edu.arch.shop.customer.dto.CustomerRequest;
import edu.arch.shop.customer.dto.CustomerResponse;
import edu.arch.shop.customer.service.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //find all customers
    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    //find customer by id
    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    //find customer by email address
    @GetMapping("/search")
    public CustomerResponse findByEmailAddress(
            @RequestParam("emailAddress") String emailAddress) {
        return customerService.findByEmailAddress(emailAddress);
    }

    //add new client
    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @RequestBody CustomerRequest request) {

        CustomerResponse response = customerService.create(request);

        return ResponseEntity
                .created(URI.create("/customers/" + response.id()))
                .body(response);
    }

    //update customer data
    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Long id,
            @RequestBody CustomerRequest request) {

        return customerService.update(id, request);
    }

    //delete customer by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}