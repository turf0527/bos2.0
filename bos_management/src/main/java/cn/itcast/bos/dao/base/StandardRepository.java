package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Turf on 2017/9/7.
 */
public interface StandardRepository extends JpaRepository<Standard,Integer> {
}
