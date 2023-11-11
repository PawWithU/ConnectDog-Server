package com.pawwithu.connectdog.common.converter;

import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class PostStatusConverter implements Converter<String, PostStatus> {
    @Override
    public PostStatus convert(String source) {
        if (!StringUtils.hasText(source)) return null;
        return PostStatus.create(source);
    }

}
