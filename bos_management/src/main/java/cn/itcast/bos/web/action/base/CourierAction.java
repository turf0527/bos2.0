package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.ICourierService;
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

/**
 * Created by Turf on 2017/9/9.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
    private Courier courier = new Courier();

    @Override
    public Courier getModel() {
        return courier;
    }

    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Autowired
    private ICourierService courierService;

    //保存快递员信息
    @Action(value = "courier_save",results = @Result(name = "success",type = "redirect",location = "./pages/base/courier.html"))
    public String save(){
        System.out.println("添加...");
        courierService.save(courier);
        return SUCCESS;
    }

    //分页查询
    @Action(value = "courier_pageQuery",results = {@Result(name = "success",type = "json")})
    private String pageQuery(){
        Pageable pageable = new PageRequest(page-1,rows);
       Page<Courier> pageData =  courierService.findPageData(pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());

        ActionContext.getContext().getValueStack().push(map);

        return SUCCESS;
    }


}
