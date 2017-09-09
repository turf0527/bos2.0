package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Turf on 2017/9/9.
 */

public interface ICourierService {


   public void save(Courier courier);

   Page<Courier> findPageData(Pageable pageable);
}
