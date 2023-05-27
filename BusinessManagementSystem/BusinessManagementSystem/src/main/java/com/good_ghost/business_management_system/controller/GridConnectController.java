package com.good_ghost.business_management_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.good_ghost.business_management_system.common.Result;
import com.good_ghost.business_management_system.common.ResultCode;
import com.good_ghost.business_management_system.entity.GridConnect;
import com.good_ghost.business_management_system.entity.Subsidy;
import com.good_ghost.business_management_system.mapper.GridConnectMapper;
import com.good_ghost.business_management_system.service.IGridConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xavier
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/grid-connect")
public class GridConnectController {

    @Autowired
    IGridConnectService gridConnectService;

    @Resource
    GridConnectMapper gridConnectMapper;

    @GetMapping("getOne")
    public  Result getById(int id){
        return new Result(gridConnectMapper.selectById(id));
    }

    //获取紧急业务接口，返回时限剩余不到一天的业务
    @GetMapping("emergent")
    public Result getEmergentBusiness(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize){
        LambdaQueryWrapper<GridConnect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GridConnect::getIsComplete, 0);
        List<GridConnect> resultList = new ArrayList<>();
        List<GridConnect> gridConnectList = gridConnectMapper.selectList(wrapper);
        for (int i = 0; i < gridConnectList.size(); i++){
            GridConnect gridConnect = gridConnectList.get(i);
            LocalDateTime now = LocalDateTime.now();
            if (gridConnect.getAddApply() != null){
                if (gridConnect.getAddSurvey() != null){
                    if (gridConnect.getAddConfirm() != null){
                        if (gridConnect.getAddRecord() != null){
                            if (gridConnect.getConnectApply() != null){
                                if (gridConnect.getConnectDevice() != null){
                                    if (gridConnect.getConnectSign() != null){
                                        if (gridConnect.getConnectCheck() != null){

                                        } else {
                                            Duration duration = Duration.between(gridConnect.getConnectApply(), now);
                                            long days = duration.toDays();
                                            if(days >= 9){
                                                resultList.add(gridConnect);
                                            }
                                            continue;
                                        }
                                    } else {
                                        Duration duration = Duration.between(gridConnect.getConnectApply(), now);
                                        long days = duration.toDays();
                                        if(days >= 7){
                                            resultList.add(gridConnect);
                                        }
                                        continue;
                                    }
                                } else {
                                    Duration duration = Duration.between(gridConnect.getConnectApply(), now);
                                    long days = duration.toDays();
                                    if(days >= 7){
                                        resultList.add(gridConnect);
                                    }
                                    continue;
                                }
                            }
                        }
                    } else {
                        Duration duration = Duration.between(gridConnect.getAddApply(), now);
                        long days = duration.toDays();
                        if(days >= 24){
                            resultList.add(gridConnect);
                        }
                        continue;
                    }
                } else {
                    Duration duration = Duration.between(gridConnect.getAddApply(), now);
                    long days = duration.toDays();
                    if(days >= 1){
                        resultList.add(gridConnect);
                    }
                    continue;
                }
            }
        }

