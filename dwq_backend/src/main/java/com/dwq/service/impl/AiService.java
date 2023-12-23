package com.dwq.service.impl;

import com.dwq.entity.dto.Ai;
import com.dwq.mapper.AiMapper;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class  AiService {
    private final AiMapper aiMapper;

    public AiService(AiMapper aiMapper) {
        this.aiMapper = aiMapper;
    }

    public List<Ai> findAll() {
        return aiMapper.findAll();
    }

    public Ai findById(Integer id) {
        return aiMapper.findById(id);
    }

    public void addAi(Ai ai) {
        if (ai.getId() == null) {
            aiMapper.insert(ai);
        } else {
            aiMapper.update(ai);
        }
    }
    public void deleteAi(Integer id) {
        aiMapper.delete(id);
    }

    public void updateAi(Ai ai){
        aiMapper.update(ai);
    }
}
