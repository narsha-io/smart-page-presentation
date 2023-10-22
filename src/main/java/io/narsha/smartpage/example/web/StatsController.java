package io.narsha.smartpage.example.web;

import io.narsha.smartpage.example.repository.FilterType;
import io.narsha.smartpage.example.repository.StatRepository;
import io.narsha.smartpage.example.repository.StatRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    //private StatRepository repository;
    private StatRepositoryV2 repository;

    @GetMapping
    public List<? extends StatRepository.Dto> get(
            @RequestParam(required = false) String track,
            @RequestParam(required = false) FilterType trackType,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) FilterType artistType,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) FilterType platformType,
            Pageable pageable) {
        //return repository.findStats(pageable);
        return repository.findStats(track, trackType, artist, artistType, platform, platformType, pageable);
    }
}
