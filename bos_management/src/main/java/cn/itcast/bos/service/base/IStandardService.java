package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Turf on 2017/9/7.
 */

public interface IStandardService {
    public void save(Standard standard);

    Page<Standard> findPageData(Pageable pageable);

    List<Standard> findAll();
}
