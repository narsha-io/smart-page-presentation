package io.narsha.smartpage.example.repository;

import io.narsha.smartpage.example.model.Artist;
import io.narsha.smartpage.example.model.TrackInPlaylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<TrackInPlaylist, TrackInPlaylist.Id> {

    @Query("select artist.name as artistName, track.name as trackName, online.name as platform, trip.quantity as quantity from TrackInPlaylist trip join trip.id.track track join track.artists artist join trip.id.onlinePlatform online")
    List<Dto> findStats(Pageable pageable);

    interface Dto {
        String getArtistName();
        String getTrackName();
        String getPlatform();
        Integer getQuantity();
    }

}
