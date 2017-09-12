package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Turf on 2017/9/9.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer> ,JpaSpecificationExecutor<Courier>{
  //自定义 修改删除标签的值 -- 作废功能
   @Query(value = "update Courier set deltag='1' where id = ?")
    @Modifying
    public void updateDelTag(int id);
}
