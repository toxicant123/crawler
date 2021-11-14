package cn.itcast.jd.dao;

import cn.itcast.jd.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-11 11:37
 */
public interface ItemDao extends JpaRepository<Item,Long> {
}
