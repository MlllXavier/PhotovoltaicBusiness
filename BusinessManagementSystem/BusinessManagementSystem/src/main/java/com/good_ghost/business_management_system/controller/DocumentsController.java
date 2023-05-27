package com.good_ghost.business_management_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.good_ghost.business_management_system.common.Result;
import com.good_ghost.business_management_system.entity.Documents;
import com.good_ghost.business_management_system.mapper.DocumentsMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/documents")
public class DocumentsController {
    @Resource
    DocumentsMapper documentsMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Documents> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Documents::getDocumentName, search).or()
                .like(Documents::getDocumentNumber, search).or()
                .like(Documents::getNotes, search);
        Page<Documents> documentsPage = documentsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new Result<>(documentsPage);
    }

    @PostMapping("/add")
    public Result<?> add(@RequestParam(value = "file", required = false) MultipartFile file,
                      @RequestParam("documentNumber") String documentNumber,
                      @RequestParam("documentName") String documentName,
                      @RequestParam("documentTime") String documentTime,
                      @RequestParam("notes") String notes) throws IOException {
        // 文件上传路径
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\documents\\";  //需要加项目名Change
        File dest0 = new File(templatePath);
        String fileName = file.getOriginalFilename();
        File dest = new File(dest0, fileName);
        file.transferTo(dest);
        Documents documents = new Documents();
        documents.setDocumentName(documentName);
        documents.setDocumentNumber(documentNumber);
        documents.setDocumentPath(fileName);
        documents.setNotes(notes);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        documents.setDocumentTime(LocalDateTime.parse(documentTime));
        documentsMapper.insert(documents);
        return new Result<>("");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id) throws IOException {
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\documents\\";  //需要加项目名Change
        Documents documents = documentsMapper.selectById(id);
        File delete = new File(templatePath+documents.getDocumentPath());
        if(!delete.delete()){
            return new Result<>("删除失败");
        }
        documentsMapper.deleteById(id);
        return new Result<>("删除成功");
    }

}
