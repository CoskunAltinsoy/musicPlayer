package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.GenreConverter;
import com.atmosware.musicplayer.converter.SongConverter;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Image;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.repository.SongRepository;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.service.ImageService;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final AlbumService albumService;
    private final GenreService genreService;
    private final ImageService imageService;
    private final SongConverter converter;
    private final GenreConverter genreConverter;

    @Override
    public Result create(SongRequest request) {
        Album album = albumService.findById(request.getAlbumId());
        Set<Genre> genres = setGenreIds(request);
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        Song song = converter.convertToEntity(request);
        song.setId(0L);
        song.setImage(image);
        song.setAlbum(album);
        song.setGenres(genres);
        song.setCreatedDate(LocalDateTime.now());
        repository.save(song);
        return new Result(Message.Song.SUCCESSFUL);
    }

    @Override
    public Result update(SongRequest request, Long id) {
        checkIfSongExistsById(id);
        Album album = albumService.findById(request.getAlbumId());
        Set<Genre> genres = setGenreIds(request);
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        Song song = repository.findById(id).orElseThrow();
        song.setName(request.getName());
        song.setImage(image);
        song.setAlbum(album);
        song.setGenres(genres);
        song.setUpdatedDate(LocalDateTime.now());
        repository.save(song);
        return new Result(Message.Song.SUCCESSFUL);
    }

    @Override
    public Result delete(Long id) {
        checkIfSongExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Song.SUCCESSFUL);
    }

    @Override
    public DataResult<SongResponse> getById(Long id) {
        checkIfSongExistsById(id);
        Song song = repository.findById(id).orElseThrow();
        var albumResponse = albumService.getById(song.getAlbum().getId());
        Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
        byte[] byteImage = imageService.downloadImageFromFileSystem(song.getImage().getFilePath());
        SongResponse songResponse = converter.convertToResponse(song);
        songResponse.setImage(byteImage);
        songResponse.setAlbum(albumResponse.getData());
        songResponse.setGenres(genreResponses);
        return new DataResult<SongResponse>(songResponse);
    }

    @Override
    public DataResult<List<SongResponse>> getAll(){
        List<Song> songs = repository.findAll();
        var responses = songs
                .stream()
                .map(song -> {
                    var albumResponse = albumService.getById(song.getAlbum().getId());
                    Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
                    byte[] byteImage = imageService.downloadImageFromFileSystem(song.getImage().getFilePath());
                    SongResponse songResponse = converter.convertToResponse(song);
                    songResponse.setImage(byteImage);
                    songResponse.setAlbum(albumResponse.getData());
                    songResponse.setGenres(genreResponses);
                    return songResponse;
                })
                .toList();
        return new DataResult<List<SongResponse>>(Message.Song.SUCCESSFUL,responses);
    }

    @Override
    public DataResult<List<SongResponse>> getAllByAlbum(Long albumId){
        List<Song> songs = repository.findByAlbum_Id(albumId);
        var responses = songs
                .stream()
                .map(song -> {
                    var albumResponse = albumService.getById(song.getAlbum().getId());
                    Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
                    byte[] byteImage = imageService.downloadImageFromFileSystem(song.getImage().getFilePath());
                    SongResponse songResponse = converter.convertToResponse(song);
                    songResponse.setImage(byteImage);
                    songResponse.setAlbum(albumResponse.getData());
                    songResponse.setGenres(genreResponses);
                    return songResponse;
                })
                .toList();
        return new DataResult<List<SongResponse>>(Message.Song.SUCCESSFUL,responses);
    }

    @Override
    public Song findById(Long id) {
        checkIfSongExistsById(id);
        return repository.findById(id).orElseThrow();
    }

    private Set<Genre> setGenreIds(SongRequest request) {
        return request.getGenreIds().stream()
                .map(genreId -> {
                    return genreService.findById(genreId);
                })
                .collect(Collectors.toSet());
    }
    private void checkIfSongExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Song.NOT_EXIST);
        }
    }
}
