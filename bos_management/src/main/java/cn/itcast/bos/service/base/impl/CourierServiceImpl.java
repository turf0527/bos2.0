package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.ICourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Turf on 2017/9/9.
 */
@Service
@Transactional
public class CourierServiceImpl implements ICourierService {

    @Autowired
    CourierRepository courierRepository;

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);

    }

    @Override
    public Page<Courier> findPageData(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
}
