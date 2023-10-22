package io.narsha.smartpage.example.dto;

import io.narsha.smartpage.spring.sql.SqlDataTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SqlDataTable(query = """
            select
                artist.art_name as artistName,
                track.trk_name as trackName,
                online_platform.onp_name as platform,
                track_playlist.trk_play_qty as quantity
                from track_playlist
                join track on track.trk_id = track_playlist.trk_id
                join track_artist on track_artist.trk_id = track.trk_id
                join artist on artist.art_id = track_artist.art_id
                join online_platform on online_platform.onp_id = track_playlist.onp_id
        """)
public class MyDTO  {

    private String artistName;
    private String trackName;
    private String platform;
    private Long quantity;
}
