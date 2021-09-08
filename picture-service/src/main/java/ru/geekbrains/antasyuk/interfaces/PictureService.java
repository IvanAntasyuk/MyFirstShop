package ru.geekbrains.antasyuk.interfaces;

import java.io.IOException;
import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    String createPicture(byte[] picture);

    void deletePicture(Long id) throws IOException;

}