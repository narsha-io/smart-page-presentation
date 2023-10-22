package io.narsha.smartpage.example.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class TrackInPlaylistId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "trk_id")
    private Track track;

    @ManyToOne
    @JoinColumn(name = "onp_id")
    private OnlinePlatform onlinePlatform;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackInPlaylistId id = (TrackInPlaylistId) o;
        return Objects.equals(track.getId(), id.track.getId()) && Objects.equals(onlinePlatform.getId(), id.onlinePlatform.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(track.getId(), onlinePlatform.getId());
    }
}
