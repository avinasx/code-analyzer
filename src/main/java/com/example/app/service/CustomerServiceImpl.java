package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.internal.CustomerDetailModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.CustomerRequestModel;
import com.example.app.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<CustomerEntity> getCustomerById(String id) {
        return customerRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<CustomerDetailModel> getAllCustomersDetail() {
        return customerRepository.findAllCustomersDetail();
    }

    @Override
    public Optional<CustomerDetailModel> getCustomerDetailById(String id) {
        return customerRepository.findCustomerDetailById(Integer.valueOf(id));
    }

    @Override
    public CustomerEntity addCustomer(CustomerRequestModel model) {
        var entity = new CustomerEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return customerRepository.save(entity);
    }

    @Override
    public CustomerEntity updateCustomer(String id, CustomerRequestModel model) {
        var resource = customerRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return customerRepository.save(entity);
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRepository.deleteById(Integer.valueOf(id));
    }
}
