package com.rory.web.controller;

import com.rory.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());

        String path = FileController.class.getClassLoader().getResource("").toString();
        File localFile = new File(path.substring(path.indexOf(":") + 1), new Date().getTime() + ".txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = FileController.class.getClassLoader().getResource("").toString();

        InputStream inputStream = new FileInputStream(new File(path.substring(path.indexOf(":") + 1), id + ".txt"));
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=test.txt");
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();

    }
}