        // 分页代码片段
        // T表示对象实体 list是所要处理的列表数据
        Page<GridConnect> page = new Page<>(pageNum,pageSize);
        // 当前页第一条数据在List中的位置
        int start = (int)((page.getCurrent() - 1) * page.getSize());
        // 当前页最后一条数据在List中的位置
        int end = (int)((start + page.getSize()) > resultList.size() ? resultList.size() : (page.getSize() * page.getCurrent()));
        page.setRecords(new ArrayList<>());
        if (page.getSize()*(page.getCurrent()-1) <= page.getTotal()) {
            // 分隔列表 当前页存在数据时 设置
            page.setRecords(resultList.subList(start, end));
        }
        page.setTotal(resultList.size());
        return new Result(ResultCode.SUCCESS, page);
    }

    //获取预计完成时间
    @GetMapping("getFinishTime")
    public Result getFinishTime(int id){
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        if (gridConnect.getAddApply() != null){
            if (gridConnect.getConnectApply() != null){
                localDateTime = gridConnect.getConnectApply().plusDays(10);
            } else {
                localDateTime = gridConnect.getAddApply().plusDays(37);
            }
        }
        return new Result(ResultCode.SUCCESS, localDateTime);
    }

    @PostMapping("/completeBusiness")
    public boolean completeBusiness(@RequestParam("id") Integer id,
                                    @RequestParam("process") Integer index){
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        LocalDateTime localDateTime = LocalDateTime.now();
        switch (index){
            case 1:
                gridConnect.setAddApply(localDateTime);
                break;
            case 2:
                gridConnect.setAddSurvey(localDateTime);
                break;
            case 3:
                gridConnect.setAddConfirm(localDateTime);
                break;
            case 4:
                gridConnect.setAddRecord(localDateTime);
                break;
            case 5:
                gridConnect.setConnectApply(localDateTime);
                break;
            case 6:
                gridConnect.setConnectDevice(localDateTime);
                break;
            case 7:
                gridConnect.setConnectSign(localDateTime);
                break;
            case 8:
                if (gridConnect.getAddApply() == null || gridConnect.getAddSurvey() == null || gridConnect.getAddConfirm() == null || gridConnect.getAddRecord() == null || gridConnect.getConnectApply() == null || gridConnect.getConnectDevice() == null || gridConnect.getConnectSign() == null){
                    return false;
                }
                gridConnect.setConnectCheck(localDateTime);
                gridConnect.setIsComplete(1);
                break;
        }
        try{
            gridConnectMapper.updateById(gridConnect);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PutMapping("/addNotes")
    public Result addNotes(String notes, String houseId){
        GridConnect gridConnect = gridConnectMapper.selectById(houseId);
        gridConnect.setNotes(notes);
        gridConnectMapper.updateById(gridConnect);
        return new Result("");
    }

    @PostMapping
    public Result<?> save(@RequestBody GridConnect gridConnect){
        gridConnectMapper.insert(gridConnect);
        return new Result<>("");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable String id){
        gridConnectMapper.deleteById(id);
        return new Result<>("");
    }

    @PutMapping
    public Result<?> update(@RequestBody GridConnect gridConnect){
        gridConnectMapper.updateById(gridConnect);
        return new Result<>("");
    }

    //参数criteria：0为获取未完成业务的记录；1为获取已完成业务的记录
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") String criteria){
        LambdaQueryWrapper<GridConnect> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GridConnect::getIsComplete, criteria);
        if(!search.equals(""))
            wrapper.eq(GridConnect::getHouseId, search);
        Page<GridConnect> gridConnectPage = gridConnectMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new Result<>(gridConnectPage);
    }

    /**
     * 上传文件，参数index表示文件的序号，该序号作为文件的名字保存
     *
     * 1.1《分布式光伏发电项目申请书》
     * 1.2《居民家庭分布式光伏发电项目并网申请表》
     * 1.3《村委（街道办）项目同意书》
     * 1.4 房产证或者宅基地证的原件及复印件（出证单位盖章）
     * 1.5 户口本原价及复印件
     * 1.6 客户居民身份证原件及复印件
     * 1.7 本人银行卡原件及复印件
     * 1.8《项目投产真实性承诺书》
     * 1.9 客户提供产品的合格证及3C证书
     * 1.10 购买产品真实有效票据（复印件）
     * 2.《关于××项目接入电网意见的函》
     * 3.《晋中市个人建设分布式光伏发电项目备案表》
     * 4.《分布式电源并网验收与并网调试申请表》
     * 5.《用电工作票》
     * 6.《分布式光伏发电项目低压发用电合同》
     * 7.1《分布式光伏发电项目并网验收意见单》
     * 7.2《分布式电源并网验收意见会签单》
     * */
    @PutMapping("/uploadFile")
    public Result uploadFile(MultipartFile file, String houseId, String index){
        if(!file.isEmpty()){
            GridConnect gridConnect = gridConnectMapper.selectById(houseId);
            //将文件保存到项目文件夹下的：files/时间/户号/
            String uploadPath = System.getProperty("user.dir") + "\\files\\" + gridConnect.getFiles() + "\\" + houseId;
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            String filename = index +suffixName;
            File localFile = new File(uploadPath + "\\" + filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                /**
                 * 这里应该把filename保存到数据库,供前端访问时使用
                 */
                return new Result(ResultCode.SUCCESS);//上传成功，返回保存的文件地址
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("上传失败");
                return new Result(ResultCode.FAILED);
            }
        }else{
            System.out.println("文件为空");
            return new Result(ResultCode.ERROR);
        }
    }

    @PostMapping("/uploading1")
    public @ResponseBody
    String uploadFile(@RequestParam(value = "file11", required = false) MultipartFile file11,
                      @RequestParam(value = "file12", required = false) MultipartFile file12,
                      @RequestParam(value = "file13", required = false) MultipartFile file13,
                      @RequestParam(value = "file14", required = false) MultipartFile file14,
                      @RequestParam(value = "file15", required = false) MultipartFile file15,
                      @RequestParam(value = "file16", required = false) MultipartFile file16,
                      @RequestParam(value = "file17", required = false) MultipartFile file17,
                      @RequestParam(value = "file18", required = false) MultipartFile file18,
                      @RequestParam("id") Integer id,
                      @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";  //需要加项目名Change
        File dest0 = new File(templatePath);
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        String process1Have = gridConnect.getProcess1Number();
        String process1Name = gridConnect.getProcess1Name();
        int num = 0;
        if (!process1Have.equals("")){
            num = process1Have.length();
        }
        try {
            if(file11 != null && !process1Have.contains("1")){
                String fileName11 = file11.getOriginalFilename();
                File dest11 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName11);
                if (!dest11.exists()) {
                    dest11.mkdirs();
                }
                file11.transferTo(dest11);
                num += 1;
                process1Have += "1";
                process1Name += fileName11 + ",";
            }
            if(file12 != null && !process1Have.contains("2")){
                String fileName12 = file12.getOriginalFilename();
                File dest12 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName12);
                if (!dest12.exists()) {
                    dest12.mkdirs();
                }
                file12.transferTo(dest12);
                num += 1;
                process1Have += "2";
                process1Name += fileName12 + ",";
            }
            if(file13 != null && !process1Have.contains("3")){
                String fileName13 = file13.getOriginalFilename();
                File dest13 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName13);
                if (!dest13.exists()) {
                    dest13.mkdirs();
                }
                file13.transferTo(dest13);
                num += 1;
                process1Have += "3";
                process1Name += fileName13 + ",";
            }
            if(file14 != null && !process1Have.contains("4")){
                String fileName14 = file14.getOriginalFilename();
                File dest14 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName14);
                if (!dest14.exists()) {
                    dest14.mkdirs();
                }
                file14.transferTo(dest14);
                num += 1;
                process1Have += "4";
                process1Name += fileName14 + ",";
            }
            if(file15 != null && !process1Have.contains("5")){
                String fileName15 = file15.getOriginalFilename();
                File dest15 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName15);
                if (!dest15.exists()) {
                    dest15.mkdirs();
                }
                file15.transferTo(dest15);
                num += 1;
                process1Have += "5";
                process1Name += fileName15 + ",";
            }
            if(file16 != null && !process1Have.contains("6")){
                String fileName16 = file16.getOriginalFilename();
                File dest16 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName16);
                if (!dest16.exists()) {
                    dest16.mkdirs();
                }
                file16.transferTo(dest16);
                num += 1;
                process1Have += "6";
                process1Name += fileName16 + ",";
            }
            if(file17 != null && !process1Have.contains("7")){
                String fileName17 = file17.getOriginalFilename();
                File dest17 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName17);
                if (!dest17.exists()) {
                    dest17.mkdirs();
                }
                file17.transferTo(dest17);
                num += 1;
                process1Have += "7";
                process1Name += fileName17 + ",";
            }
            if(file18 != null && !process1Have.contains("8")){
                String fileName18 = file18.getOriginalFilename();
                File dest18 = new File(dest0, houseId + File.separator + "1并网申请" + File.separator + fileName18);
                if (!dest18.exists()) {
                    dest18.mkdirs();
                }
                file18.transferTo(dest18);
                num += 1;
                process1Have += "8";
                process1Name += fileName18 + ",";
            }
            if(num>=1){
                LocalDateTime now = LocalDateTime.now();
                gridConnect.setProcess1Number(process1Have);
                gridConnect.setProcess1Name(process1Name);
                if(process1Have.contains("1")&&process1Have.contains("2")&&process1Have.contains("3")&&process1Have.contains("4")&&process1Have.contains("5")){
                    gridConnect.setAddApply(now);
                }
                gridConnectMapper.updateById(gridConnect);
                return "上传成功";
            }
            return "上传失败";
        } catch (Exception e) {
            return "上传失败";
        }
    }
    @DeleteMapping("/deleteFile1")
    public Integer deleteFile1(@RequestParam("fileList") String fileList,
                               @RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        String process1Have = gridConnect.getProcess1Number();
        String process1Name = gridConnect.getProcess1Name();
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\1并网申请\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                if(path.contains("申请书") && fileList.contains("1")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("1", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("申请表") && fileList.contains("2")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("2", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("同意书") && fileList.contains("3")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("3", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("房产证") && fileList.contains("4")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("4", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("承诺书") && fileList.contains("5")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("5", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("身份证") && fileList.contains("6")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("6", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("银行卡") && fileList.contains("7")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("7", "");
                    process1Name = process1Name.replace(path+",", "");
                }
                else if(path.contains("户口本") && fileList.contains("8")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process1Have = process1Have.replace("8", "");
                    process1Name = process1Name.replace(path+",", "");
                }
            }
            gridConnect.setProcess1Number(process1Have);
            gridConnect.setProcess1Name(process1Name);
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading2")
    public @ResponseBody
    String uploadFile2(@RequestParam("file2") MultipartFile file2,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        String fileName2 = file2.getOriginalFilename();
        File dest0 = new File(templatePath);
        File dest2 = new File(dest0, houseId + File.separator + "2接入方案设计及确认" + File.separator + fileName2);
        try {
            if (!dest2.exists()) {
                dest2.mkdirs();
            }
            file2.transferTo(dest2);
        } catch (Exception e) {
            return "上传失败";
        }
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setAddSurvey(now);
            gridConnect.setProcess2Name(fileName2);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile2")
    public Integer deleteFile2(@RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\2接入方案设计及确认\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                File delete = new File(templatePath+path);
                if(!delete.delete()){
                    return 0;
                }
            }
            gridConnect.setProcess2Name("");
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading3")
    public @ResponseBody
    String uploadFile3(@RequestParam("file3") MultipartFile file3,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        File dir = new File("");
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        String fileName2 = file3.getOriginalFilename();
        File dest0 = new File(templatePath);
        File dest2 = new File(dest0, houseId + File.separator + "3项目备案" + File.separator + fileName2);
        try {
            if (!dest2.exists()) {
                dest2.mkdirs();
            }
            file3.transferTo(dest2);
        } catch (Exception e) {
            return "上传失败";
        }
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setAddConfirm(now);
            gridConnect.setProcess3Name(fileName2);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile3")
    public Integer deleteFile3(@RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\3项目备案\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                File delete = new File(templatePath+path);
                if(!delete.delete()){
                    return 0;
                }
            }
            gridConnect.setProcess3Name("");
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading4")
    public @ResponseBody
    String uploadFile4(@RequestParam("file4") MultipartFile file4,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        String fileName2 = file4.getOriginalFilename();
        File dest0 = new File(templatePath);
        File dest2 = new File(dest0, houseId + File.separator + "4并网验收与调试申请" + File.separator + fileName2);
        try {
            if (!dest2.exists()) {
                dest2.mkdirs();
            }
            file4.transferTo(dest2);
        } catch (Exception e) {
            return "上传失败";
        }
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setAddRecord(now);
            gridConnect.setProcess4Name(fileName2);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile4")
    public Integer deleteFile4(@RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\4并网验收与调试申请\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                File delete = new File(templatePath+path);
                if(!delete.delete()){
                    return 0;
                }
            }
            gridConnect.setProcess4Name("");
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading5")
    public @ResponseBody
    String uploadFile5(@RequestParam("file5") MultipartFile file5,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        String fileName2 = file5.getOriginalFilename();
        File dest0 = new File(templatePath);
        File dest2 = new File(dest0, houseId + File.separator + "5安装计量装置" + File.separator + fileName2);
        try {
            if (!dest2.exists()) {
                dest2.mkdirs();
            }
            file5.transferTo(dest2);
        } catch (Exception e) {
            return "上传失败";
        }
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setConnectApply(now);
            gridConnect.setProcess5Name(fileName2);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile5")
    public Integer deleteFile5(@RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\5安装计量装置\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                File delete = new File(templatePath+path);
                if(!delete.delete()){
                    return 0;
                }
            }
            gridConnect.setProcess5Name("");
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading6")
    public @ResponseBody
    String uploadFile6(@RequestParam("file6") MultipartFile file6,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        // 文件上传路径
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        String fileName2 = file6.getOriginalFilename();
        File dest0 = new File(templatePath);
        File dest2 = new File(dest0, houseId + File.separator + "6签署合同及协议" + File.separator + fileName2);
        try {
            if (!dest2.exists()) {
                dest2.mkdirs();
            }
            file6.transferTo(dest2);
        } catch (Exception e) {
            return "上传失败";
        }
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setConnectDevice(now);
            gridConnect.setProcess6Name(fileName2);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile6")
    public Integer deleteFile6(@RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\6签署合同及协议\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                File delete = new File(templatePath+path);
                if(!delete.delete()){
                    return 0;
                }
            }
            gridConnect.setProcess6Name("");
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading7")
    public @ResponseBody
    String uploadFile7(@RequestParam(value = "file71", required = false) MultipartFile file71,
                       @RequestParam(value = "file72", required = false) MultipartFile file72,
                       @RequestParam("id") Integer id,
                       @RequestParam("houseId") String houseId) throws IOException {
        System.out.println("开始处理流程7");
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\";
        File dest0 = new File(templatePath);
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        String process7Have = gridConnect.getProcess7Number();
        String process7Name = gridConnect.getProcess7Name();
        int num = 0;
        if (!process7Have.equals("")){
            num = process7Have.length();
        }
        try {
            if(file71 != null && !process7Have.contains("1")){
                String fileName71 = file71.getOriginalFilename();
                File dest71 = new File(dest0, houseId + File.separator + "7并网验收与调试" + File.separator + fileName71);
                if (!dest71.exists()) {
                    dest71.mkdirs();
                }
                file71.transferTo(dest71);
                num += 1;
                process7Have += "1";
                process7Name += fileName71 + ",";
            }
            if(file72 != null && !process7Have.contains("2")){
                String fileName72 = file72.getOriginalFilename();
                File dest72 = new File(dest0, houseId + File.separator + "7并网验收与调试" + File.separator + fileName72);
                if (!dest72.exists()) {
                    dest72.mkdirs();
                }
                file72.transferTo(dest72);
                num += 1;
                process7Have += "2";
                process7Name += fileName72 + ",";
            }
            if(num>=1){
                LocalDateTime now = LocalDateTime.now();
                try{
                    gridConnect.setConnectSign(now);
                    gridConnect.setProcess7Name(process7Name);
                    gridConnect.setProcess7Number(process7Have);
                    gridConnectMapper.updateById(gridConnect);
                    return "上传成功";
                }catch (Exception e){
                    return "上传失败";
                }
            }
            return "上传失败";
        } catch (Exception e) {
            return "上传失败";
        }
    }

    @DeleteMapping("/deleteFile7")
    public Integer deleteFile7(@RequestParam("fileList") String fileList,
                               @RequestParam("id") Integer id,
                               @RequestParam("houseId") String houseId) throws IOException {
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        String process7Have = gridConnect.getProcess7Number();
        String process7Name = gridConnect.getProcess7Name();
        File dir = new File("");
        String rootPath = dir.getCanonicalPath();
        File temp = new File(rootPath);
        rootPath = temp.getParent();
        String templatePath = rootPath+"\\photovoltaic-vue\\public\\files\\"+ houseId + "\\7并网验收与调试\\";
        File Dire = new File(templatePath);
        if(Dire.isDirectory()){
            String[] files = Dire.list();
            for (String path : files){
                System.out.println(path);
                if(path.contains("意见单") && fileList.contains("1")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process7Have = process7Have.replace("1", "");
                    process7Name = process7Name.replace(path+",", "");
                }
                else if(path.contains("会签单") && fileList.contains("2")){
                    File delete = new File(templatePath+path);
                    if(!delete.delete()){
                        return 0;
                    }
                    process7Have = process7Have.replace("2", "");
                    process7Name = process7Name.replace(path+",", "");
                }
            }
            gridConnect.setProcess7Number(process7Have);
            gridConnect.setProcess7Name(process7Name);
            gridConnectMapper.updateById(gridConnect);
            return 1;
        }
        return 0;
    }

    @PostMapping("/uploading8/{id}")
    public String uploadFile8(@PathVariable("id") Integer id){
        GridConnect gridConnect = gridConnectMapper.selectById(id);
        LocalDateTime now = LocalDateTime.now();
        try{
            gridConnect.setConnectCheck(now);
            gridConnect.setIsComplete(1);
            gridConnectMapper.updateById(gridConnect);
            return "上传成功";
        }catch (Exception e){
            return "上传失败";
        }
    }

    //下载文件是下载一个用户的所有文件的压缩包
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam Subsidy subsidy, HttpServletResponse response) {
        String sourceFilePath = System.getProperty("user.dir") + "\\files\\" + subsidy.getTime() + "\\" + subsidy.getHouseId();
        try {
            String fileName = subsidy.getHouseId() + ".zip";
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");
            File sourceFile = new File(sourceFilePath);
            if (!sourceFile.exists()) {
                System.out.println(">>>>>> 待压缩的文件目录：" + sourceFilePath + " 不存在. <<<<<<");
            } else {
                ZipOutputStream zos = null;
                BufferedInputStream bis = null;
                try{

                    File[] files = sourceFile.listFiles();
                    zos = new ZipOutputStream(response.getOutputStream());
                    byte[] buf = new byte[8192];
                    int len;
                    for (int i = 0; i < files.length; i++) {
                        File file = files[i];
                        if (!file.isFile()) continue;
                        ZipEntry ze = new ZipEntry(file.getName());
                        zos.putNextEntry(ze);
                        bis = new BufferedInputStream(new FileInputStream(file));
                        while ((len = bis.read(buf)) > 0) {
                            zos.write(buf, 0, len);
                        }
                        zos.closeEntry();
                    }
                    zos.closeEntry();
                }catch(Exception ex){
                    ex.printStackTrace();
                }finally {
                    if(bis != null){
                        try{
                            bis.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(zos != null){
                        try{
                            zos.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载文件失败");
            response.setStatus(500);
        }
    }
}
