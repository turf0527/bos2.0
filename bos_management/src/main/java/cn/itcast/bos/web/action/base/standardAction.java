package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.IStandardService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Turf on 2017/9/7.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    private Standard standard = new Standard();

    //注入service
    @Autowired
    private IStandardService standardService;

    @Override
    public Standard getModel() {
        return standard;
    }

    private int page;
    private int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setPage(int page) {
        this.page = page;
    }

    //保存投派标准数据
    @Action(value = "standard_save",results = {@Result(name = "success",type = "redirect",location = "./pages/base/standard.html")})
    public String save(){
        System.out.println("添加...");
        standardService.save(standard);
        return SUCCESS;
    }

    //投派标准 分页查询
    @Action(value = "standard_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Standard> pageData = standardService.findPageData(pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());
        //将map的转换为json对象
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

    //快递员信息表关联投派标准数据
    @Action(value = "standard_findAll",results = {@Result(name = "success",type = "json")})
    public String findAll(){
        List<Standard> standards = standardService.findAll();
        ActionContext.getContext().getValueStack().push(standards);
        return SUCCESS;
    }


}
