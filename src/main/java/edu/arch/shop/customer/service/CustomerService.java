
package edu.arch.shop.customer.service;

import edu.arch.shop.customer.dto.CustomerRequest;
import edu.arch.shop.customer.dto.CustomerResponse;
import edu.arch.shop.customer.entity.Customer;
import edu.arch.shop.customer.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerResponse findById(Long id) {
        return toResponse(findCustomer(id));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        validate(request);

        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmailAddress(request.emailAddress());

        return toResponse(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        validate(request);

        Customer customer = findCustomer(id);
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmailAddress(request.emailAddress());

        return toResponse(customerRepository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = findCustomer(id);
        customerRepository.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void validate(CustomerRequest request) {
        if (request.firstName() == null
                || request.firstName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "First name is required"
            );
        }

        if (request.firstName().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "First name is too long"
            );
        }

        if (request.lastName() == null
                || request.lastName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Last name is required"
            );
        }

        if (request.lastName().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Last name is too long"
            );
        }

        if (request.emailAddress() == null
                || request.emailAddress().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email address is required"
            );
        }

        if (request.emailAddress().length() > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email address is too long"
            );
        }

        if (!request.emailAddress().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid email address"
            );
        }
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmailAddress()
        );
    }
}
