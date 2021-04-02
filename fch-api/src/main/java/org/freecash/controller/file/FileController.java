package org.freecash.controller.file;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.freecash.config.FileConfig;
import org.freecash.constant.ConstantKey;
import org.freecash.domain.FchFile;
import org.freecash.domain.FchUser;
import org.freecash.service.FchFileService;
import org.freecash.utils.HttpResult;
import org.freecash.utils.SnowflakeIdWorker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;

@CrossOrigin(maxAge = 3600)
@Api(value = "文件存储接口")
@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileConfig fileConfig;
    private final FchFileService fchFileService;


    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public HttpResult<Integer> upload(@RequestParam("file") MultipartFile file, HttpSession session) throws Exception{
        FchUser user = (FchUser)session.getAttribute(ConstantKey.SESSION_USER);
        String fileName = file.getOriginalFilename();
        String newFileName = SnowflakeIdWorker.getUUID()+"." + FilenameUtils.getExtension(fileName);
        IOUtils.copy(file.getInputStream(), new FileOutputStream(fileConfig.getPath()+ File.separator + newFileName));
        FchFile fchFile = FchFile.builder().fileName(fileName).filePath(newFileName).username(user.getUsername())
                .build();
        fchFile = fchFileService.add(fchFile);
        return HttpResult.SUCCESS(fchFile.getPid());
    }

    @ApiOperation(value = "下载文件")
    @PostMapping("download")
    public void upload(Integer id, HttpServletResponse response) throws Exception{
        FchFile fchFile = fchFileService.get(id);
        File f = new File(fileConfig.getPath()+ File.separator + fchFile.getFilePath());
        response.setHeader("Content-Disposition", "attachment; filename="+fchFile.getFileName());
        IOUtils.copy(new FileInputStream(f), response.getOutputStream());
    }

    @ApiOperation(value = "删除文件")
    @PostMapping("remove")
    public HttpResult<String> remove(Integer id, HttpSession session) throws Exception{
        FchUser user = (FchUser)session.getAttribute(ConstantKey.SESSION_USER);
        FchFile fchFile = fchFileService.get(id);
        if(Objects.isNull(fchFile)){
            return HttpResult.FAIL(-1,"删除文件不存在","");
        }
        if(!Objects.equals(user.getNickname(), fchFile.getUsername())){
            return HttpResult.FAIL(-2,"不能删除不属于用户自己的文件","");
        }
        File f = new File(fileConfig.getPath()+ File.separator + fchFile.getFilePath());
        f.deleteOnExit();

        fchFileService.delete(fchFile);
        return HttpResult.SUCCESS("");
    }
}
