package com.zhuhaoran.rebatemall.utils;

import com.zhuhaoran.rebatemall.viewObject.ResultVo;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZhuHaoran
 * @className SendRequestUtils
 * @date 2019/4/25
 * @description
 */
public class SendRequestUtils {

    public static ResultVo sendPostRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<ResultVo> responseEntity = restTemplate.exchange(url, method, requestEntity, ResultVo.class);

        return responseEntity.getBody();
    }
}
