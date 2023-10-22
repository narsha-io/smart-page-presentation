package io.narsha.smartpage.example.web;

import io.narsha.smartpage.example.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatRepository repository;

    @GetMapping
    public List<StatRepository.Dto> get(Pageable pageable) {
        return repository.findStats(pageable);
    }
}
