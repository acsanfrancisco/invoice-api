package acsanfrancisco.invoice_system.service;

import acsanfrancisco.invoice_system.dto.CreateCustomerDto;
import acsanfrancisco.invoice_system.dto.CustomerResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateCustomerDto;
import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import acsanfrancisco.invoice_system.exception.CustomerIsNotActiveException;
import acsanfrancisco.invoice_system.exception.CustomerNotFoundException;
import acsanfrancisco.invoice_system.exception.DocumentAlreadyRegisteredException;
import acsanfrancisco.invoice_system.mapper.CustomerMapper;
import acsanfrancisco.invoice_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import static acsanfrancisco.invoice_system.entity.specification.CustomerSpecifications.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponseDto createCustomer(CreateCustomerDto dto) {
        if(customerRepository.existsByDocument(dto.getDocument())){
            throw new DocumentAlreadyRegisteredException("Document already exists. Document: " + dto.getDocument());
        }
        Customer customer = CustomerMapper.toEntity(dto);
        return CustomerMapper
                .toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponseDto updateCustomer(UpdateCustomerDto dto, UUID id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found. Id: " + id));
        if(customer.getIsActive() == false){
            throw new CustomerIsNotActiveException("Must inform a active customer to update. ID: " + id);
        }

        if(dto.getFullName() != null){
            customer.setFullName(dto.getFullName());
        }

        if(dto.getWhatsappNumber() != null){
            customer.setWhatsappNumber(dto.getWhatsappNumber());
        }

        return CustomerMapper
                .toDto(customerRepository.save(customer));
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found. Id: " + id));
        customerRepository.delete(customer);
    }

    @Transactional
    public void safeDeleteCustomer(UUID id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found. Id: " + id));
        if(!customer.getIsActive()){
            throw new CustomerIsNotActiveException("Customer is already not active.");
        }
        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto findCustomerById(UUID id){
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found. Id: " + id));
        return CustomerMapper.toDto(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto findCustomerByDocument(String document){
        Customer customer = customerRepository
                .findByDocument(document)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found. Document: " + document));
        return CustomerMapper.toDto(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponseDto findCustomerByWhatsappNumber(String whatsappNumber){
        Customer customer = customerRepository
                .findByWhatsappNumber(whatsappNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found. Whatsapp number: " + whatsappNumber));
        return CustomerMapper.toDto(customer);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> search(String fullName, String document, String whatsappNumber,
                                            DocumentType documentType, Boolean active, Pageable pageable){
        Specification<Customer> specification = Specification.allOf(
                fullNameLike(fullName),
                documentLike(document),
                whatsappNumberEquals(whatsappNumber),
                documentTypeEquals(documentType),
                isActive(active));
        return customerRepository.findAll(specification, pageable).map(CustomerMapper::toDto);
    }
}
