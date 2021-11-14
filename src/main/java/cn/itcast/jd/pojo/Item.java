package cn.itcast.jd.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-11 11:30
 */
@Entity
@Table(name = "jd_item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spu;

    private Long sku;

    private String title;

    private Double price;

    private String pic;

    private String url;

    private Date created;

    private Date updated;

    public Item() {
    }

    public Item(Long id, Long spu, Long sku, String title, Double price, String pic, String url, Date created, Date updated) {
        this.id = id;
        this.spu = spu;
        this.sku = sku;
        this.title = title;
        this.price = price;
        this.pic = pic;
        this.url = url;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpu() {
        return spu;
    }

    public void setSpu(Long spu) {
        this.spu = spu;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", spu=" + spu +
                ", sku=" + sku +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
