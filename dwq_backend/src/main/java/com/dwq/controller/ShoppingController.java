package com.dwq.controller;

import com.dwq.entity.dto.Shopping;
import com.dwq.service.impl.ShoppingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/shoppings")
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public void addShopping(@RequestBody Shopping shopping) {
        shoppingService.addShopping(shopping);
    }

    @DeleteMapping("/{id}")
    public void deleteShopping(@PathVariable("id") Integer shoppingId) {
        shoppingService.deleteShopping(shoppingId);
    }

    @PutMapping("/{id}")
    public void updateShopping(@RequestBody Shopping shopping,@PathVariable("id") Integer shoppingId) {
        shopping.setShoppingId(shoppingId);
        shoppingService.updateShopping(shopping);
    }

    @GetMapping("/{id}")
    public List<Shopping> getShopping(@PathVariable("id") Integer shoppingId) {
        return shoppingService.getShopping(shoppingId);
    }

    @GetMapping("/")
    public List<Shopping> getAllShoppings() {
        return shoppingService.getAllShoppings();
    }
}
