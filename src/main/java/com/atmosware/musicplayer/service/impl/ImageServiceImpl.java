package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Image;
import com.atmosware.musicplayer.repository.ImageRepository;
import com.atmosware.musicplayer.service.ImageService;
import com.atmosware.musicplayer.util.result.DataResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    private final String FOLDER_PATH="C:/Spring&JavaProject/musicPlayer/img/";
    @Override
    public DataResult<Image> uploadImageToFileSystem(MultipartFile file) {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        Image image = repository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath).build());
        try {
            file.transferTo(new File(filePath));
        }catch (IOException e){
            throw new RuntimeException(""+e.getMessage());
        }

        return new DataResult<>("",image);
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName){
        Optional<Image> image = repository.findByName(fileName);
        String filePath = image.get().getFilePath();
        byte[] images = null;
        try {
           images = Files.readAllBytes(new File(filePath).toPath());
        }catch (IOException e){
            throw new RuntimeException(""+e.getMessage());
        }
        return images;
    }

    @Override
    public Image findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
