package io.narsha.smartpage.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "track")
public class Track {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trk_id")
    private Long id;

    @Column(name = "trk_name", unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "track_artist",
            joinColumns = { @JoinColumn(name = "trk_id") },
            inverseJoinColumns = { @JoinColumn(name = "art_id") }
    )
    private Set<Artist> artists;
}
