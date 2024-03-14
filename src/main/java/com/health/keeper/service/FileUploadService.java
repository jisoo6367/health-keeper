package com.health.keeper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    @Value("${imgbb.api.key}") // application.properties 또는 application.yml에서 API 키를 관리할 수 있도록 설정
    private String apiKey ;

    private final RestTemplate restTemplate;

    public FileUploadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String uploadImageToImgBB(MultipartFile file) throws IOException {

        // 임시 파일 생성
        File tempFile = File.createTempFile("temp", null);
        // 임시 파일로 MultipartFile의 내용을 복사
        file.transferTo(tempFile);

//        // 파일을 임시 디렉토리에 저장
//        String tempDir = System.getProperty("java.io.tmpdir");
//        String filePath = tempDir + File.separator + file.getOriginalFilename();
//        file.transferTo(new File(filePath));

//        // 업로드할 파일의 바이트 배열을 생성
//        byte[] fileContent = file.getBytes();
//
//        // 파일 이름 가져오기
//        String fileName = file.getOriginalFilename();

        // ImgBB 업로드 API 엔드포인트 URL
        String apiUrl = "https://api.imgbb.com/1/upload";

        // 업로드할 파일을 ByteArrayResource로 변환
        //byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        byte[] fileContent = Files.readAllBytes(tempFile.toPath());
        Resource fileAsResource = new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
                //return fileName;
            }
        };

        // 업로드할 파일 및 API 키를 포함하는 multipart 요청 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", fileAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(body, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // 업로드 결과 확인
        if (response.getStatusCode() == HttpStatus.OK) {
            // API가 제공하는 이미지 URL 가져오기
            return response.getBody();
        } else {
            // 업로드 실패 시 예외 처리
            throw new RuntimeException("Image upload failed with status code: " + response.getStatusCode());
        }
    }
}

