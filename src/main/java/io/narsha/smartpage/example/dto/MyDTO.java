package io.narsha.smartpage.example.dto;

import io.narsha.smartpage.example.repository.StatRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyDTO implements StatRepository.Dto {

    private String artistName;
    private String trackName;
    private String platform;
    private Long quantity;
}
