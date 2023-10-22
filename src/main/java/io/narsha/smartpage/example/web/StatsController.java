package io.narsha.smartpage.example.web;

import io.narsha.smartpage.core.SmartPageQuery;
import io.narsha.smartpage.example.dto.MyDTO;
import io.narsha.smartpage.spring.core.SmartPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private SmartPage smartPage;

    @GetMapping
    public ResponseEntity<List<MyDTO>> get(SmartPageQuery<MyDTO> query) {
        return smartPage.asResponseEntity(query);
    }
}
