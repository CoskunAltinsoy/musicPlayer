package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.model.entity.Image;
import com.atmosware.musicplayer.util.result.DataResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    DataResult<Image> uploadImageToFileSystem(MultipartFile file);
    byte[] downloadImageFromFileSystem(String fileName);
    Image findById(Long id);
}
