package com.dwq.controller;
import com.dwq.entity.dto.Address;
import com.dwq.service.impl.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public void addAddress(@RequestBody Address address) {
        addressService.addAddress(address);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable("id") Integer addressId) {
        addressService.deleteAddress(addressId);
    }

    @PutMapping("/{id}")
    public void updateAddress(@RequestBody Address address,@PathVariable("id") int id) {
        address.setAddressId(id);
        addressService.updateAddress(address);
    }

    @GetMapping
    public List<Address> getAddress(String consignee) {
        if(consignee.isEmpty())
        return addressService.getAllAddresses();
        else
        return addressService.getAddress(consignee);
    }
}
