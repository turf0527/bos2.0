package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.ICourierService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    //属性注入
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
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

    //带条件的分页查询
    @Action(value = "courier_pageQuery",results = {@Result(name = "success",type = "json")})
    public String pageQuery(){
        Pageable pageable = new PageRequest(page-1,rows);
        Specification specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                //当前查询root根对象Courier
                List<Predicate> list = new ArrayList<Predicate>();
                //单表查询
                if(StringUtils.isNotBlank(courier.getCourierNum())){
                    //进行员工编号查询--等值查询
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
                    list.add(p1);
                }
                if(StringUtils.isNotBlank(courier.getCompany())){
                    //员工所属单位查询--模糊查询
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(courier.getType())){
                    //员工类型查询--等值查询
                    Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
                    list.add(p3);
                }

                //多表查询
                Join<Object,Object> standardRoot = root.join("standard",JoinType.INNER);
                if(courier.getStandard()!=null &&StringUtils.isNotBlank(courier.getStandard().getName())){
                    //进行收派标准模糊查询
                    Predicate p4 = cb.like(standardRoot.get("name").as(String.class), "%" + courier.getStandard().getName() + "%");
                    list.add(p4);

                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
       Page<Courier> pageData =  courierService.findPageData(specification,pageable);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageData.getTotalElements());
        map.put("rows",pageData.getContent());

        ActionContext.getContext().getValueStack().push(map);

        return SUCCESS;
    }

    @Action(value = "courier_delBatch" ,results = {@Result(name="success",type = "redirect",location = "./pages/base/courier.html")})
    public String delBatch(){
        //按,分割ids
        String[] idArray = ids.split(",");
        //调用业务层批量作废
        courierService.delBatch(idArray);
        return SUCCESS;
    }

}
