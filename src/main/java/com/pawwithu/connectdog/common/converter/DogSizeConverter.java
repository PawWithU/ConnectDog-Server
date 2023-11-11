package com.pawwithu.connectdog.common.converter;

import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DogSizeConverter implements Converter<String, DogSize> {
    @Override
    public DogSize convert(String source) {
        if (!StringUtils.hasText(source)) return null;
        return DogSize.create(source);
    }
}