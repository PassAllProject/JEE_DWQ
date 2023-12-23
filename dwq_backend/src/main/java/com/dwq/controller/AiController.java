package com.dwq.controller;
import com.dwq.entity.dto.Ai;
import com.dwq.service.impl.AiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping
    public List<Ai> getAll() {
        return aiService.findAll();
    }

    @GetMapping("/{id}")
    public Ai getById(@PathVariable Integer id) {
        return aiService.findById(id);
    }

    @PostMapping
    public void post(@RequestBody Ai ai) {
        aiService.addAi(ai);
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Integer id, @RequestBody Ai ai) {
        ai.setId(id);
        aiService.updateAi(ai);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        aiService.deleteAi(id);
    }
}
