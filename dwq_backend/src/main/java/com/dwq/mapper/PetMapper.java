package com.dwq.mapper;
import com.dwq.entity.RestBean;
import com.dwq.entity.dto.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface PetMapper {
    @Insert("INSERT INTO dwq_pet (name, gender, age, species, avatar, temperament, price) VALUES (#{name}, #{gender}, #{age}, #{species}, #{avatar}, #{temperament}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPet(Pet pet);

    @Delete("DELETE FROM dwq_pet WHERE id = #{id}")
    int deletePetById(Integer id);

    @Update("UPDATE dwq_pet SET name=#{name}, gender=#{gender}, age=#{age}, species=#{species}, avatar=#{avatar}, temperament=#{temperament}, price=#{price} WHERE id = #{id}")
    int updatePet(Pet pet);

    @Select("SELECT * FROM dwq_pet WHERE name = #{name}")
    List<Pet> selectPetById(String name);

    @Select("SELECT * FROM dwq_pet LIMIT #{limit} OFFSET #{offset}")
    List<Pet> selectAllPets(@Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM dwq_pet")
    int getTotalPetCount();
    @Select("SELECT * FROM dwq_pet WHERE id = #{id}")
    Pet getPetById(Integer id);
}

