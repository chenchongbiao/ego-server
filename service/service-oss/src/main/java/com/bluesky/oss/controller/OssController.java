package com.bluesky.oss.controller;


import com.bluesky.oss.service.OssService;
import com.bluesky.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description="阿里云文件管理")
@RestController//相当于@Controller+@ResponseBody两个注解的结合，返回json数据不需要在方法前面加@ResponseBody注解了
@RequestMapping("/ossservice/fileoss")//访问路径
//@CrossOrigin//允许跨域
public class OssController {

    @Autowired
    private OssService ossService;
    //上传头像的方法

    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
//    @ApiOperation(value = "文件上传")
//    @PostMapping("upload")
//    public R uploadOssFile(@ApiParam(name = "file", value = "文件", required = true)
//                               @RequestParam("file") MultipartFile file) {
//
//        String uploadUrl = ossService.uploadFileAvatar(file);
//        //返回r对象
//        return R.ok().message("文件上传成功").data("url", uploadUrl);
//    }

}
