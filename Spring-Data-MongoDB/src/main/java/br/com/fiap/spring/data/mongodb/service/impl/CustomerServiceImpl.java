package br.com.fiap.spring.data.mongodb.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.fiap.spring.data.mongodb.model.SalesOrder;
import br.com.fiap.spring.data.mongodb.model.SalesOrderItem;
import br.com.fiap.spring.data.mongodb.repository.CustomerRepository;
import br.com.fiap.spring.data.mongodb.repository.SalesOrderRepository;
import br.com.fiap.spring.data.mongodb.service.CustomerService;
import br.com.fiap.spring.data.mongodb.advice.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.spring.data.mongodb.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SalesOrderRepository salesOrderRepository;

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return customers;
	}

	@Override
	public Customer getCustomerById(String id) {
		return customerRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		Customer storedCustomer = customerRepository.findById(customer.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

		storedCustomer.setName(customer.getName());
		storedCustomer.setSurname(customer.getSurname());
		storedCustomer.setGender(customer.getGender());
		storedCustomer.setBirthDate(customer.getBirthDate());
		storedCustomer.setAddress(customer.getAddress());
		storedCustomer.setPhones(customer.getPhones());

		customerRepository.save(storedCustomer);
	}

	@Override
	public void deleteCustomer(String id) {

		List<SalesOrder> salesOrders = salesOrderRepository.findAll();

		for (SalesOrder salesOrder : salesOrders) {
			if (salesOrder.getCustomer().getId().equals(id))
				throw new ResponseError(HttpStatus.PRECONDITION_FAILED, "O cliente informado possui ao menos um pedido de venda associado.");
		}

		customerRepository.delete(customerRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Cliente não encontrado")));
	}
}
