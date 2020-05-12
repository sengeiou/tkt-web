package com.mtl.cypw.test;

import com.mtl.cypw.common.utils.FileUploadTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author tang.
 * @date 2019/12/10.
 */
public class FileTest extends BaseTest {

    @Autowired
    private FileUploadTemplate fileUploadTemplate;

//    @Test
//    public void create(){
//        fileService.createBucket();
//    }

    @Test
    public void fileUpload() throws InterruptedException {
        File file1 = new File("D:\\Users\\Pictures\\Saved Pictures\\6.png");
        String url1 = fileUploadTemplate.fileUpload(file1);
        System.out.println("url1=" + url1);
    }
}
