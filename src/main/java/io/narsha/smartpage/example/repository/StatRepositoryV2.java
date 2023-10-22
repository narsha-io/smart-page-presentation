package io.narsha.smartpage.example.repository;

import io.narsha.smartpage.example.dto.MyDTO;
import io.narsha.smartpage.example.model.Artist_;
import io.narsha.smartpage.example.model.OnlinePlatform_;
import io.narsha.smartpage.example.model.TrackInPlaylist;
import io.narsha.smartpage.example.model.TrackInPlaylistId_;
import io.narsha.smartpage.example.model.TrackInPlaylist_;
import io.narsha.smartpage.example.model.Track_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatRepositoryV2 {

    @Autowired
    private EntityManager entityManager;

    public List<MyDTO> findStats(String trackName, FilterType trackNameFilterType, String artistName, FilterType artistNameFilterType, String platformName, FilterType platformNameFilterType, Pageable pageable) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(MyDTO.class);

        var trackInPlaylist = query.from(TrackInPlaylist.class);
        final var trackEntity = trackInPlaylist.join(TrackInPlaylist_.ID).join(TrackInPlaylistId_.TRACK);
        final var onlinePlatformEntity = trackInPlaylist.join(TrackInPlaylist_.ID).join(TrackInPlaylistId_.ONLINE_PLATFORM);
        final var artistEntity = trackEntity.join(Track_.ARTISTS);

        if(trackName != null) {
            var predicate = applyStringFilter(builder, trackEntity, Track_.NAME, trackName, trackNameFilterType);
            query.where(predicate);
        }

        if(artistName != null) {
            var predicate = applyStringFilter(builder, artistEntity, Artist_.NAME, artistName, artistNameFilterType);
            query.where(predicate);
        }

        if(platformName != null) {
            var predicate = applyStringFilter(builder, onlinePlatformEntity, OnlinePlatform_.NAME, platformName, platformNameFilterType);
            query.where(predicate);
        }

        var selection = builder.construct(MyDTO.class, artistEntity.get(Artist_.NAME), trackEntity.get(Track_.NAME), onlinePlatformEntity.get(OnlinePlatform_.NAME), trackInPlaylist.get(TrackInPlaylist_.QUANTITY));
        query.select(selection);

        // how to manage sort ? (from Order spring to entity sort clause ?)
        TypedQuery<MyDTO> q = entityManager.createQuery(query);
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        return q.getResultList();
    }

    private Predicate applyStringFilter(CriteriaBuilder builder, From from , String field, String value, FilterType type) {
        final Path<String> path = from.get(field);
        final Predicate predicate;
        if(type == FilterType.LIKE) {
            predicate = builder.like(path, "%"+value+"%");
        }else {
            predicate = builder.equal(path, value);
        }
        return predicate;
    }


}
