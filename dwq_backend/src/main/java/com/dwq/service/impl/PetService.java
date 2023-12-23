package com.dwq.service.impl;
import com.dwq.entity.RestBean;
import com.dwq.entity.dto.Pet;
import com.dwq.mapper.PetMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetMapper petMapper;

    @Autowired
    public PetService(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    public Boolean addPet(Pet pet) {
        return petMapper.insertPet(pet)!=0;
    }

    public boolean deletePet(Integer id) {
        return petMapper.deletePetById(id)!=0;
    }

    public boolean updatePet(Pet pet) {
        return petMapper.updatePet(pet)!=0;
    }

    public List<Pet> getPet(String name) {
        return petMapper.selectPetById(name);
    }

    public List<Pet> getAllPets(Integer currentPage,Integer pageSize) {
        int offset = (currentPage - 1) * pageSize;
        return petMapper.selectAllPets(pageSize,offset);
    }
    public int getTotal(){
        return petMapper.getTotalPetCount();
    }

    public Pet getPetId(Integer id) {
        return petMapper.getPetById(id);
    }
}
