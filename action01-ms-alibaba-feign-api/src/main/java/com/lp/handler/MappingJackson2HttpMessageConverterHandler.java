package com.lp.handler;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName com.lp.handler.MappingJackson2HttpMessageConverterHandler
 * @Deacription 暂时还是不能解决feign调用返回Object类型报错的情况
 * @Author LP
 * @Date 2021/2/18 21:47
 * @Version 1.0
 **/
public class MappingJackson2HttpMessageConverterHandler extends MappingJackson2HttpMessageConverter {
    public MappingJackson2HttpMessageConverterHandler(){
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);  //加入text/html类型的支持
        setSupportedMediaTypes(mediaTypes);// tag6
    }
}

