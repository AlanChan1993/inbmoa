package com.infinitus.bms_oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/StoreExcelColler")
public class StoreExcelColler<Workbook> {

    @RequestMapping("/getStroeUrl")
    private String  getStroeUrl(@RequestParam("file") MultipartFile file){
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }



}
