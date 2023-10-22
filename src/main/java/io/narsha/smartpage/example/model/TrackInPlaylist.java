package io.narsha.smartpage.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "track_playlist")
public class TrackInPlaylist {

    @Embeddable
    public class Id implements Serializable {

        @ManyToOne
        @JoinColumn(name = "trk_id")
        private Track track;

        @ManyToOne
        @JoinColumn(name = "onp_id")
        private OnlinePlatform onlinePlatform;

        public Track getTrack() {
            return track;
        }

        public void setTrack(Track track) {
            this.track = track;
        }

        public OnlinePlatform getOnlinePlatform() {
            return onlinePlatform;
        }

        public void setOnlinePlatform(OnlinePlatform onlinePlatform) {
            this.onlinePlatform = onlinePlatform;
        }
    }

    @EmbeddedId
    private Id id;
    @Column(name = "trk_play_qty")
    private Long quantity;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
