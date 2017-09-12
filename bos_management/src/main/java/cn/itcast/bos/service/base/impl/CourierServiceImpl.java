package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.ICourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    //保存快递员
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);

    }

    //带条件的分页查询
    @Override
    public Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    @Override
    public void delBatch(String[] idArray) {
        for (String idstr : idArray) {
            int id = Integer.parseInt(idstr);
            courierRepository.updateDelTag(id);
        }

    }
}
