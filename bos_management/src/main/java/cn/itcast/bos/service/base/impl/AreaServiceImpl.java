package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Turf on 2017/9/11.
 */
@Service
@Transactional
public class AreaServiceImpl implements IAreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);

    }
}
