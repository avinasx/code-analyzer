package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.CustomerDetailModel;
import com.example.app.model.internal.CustomerModel;
import com.example.app.model.mapping.mapper.CustomerMapper;
import com.example.app.model.request.CustomerRequestModel;
import com.example.app.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerModel> getAllCustomers() {
        var result = customerRepository.findAll();
        return customerMapper.mapToDtoList(result);
    }

    @Override
    public Optional<CustomerModel> getCustomerById(String id) {
        var result = customerRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id '" + id + "'"));
        return Optional.of(customerMapper.mapToDto(entity));
    }

    @Override
    public List<CustomerDetailModel> getAllCustomersDetail() {
        return customerRepository.findAllCustomersDetail();
    }

    @Override
    public Optional<CustomerDetailModel> getCustomerDetailById(String id) {
        var result = customerRepository.findCustomerDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public CustomerModel addCustomer(CustomerRequestModel model) {
        var entity = customerMapper.mapToEntity(model);
        var result = customerRepository.save(entity);
        return customerMapper.mapToDto(result);
    }

    @Override
    public CustomerModel updateCustomer(String id, CustomerRequestModel model) {
        var result = customerRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id '" + id + "'"));
        customerMapper.updateEntity(model, entity);
        var updated = customerRepository.save(entity);
        return customerMapper.mapToDto(updated);
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRepository.deleteById(Integer.valueOf(id));
    }
}
