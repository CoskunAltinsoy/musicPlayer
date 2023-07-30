package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.SongConverter;
import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Favorite;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.FavoriteRepository;
import com.atmosware.musicplayer.service.FavoritesService;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoriteRepository repository;
    private final UserService userService;
    private final SongService songService;
    private final AuthenticationFacade authenticationFacade;
    private final SongConverter songConverter;

    @Override
    public Result createSongToFavorite(Long songId, Long favoriteId) {
        String email = authenticationFacade.getUsername();
        User user = userService.findByEmail(email);
        Song song = songService.findById(songId);
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.getSongs().add(song);
        return new Result(Message.Favorite.successful);
    }

    @Override
    public Result deleteSongToFavorite(Long songId, Long favoriteId) {
        String email = authenticationFacade.getUsername();
        User user = userService.findByEmail(email);
        Song song = songService.findById(songId);
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.getSongs().remove(song);
        return new Result(Message.Favorite.successful);
    }

    @Override
    public DataResult<FavoriteResponse> getById(Long id) {
        Favorite favorite = repository.findById(id).orElseThrow();
        Set<SongResponse> songResponses = songConverter.convertToResponseList(favorite.getSongs());
        FavoriteResponse favoriteResponse = new FavoriteResponse();
        favoriteResponse.setSongs(songResponses);
        return new DataResult<FavoriteResponse>(favoriteResponse);
    }

    @Override
    public DataResult<List<FavoriteResponse>> getAll() {
        List<Favorite> favorites = repository.findAll();
        var responses = favorites.stream()
                .map(favorite -> {
                    Set<SongResponse> songResponses = songConverter.convertToResponseList(favorite.getSongs());
                    FavoriteResponse favoriteResponse = new FavoriteResponse();
                    favoriteResponse.setSongs(songResponses);
                    return favoriteResponse;
                })
                .toList();
        return new DataResult<List<FavoriteResponse>>(Message.Favorite.successful,responses);
    }
}
