package cn.itcast.bos.web.action.base;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.IStandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by Turf on 2017/9/7.
 */
@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("pototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    private Standard standard = new Standard();

    //注入service
    @Autowired
    private IStandardService standardService;

    @Override
    public Standard getModel() {
        return standard;
    }

    @Action(value = "standard_save",results = {@Result(name = "success",type = "redirect",location = "./pages/base/standard.html")})
    public String save(){
        System.out.println("添加...");
        standardService.save(standard);
        return SUCCESS;

    }

}
