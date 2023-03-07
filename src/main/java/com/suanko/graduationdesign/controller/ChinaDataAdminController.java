package com.suanko.graduationdesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suanko.graduationdesign.entity.NocvData;
import com.suanko.graduationdesign.service.IndexService;
import com.suanko.graduationdesign.vo.DataView;
import com.suanko.graduationdesign.vo.NocvDataVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController

public class ChinaDataAdminController {
    @Autowired
    private IndexService indexService;




    //模糊查询带分页
    @GetMapping(value = "/listDataByPage")
    public DataView listDataByPage(NocvDataVo nocvDataVo){
        //1.创建分页得对象 当前页 每页限制大小
        IPage<NocvData> page=new Page<>(nocvDataVo.getPage(), nocvDataVo.getLimit());
        //2.创建模糊查询条件
        QueryWrapper<NocvData> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(!(nocvDataVo.getName()==null),"name",nocvDataVo.getName());
        //3.确诊最多得排最上面
        queryWrapper.orderByDesc("value");
        //4.查询数据库
        indexService.page(page,queryWrapper);
        //5.返回数据封装
        DataView dataView=new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }

    /**
     * DELETE BY　ID
     * @param id
     * @return
     */
    @DeleteMapping("/china/deleteById")
    public DataView deleteById(Integer id){
        indexService.removeById(id);
        DataView dataView=new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除中国疫情数据成功!");
        return dataView;
    }

    /**
     * 新增或修改【id】
     * id：nocvData 有值修改，无值即新增
     * @param nocvData
     * @return
     */
    @PostMapping("/china/addOrUpdateChina")
    public DataView addOrUpdateChina(NocvData nocvData){
        boolean save=indexService.saveOrUpdate(nocvData);
        DataView dataView = new DataView();
        if (save) {
            dataView.setCode(200);
            dataView.setMsg("更新中国疫情数据成功!");
            return dataView;
        }
        dataView.setCode(100);
        dataView.setMsg("更新中国疫情数据失败!");
        return dataView;
    }


    /**
     * Excel的拖拽点击上传
     * 1.前台页面发送一个请求，上传文件MutilpartFile HTTP
     * 2.Controller，上传文件MutilpartFile 参数
     * 3.POI 解析文件，里面的数据一行一行解析出来
     * 4.每一条数据插入数据库
     * 5.mybatisplus批量saveBatch(list)
     */
    @RequestMapping(value = "/excelImportChina",method = RequestMethod.POST)
    public DataView excelImportChina(@RequestParam("file")MultipartFile file) throws Exception{
        DataView dataView=new DataView();

        //1.文件不能为空
        if (file.isEmpty()){
            dataView.setMsg("文件为空，不能上传！");
            return dataView;
        }
        //2.POI获取Excel数据
        XSSFWorkbook wb=new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet=wb.getSheetAt(0);

        //3.定义一个程序集合  接收文件中的数据
        List<NocvData> list=new ArrayList<>();
        XSSFRow row=null;

        //4.解析数据，装到集合里面
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            //定义实体
            NocvData nocvData=new NocvData();
            //每一行数据放到实体里
            row= sheet.getRow(i);
            //解析数据
            nocvData.setName(row.getCell(0).getStringCellValue());
            nocvData.setValue((int) row.getCell(1).getNumericCellValue());
            list.add(nocvData);
        }

        //6.插入数据库
        indexService.saveBatch(list);
        dataView.setCode(200);
        dataView.setMsg("Excel文件插入成功！");
        return dataView;
    }

    /**
     * 导出Excel数据   中国疫情数据
     * 1.查询数据库
     * 2.建立Excel对象，封装数据
     * 3.建立输出流，输出文件
     */
    @RequestMapping("/excelOutPortChina")
    public void excelOutPortChina(HttpServletResponse response) throws Exception {
        //1.查询数据库
        List<NocvData> list=indexService.list();
        //2.建立Excel对象，封装数据
        response.setCharacterEncoding("UTF-8");
        XSSFWorkbook wb=new XSSFWorkbook();
        //创建Sheet对象
        XSSFSheet sheet=wb.createSheet("中国疫情数据sheet1");
        //创建表头
        XSSFRow xssfRow=sheet.createRow(0);
        xssfRow.createCell(0).setCellValue("城市名称");
        xssfRow.createCell(1).setCellValue("确诊数量");
        //3.遍历数据，封装Excel做对象
        for (NocvData data:list) {
            XSSFRow dataRow=sheet.createRow(sheet.getLastRowNum()+1);
            dataRow.createCell(0).setCellValue(data.getName());
            dataRow.createCell(1).setCellValue(data.getValue());
        }
        //4.建立输出流，输出浏览器文件
        OutputStream os=null;
        //设置一下Excel名字,输出类型编写
        response.setContentType("application/octet-stream;chartset=utf8");
        response.setHeader("Content-Disposition","attachment;filename="+new String("中国疫情数据表".getBytes(),"iso-8859-1")+".xlsx");
        //输出文件
        os=response.getOutputStream();
        wb.write(os);
        os.flush();
        //5.关闭输出流
        os.close();
    }
}
