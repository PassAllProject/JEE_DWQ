package com.dwq.mapper;

import com.dwq.entity.dto.Ai;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AiMapper {
    @Select("SELECT * FROM dwq_ai")
    List<Ai> findAll();

    @Select("SELECT * FROM dwq_ai WHERE id = #{id}")
    Ai findById(Integer id);

    @Insert("INSERT INTO dwq_ai (username, promote, answer) VALUES (#{username}, #{promote}, #{answer})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Ai ai);

    @Update("UPDATE dwq_ai SET username = #{username}, promote = #{promote}, answer = #{answer}, time = #{time} WHERE id = #{id}")
    void update(Ai ai);

    @Delete("DELETE FROM dwq_ai WHERE id = #{id}")
    void delete(Integer id);
}
