package cn.itcast.jd.service;

import cn.itcast.jd.pojo.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-11 11:38
 */
public interface ItemService {

    /**
     * 保存商品
     * @param item
     */
    void save(Item item);

    /**
     * 根据条件查询商品
     * @param item
     * @return
     */
    List<Item> findAll(Item item);

}
