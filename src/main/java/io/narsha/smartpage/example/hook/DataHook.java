package io.narsha.smartpage.example.hook;

import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Component
public class DataHook {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() throws IOException {
        var count = jdbcTemplate.queryForObject("select count(1) from artist", Integer.class);

        if (count > 1) {
            return;
        }

        injectData();
    }

    private void injectData() throws IOException {
        try (var inputStream = DataHook.class.getResourceAsStream("/spotify-2023.csv");
             var inputStreamReader = new InputStreamReader(inputStream);
             CSVReader csvReader = new CSVReader(inputStreamReader)) {

            final String spotify = "spotify";
            final String apple = "apple";
            final String deezer = "deezer";

            createPlatform(spotify);
            createPlatform(apple);
            createPlatform(deezer);

            csvReader.readAll().stream().skip(1).forEach(line -> {
                var track = line[0];
                var artists = line[1].split(",");

                var spotifyQty = Integer.valueOf(line[6]);
                var appleQty = Integer.valueOf(line[9]);
                var deezerQty = Integer.valueOf(line[11].replace(",",""));

                createTrack(track);
                Stream.of(artists).map(e -> e.trim()).filter(e -> !e.isEmpty()).forEach(artist -> {
                    createArtist(artist);
                    linkArtistTrack(track, artist);
                });
                createInPlaylist(spotify, track, spotifyQty);
                createInPlaylist(apple, track, appleQty);
                createInPlaylist(deezer, track, deezerQty);
            });
        }
    }

    private void createTrack(String track) {
        jdbcTemplate.update("insert into track(trk_name) values (?) ", track.trim());
    }

    private void createArtist(String artist) {
        jdbcTemplate.update("""
         merge into artist
                               using (values (?)) s(name)
                               on artist.art_name = s.name
                               when not matched then insert(art_name) values (s.name)
        """,artist.trim());
    }

    private void linkArtistTrack(String track, String artist) {
        jdbcTemplate.update("insert into track_artist(trk_id, art_id) values ((select trk_id from track where trk_name = ?), (select art_id from artist where art_name = ?)) ", track.trim(), artist.trim());
    }

    private void createPlatform(String platform) {
        jdbcTemplate.update("""
             insert into online_platform(onp_name) values (?)
           """, platform.trim());
    }

    private void createInPlaylist(String platform, String track, Integer qty) {
        jdbcTemplate.update("insert into track_playlist(trk_id, onp_id, trk_play_qty) values ((select trk_id from track where trk_name = ?), (select onp_id from online_platform where onp_name = ?), ?) ", track.trim(), platform.trim(), qty);
    }
}
