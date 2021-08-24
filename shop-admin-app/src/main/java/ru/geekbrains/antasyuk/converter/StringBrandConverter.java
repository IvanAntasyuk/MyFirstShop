package ru.geekbrains.antasyuk.converter;

import org.springframework.stereotype.Component;
import ru.geekbrains.antasyuk.dto.BrandDto;
import org.springframework.core.convert.converter.Converter;

@Component
public class StringBrandConverter implements Converter<String, BrandDto> {

    @Override
    public BrandDto convert(String s) {
        String[] arr = s.split(";");
        return new BrandDto(Long.parseLong(arr[0]), arr[1]);
    }
}