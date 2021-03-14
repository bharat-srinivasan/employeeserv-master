package com.paypal.bfs.test.employeeserv.converter;

import com.paypal.bfs.test.employeeserv.entity.Address;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converter for handling conversions between Entity class and model class
 */
@Component
public class AddressConverter implements EntityConverter<com.paypal.bfs.test.employeeserv.api.model.Address, Address> {

    @Override
    public Address toEntity(com.paypal.bfs.test.employeeserv.api.model.Address model) {
        if (null == model) return null;
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setLine1(model.getLine1());
        address.setLine2(model.getLine2());
        address.setState(model.getState());
        address.setCity(model.getCity());
        address.setZipCode(model.getZipCode());
        return address;
    }

    @Override
    public com.paypal.bfs.test.employeeserv.api.model.Address toModel(Address entity) {
        if (null == entity) return null;
        com.paypal.bfs.test.employeeserv.api.model.Address address = new com.paypal.bfs.test.employeeserv.api.model.Address();
        address.setCity(entity.getCity());
        address.setState(entity.getState());
        address.setZipCode(entity.getZipCode());
        address.setLine1(entity.getLine1());
        address.setLine2(entity.getLine2());
        return address;
    }
}
