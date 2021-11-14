package cn.itcast.jd.task;

import cn.itcast.jd.pojo.Item;
import cn.itcast.jd.service.ItemService;
import cn.itcast.jd.util.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Scheduled(fixedDelay = 1000 * 1000)
    public void itemTask() throws Exception{
        for (int i = 1; i < 10; i = i + 2) {
            String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&wq=%E6%89%8B%E6%9C%BA&pvid=0d07a4ba92534880877462bdba6ebc89&s=116&click=0&page=";
            String html = httpUtils.doGet(url + i);

            //解析页面，获得商品数据并存储
            parse(html);
            System.out.println("第"+i+"页抓取完成");
        }
        System.out.println("手机数据抓取完成");

    }

    private void parse(String html) throws Exception {
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

                String priceJson = httpUtils.doGet("https://p.3.cn/prices/mgets?callback=jQuery3789730&type=1&area=15_1158_1228_0&skuIds=J_" + sku);
//                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
//                item.setPrice(price);
                Pattern compile = Pattern.compile("(\"p\":\")(\\d{4})");
                Matcher matcher = compile.matcher(priceJson);
                if (matcher.find()){
                    String priceString = matcher.group(2);
                    double price = Double.parseDouble(priceString);
                    item.setPrice(price);
                }


                String itemInfo = httpUtils.doGet(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                item.setTitle(title);

                item.setCreated(new Date());
                item.setUpdated(item.getCreated());

                itemService.save(item);

            }
        }
    }
}
