package ru.geekbrains.antasyuk.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.geekbrains.antasyuk.dto.CategoryDto;
import ru.geekbrains.antasyuk.models.Category;

@Component
public class StringCategoryConverter implements Converter<String, CategoryDto> {

    @Override
    public CategoryDto convert(String s) {
        String[] arr = s.split(";");
        return new CategoryDto(Long.parseLong(arr[0]), arr[1]);
    }
}