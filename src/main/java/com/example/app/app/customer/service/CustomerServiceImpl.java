package com.example.app.app.customer.service;

import com.example.app.app.customer.domain.dto.CustomerDetailsModel;
import com.example.app.app.customer.domain.dto.CustomerModel;
import com.example.app.app.customer.domain.dto.CustomerRequestModel;
import com.example.app.app.customer.domain.mapper.CustomerMapper;
import com.example.app.app.customer.repository.CustomerRepository;
import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.rental.domain.dto.RentalModel;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerModel> getCustomerList() {
        var list = customerRepository.findAll();
        return customerMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerModel> getCustomer(String customerId) {
        var entity = customerRepository.findById(Integer.valueOf(customerId)).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id '" + customerId + "'"));
        return Optional.of(customerMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDetailsModel> getCustomerDetailsList() {
        return customerRepository.findAllCustomerDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDetailsModel> getCustomerDetails(String customerId) {
        var model = customerRepository.findCustomerDetailsById(Integer.valueOf(customerId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found with id '" + customerId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentModel> getCustomerPaymentList(String customerId) {
        return customerRepository.findAllCustomerPaymentListById(Integer.valueOf(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentModel> getCustomerPaymentList(String customerId, String startDate, String endDate) {
        return customerRepository.findAllCustomerPaymentListByIdWithFilter(Integer.valueOf(customerId),
                LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalModel> getCustomerRentalList(String customerId) {
        return customerRepository.findAllCustomerRentalListById(Integer.valueOf(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalModel> getCustomerRentalList(String customerId, String status, String startDate, String endDate) {
        return customerRepository.findAllCustomerRentalListByIdWithFilter(Integer.valueOf(customerId),
                status, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    @Override
    @Transactional
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
