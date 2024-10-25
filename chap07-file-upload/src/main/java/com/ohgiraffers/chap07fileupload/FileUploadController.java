package com.ohgiraffers.chap07fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {

    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile, String singleFileDescription, Model model){
        System.out.println("singleFile = " + singleFile);
        /*
        * StandardMultipartHttpServletRequest
        * - spring 에서 multipart 요청을 처리하기 위한 클래스
        *
        * $StandardMultipartFile@3721bcdf
        * - 업로드된 파일을 나타내는 객체
        * - 실제 파일 데이터에 대한 접근을 제공한다.
        * */
        System.out.println("singleFileDescription = " + singleFileDescription);

        // 파일을 저장할 경로 지정
        String filePath = "C:/uploads/single";
        File fileDir = new File(filePath);

        if(!fileDir.exists()){
            fileDir.mkdirs(); // 경로가 없으면 생성한다.
        }


        // 사용자가 업로드한 원본 파일 이름
        String originFileName = singleFile.getOriginalFilename();

        // 확장자 추출
        String ext = originFileName.substring(originFileName.lastIndexOf("."));

        // UUID.randomUUID() 고유 식별자 생성
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        // 업로드된 파일을 지정된 경로와 고유한 파일 이름으로 저장한다.
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));
            // 여기에 DB 저장 로직 추가하면 됨.

            model.addAttribute("message", "파일 업로드 성공!");
            model.addAttribute("img", "/img/single/" + savedName);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "파일 업로드 실패!");
        }

        return "result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multiFile, String multiFileDescription, Model model){
        System.out.println("multiFile = " + multiFile);

        String[] multiSplit = multiFileDescription.split(",");
        model.addAttribute("description", multiSplit);
        for(String txt:multiSplit){
            System.out.println("txt = " + txt);
        }
        

        String filePath = "C:/uploads/multi";
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        List<String> imgList = new ArrayList<>();
   
        for(MultipartFile file : multiFile){
            String originFileName = file.getOriginalFilename();
            String ext = originFileName.substring(originFileName.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
            try {
                file.transferTo(new File(filePath+"/"+savedName));

                model.addAttribute("message", "멀티플 업로드 성공!");

                imgList.add("/img/multi/" + savedName);
                model.addAttribute("images", imgList);

            } catch (IOException e) {
                /*throw new RuntimeException(e);*/

                e.printStackTrace();
                model.addAttribute("message", "멀티플 업로드 실패!");
            }
        }




        return "result";

    }
}
