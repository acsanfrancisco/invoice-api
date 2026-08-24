package acsanfrancisco.invoice_system.mapper;

import acsanfrancisco.invoice_system.dto.CreateCustomerDto;
import acsanfrancisco.invoice_system.dto.CustomerResponseDto;
import acsanfrancisco.invoice_system.dto.UpdateCustomerDto;
import acsanfrancisco.invoice_system.entity.Customer;
import acsanfrancisco.invoice_system.entity.enums.DocumentType;
import org.hibernate.sql.Update;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerDto dto) {
        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setDocument(dto.getDocument());
        customer.setWhatsappNumber(dto.getWhatsappNumber());
        customer.setDocumentType(DocumentType.validateDocumentType(dto.getDocument()));
        customer.setIsActive(true);
        return customer;
    }

    public static CustomerResponseDto toDto(Customer customer){
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setDocument(customer.getDocument());
        dto.setWhatsappNumber(customer.getWhatsappNumber());
        return dto;
    }

    public static List<CustomerResponseDto> listToDto(List<Customer> customers) {
        return customers.stream().map(CustomerMapper::toDto).toList();
    }

    public static void updateToEntity(UpdateCustomerDto dto, Customer customer) {
        customer.setFullName(dto.getFullName());
        customer.setWhatsappNumber(String.valueOf(dto.getWhatsappNumber()));
    }
}
