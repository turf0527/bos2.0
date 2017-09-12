package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.IAreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Turf on 2017/9/11.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport {
    //分页
    private int page;
    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {
        this.page = page;
    }

    //接收上传文件
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Autowired
    private IAreaService areaService;


    //一键上传--导入excel区域信息
    @Action(value = "area_batchImport")
    public void batchImport() throws IOException {
        List<Area> areas = new ArrayList<Area>();

        //加载上传文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        //读取第一个excle中得sheet
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        //读取sheet中得每一行
        for(Row row:sheet){
            if(row.getRowNum()==0){
                //判断为第一行,直接跳过
                continue;
            }
            //跳过空行
            if(row.getCell(0)==null || StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                continue;
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            //基于pinyin4j生成城市简码和编码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
             province = province.substring(0, province.length() - 1);
             city = city.substring(0, city.length() - 1);
             district = district.substring(0, district.length() - 1);

            //简码
            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer buffer = new StringBuffer();
            for (String headStr : headArray) {
                buffer.append(headStr);
            }
            area.setShortcode(buffer.toString());
            //编码
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(cityCode);

            //将area添加到集合中
            areas.add(area);

        }
            //调用service方法
            areaService.saveBatch(areas);

    }

    //区域分页查询
    @Action(value = "area_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest(page-1,rows);
        Specification specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //添加条件



                return null;
            }
        };

        return SUCCESS;
    }
}
