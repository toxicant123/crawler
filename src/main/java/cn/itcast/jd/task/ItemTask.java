package cn.itcast.jd.task;

import cn.itcast.jd.pojo.Item;
import cn.itcast.jd.service.ItemService;
import cn.itcast.jd.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTML;
import java.util.Date;
import java.util.List;

/**
 * @author toxicant123
 * @Description
 * @create 2021-11-14 15:46
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;

    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception{
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&wq=%E6%89%8B%E6%9C%BA&pvid=0d07a4ba92534880877462bdba6ebc89&s=116&click=0&page=";
//        String url1 = "https://search.jd.com/Search?keyword=iphone&enc=utf-8&wq=iphone&pvid=7f34a09da2a64afcb5a4763ad63b9019";
        for (int i = 1; i < 10; i = i + 2) {
            String html = httpUtils.doGet(url + i);

            //解析页面，获得商品数据并存储
            parse(html);
        }
        System.out.println("手机数据抓取完成");

    }

    private void parse(String html) {
        Document doc = Jsoup.parse(html);
        Elements spuEles = doc.select("div#J_goodsList > ul > li");
        for (Element spuEle : spuEles) {
            String spuString = spuEle.attr("data-spu").equals("")?spuEle.attr("data-sku"):spuEle.attr("data-spu");
            long spu = Long.parseLong(spuString);
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle : skuEles) {
                long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));
                Item item = new Item();
                item.setSku(sku);
                List<Item> list = itemService.findAll(item);

                if (list.size() > 0){
                    continue;
                }

                item.setSpu(spu);

                String itemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(itemUrl);

                String picUrl = "https:" + skuEle.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/", "/n1/");
                String picName = httpUtils.doGetImage(picUrl);
                item.setPic(picName);
//
//                item.setPrice();
//
//                item.setTitle();



                item.setCreated(new Date());
                item.setUpdated(item.getCreated());
            }
        }
    }
}
