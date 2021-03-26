package org.freecash.test;

import org.apache.commons.io.IOUtils;
import org.freecash.component.FreeDriveComponent;
import org.freecash.web.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreeDriveTests {

    @Autowired
    private FreeDriveComponent freeDriveComponent;
    @Test
    public void testPut() throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
       IOUtils.copy(new FileInputStream(new File("D:\\桌面\\freecash\\filedrive商业模式建议.md")),out);
        PutRequest request = PutRequest.builder()
                .file(new File("D:\\桌面\\freecash\\filedrive商业模式建议.md")).build();
        PutResponse response = freeDriveComponent.put(request);
        System.out.println(response.getCode());
        System.out.println(response.getMsg());
        if(response.getCode() == 0){
            System.out.println(response.getData().getHash());
        }
    }

    @Test
    public void testGet() throws Exception{
        GetResponse response = freeDriveComponent.get(GetRequest.builder().hash("f1ab1bf99d49a22d20925ca8b336b13e7814d6d8a42f1dd0867781078fa06b40").build());
        System.out.println(response.getCode());
        System.out.println(response.getMsg());
        if(response.getCode() == 0){
            IOUtils.write(response.getData().getFile(), new FileOutputStream("D:\\桌面\\1.txt"));
        }
    }

    @Test
    public void testCheck() throws Exception{
        CheckResponse response = freeDriveComponent.check(CheckRequest.builder().hash("f1ab1bf99d49a22d20925ca8b336b13e7814d6d8a42f1dd0867781078fa06b40").build());
        System.out.println(response.getCode());
        System.out.println(response.getMsg());
        System.out.println(response.isExist());
    }
}
