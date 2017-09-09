package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Turf on 2017/9/9.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer> {
}
