package ru.geekbrains.antasyuk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.interfaces.PictureRepasitory;
import ru.geekbrains.antasyuk.models.Picture;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceFileImpl implements PictureService {
    @Value("${picture.storage.path}")
    private String storagePath;

    private final PictureRepasitory pictureRepasitory;
    private static final Logger logger = LoggerFactory.getLogger(PictureServiceFileImpl.class);

    @Autowired
    public PictureServiceFileImpl(PictureRepasitory pictureRepasitory) {
        this.pictureRepasitory = pictureRepasitory;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return pictureRepasitory.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        return pictureRepasitory.findById(id)
                .map(pic -> Paths.get(storagePath, pic.getStorageUUID()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException ex) {
                        logger.error("Can't read file for picture with id " + id, ex);
                        throw new RuntimeException(ex);
                    }
                });
    }


    @Override
    public String createPicture(byte[] picture) {
        String fileName = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(storagePath, fileName))) {
            os.write(picture);
        } catch (IOException ex) {
            logger.error("Can't write file", ex);
            throw new RuntimeException(ex);
        }
        return fileName;
    }
}
