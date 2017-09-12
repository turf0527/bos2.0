package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Area;

import java.util.List;

/**
 * Created by Turf on 2017/9/11.
 */
public interface IAreaService {
    public void saveBatch(List<Area> areas);
}
