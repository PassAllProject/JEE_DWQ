package com.dwq.controller;

import com.dwq.entity.RestBean;
import com.dwq.entity.dto.Pet;
import com.dwq.entity.vo.response.divPage;
import com.dwq.service.impl.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/pets")
@Tag(name = "宠物相关", description = "包括宠物添加、删除、修改、查询等操作。")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Operation(summary = "添加宠物操作")
    public RestBean<Void> addPet(@RequestBody Pet pet) {
        if(petService.addPet(pet))
            return RestBean.success();
        else
            return RestBean.failure(409,"添加宠物信息失败");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除宠物操作")
    public RestBean<Void> deletePet(@PathVariable Integer id) {
        if(petService.deletePet(id))
            return RestBean.success();
        else
            return RestBean.failure(409,"删除宠物信息失败");
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改宠物操作")
    public RestBean<Void> updatePet(@RequestBody Pet pet,@PathVariable Integer id) {
        pet.setId(id);
        if(petService.updatePet(pet))
            return RestBean.success();
        else
            return RestBean.failure(409,"修改宠物信息失败");
    }
    @GetMapping
    @Operation(summary = "获取宠物")
    public RestBean<Object> getPet(String name,Integer currentPage,Integer pageSize) {
        if (name.isEmpty()) {
            divPage divPage = new divPage();
            divPage.setTotal(petService.getTotal());
            divPage.setData(petService.getAllPets(currentPage, pageSize));
            return RestBean.success(divPage);
        }else if(name.equals(String.valueOf(-1))){
            return RestBean.success(petService.getTotal());
        }else{
            divPage divPage=new divPage();
            divPage.setTotal(petService.getTotal());
            divPage.setData(petService.getPet(name));
            return RestBean.success(divPage);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "获取宠物")
    public RestBean<Object> getID(@PathVariable Integer id){
        return RestBean.success(petService.getPetId(id));
    }
}

