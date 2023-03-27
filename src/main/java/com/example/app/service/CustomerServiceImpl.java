package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.core.CustomerModel;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailModel;
import com.example.app.model.mapping.mapper.CustomerMapper;
import com.example.app.model.request.CustomerRequestModel;
import com.example.app.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerModel> getCustomers() {
        var list = customerRepository.findAll();
        return customerMapper.mapToDtoList(list);
    }

    @Override
    public Optional<CustomerModel> getCustomer(String customerId) {
        var entity = customerRepository.findById(Integer.valueOf(customerId)).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id '" + customerId + "'"));
        return Optional.of(customerMapper.mapToDto(entity));
    }

    @Override
    public List<CustomerDetailModel> getCustomersDetail() {
        return customerRepository.findAllCustomersDetail();
    }

    @Override
    public Optional<CustomerDetailModel> getCustomerDetail(String customerId) {
        var model = customerRepository.findCustomerDetailById(Integer.valueOf(customerId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found with id '" + customerId + "'");
        }
        return model;
    }

    @Override
    public List<PaymentModel> getCustomerPayments(String customerId) {
        return customerRepository.findAllCustomerPaymentsById(Integer.valueOf(customerId));
    }

    @Override
    public List<PaymentModel> getCustomerPayments(String customerId, String startDate, String endDate) {
        return customerRepository.findAllCustomerPaymentsByIdWithFilter(Integer.valueOf(customerId),
                LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Override
    public List<RentalModel> getCustomerRentals(String customerId) {
        return customerRepository.findAllCustomerRentalsById(Integer.valueOf(customerId));
    }

    @Override
    public List<RentalModel> getCustomerRentals(String customerId, String status, String startDate, String endDate) {
        return customerRepository.findAllCustomerRentalsByIdWithFilter(Integer.valueOf(customerId),
                status, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Override
    public CustomerModel addCustomer(CustomerRequestModel model) {
        var entity = customerMapper.mapToEntity(model);
        var savedEntity = customerRepository.save(entity);
        return customerMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public CustomerModel updateCustomer(String customerId, CustomerRequestModel model) {
        var entity = customerRepository.findById(Integer.valueOf(customerId)).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id '" + customerId + "'"));
        entity.update(customerMapper.mapToEntity(model));
        return customerMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(Integer.valueOf(customerId));
    }
}
