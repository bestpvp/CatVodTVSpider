package com.github.catvod.spider;

import android.text.TextUtils;
import com.github.catvod.crawler.Spider;
import com.github.catvod.net.OkHttp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhixc
 * 哔哩兔
 */
public class Bilituys extends Spider {

    private final String siteURL = "https://www.bilituys.com";

    private final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36";

    private Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("accept-language", "zh-CN,zh;q=0.9");
        header.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"");
        header.put("sec-ch-ua-mobile", "?0");
        header.put("sec-ch-ua-platform", "\"macOS\"");
        header.put("sec-fetch-dest", "document");
        header.put("sec-fetch-mode", "navigate");
        header.put("sec-fetch-site", "same-origin");
        header.put("sec-fetch-user", "?1");
        header.put("upgrade-insecure-requests", "1");
        header.put("User-Agent", userAgent);
        return header;
    }

    /**
     * 首页内容
     */
    @Override
    public String homeContent(boolean filter) throws Exception {
        JSONArray classes = new JSONArray();
        List<String> typeIds = Arrays.asList("1", "2", "3", "4");
        List<String> typeNames = Arrays.asList("电影", "连续剧", "综艺", "动漫");
        for (int i = 0; i < typeIds.size(); i++) {
            JSONObject c = new JSONObject();
            c.put("type_id", typeIds.get(i));
            c.put("type_name", typeNames.get(i));
            classes.put(c);
        }
        // filter 二级筛选 start
        String f = "{\"1\": [{\"key\": \"class\", \"name\": \"剧情\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"喜剧\", \"v\": \"喜剧\"}, {\"n\": \"爱情\", \"v\": \"爱情\"}, {\"n\": \"恐怖\", \"v\": \"恐怖\"}, {\"n\": \"动作\", \"v\": \"动作\"}, {\"n\": \"科幻\", \"v\": \"科幻\"}, {\"n\": \"剧情\", \"v\": \"剧情\"}, {\"n\": \"战争\", \"v\": \"战争\"}, {\"n\": \"警匪\", \"v\": \"警匪\"}, {\"n\": \"犯罪\", \"v\": \"犯罪\"}, {\"n\": \"动画\", \"v\": \"动画\"}, {\"n\": \"奇幻\", \"v\": \"奇幻\"}, {\"n\": \"武侠\", \"v\": \"武侠\"}, {\"n\": \"冒险\", \"v\": \"冒险\"}, {\"n\": \"枪战\", \"v\": \"枪战\"}, {\"n\": \"恐怖\", \"v\": \"恐怖\"}, {\"n\": \"悬疑\", \"v\": \"悬疑\"}, {\"n\": \"惊悚\", \"v\": \"惊悚\"}, {\"n\": \"经典\", \"v\": \"经典\"}, {\"n\": \"青春\", \"v\": \"青春\"}, {\"n\": \"文艺\", \"v\": \"文艺\"}, {\"n\": \"微电影\", \"v\": \"微电影\"}, {\"n\": \"古装\", \"v\": \"古装\"}, {\"n\": \"历史\", \"v\": \"历史\"}, {\"n\": \"运动\", \"v\": \"运动\"}, {\"n\": \"农村\", \"v\": \"农村\"}, {\"n\": \"儿童\", \"v\": \"儿童\"}, {\"n\": \"网络电影\", \"v\": \"网络电影\"}]}, {\"key\": \"area\", \"name\": \"地区\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"大陆\", \"v\": \"大陆\"}, {\"n\": \"香港\", \"v\": \"香港\"}, {\"n\": \"台湾\", \"v\": \"台湾\"}, {\"n\": \"美国\", \"v\": \"美国\"}, {\"n\": \"法国\", \"v\": \"法国\"}, {\"n\": \"英国\", \"v\": \"英国\"}, {\"n\": \"日本\", \"v\": \"日本\"}, {\"n\": \"韩国\", \"v\": \"韩国\"}, {\"n\": \"德国\", \"v\": \"德国\"}, {\"n\": \"泰国\", \"v\": \"泰国\"}, {\"n\": \"印度\", \"v\": \"印度\"}, {\"n\": \"意大利\", \"v\": \"意大利\"}, {\"n\": \"西班牙\", \"v\": \"西班牙\"}, {\"n\": \"加拿大\", \"v\": \"加拿大\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"year\", \"name\": \"年份\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"2023\", \"v\": \"2023\"}, {\"n\": \"2022\", \"v\": \"2022\"}, {\"n\": \"2021\", \"v\": \"2021\"}, {\"n\": \"2020\", \"v\": \"2020\"}, {\"n\": \"2019\", \"v\": \"2019\"}, {\"n\": \"2018\", \"v\": \"2018\"}, {\"n\": \"2017\", \"v\": \"2017\"}, {\"n\": \"2016\", \"v\": \"2016\"}, {\"n\": \"2015\", \"v\": \"2015\"}, {\"n\": \"2014\", \"v\": \"2014\"}, {\"n\": \"2013\", \"v\": \"2013\"}, {\"n\": \"2012\", \"v\": \"2012\"}, {\"n\": \"2011\", \"v\": \"2011\"}, {\"n\": \"2010\", \"v\": \"2010\"}, {\"n\": \"2009\", \"v\": \"2009\"}, {\"n\": \"2008\", \"v\": \"2008\"}, {\"n\": \"2007\", \"v\": \"2007\"}, {\"n\": \"2006\", \"v\": \"2006\"}, {\"n\": \"2005\", \"v\": \"2005\"}, {\"n\": \"2004\", \"v\": \"2004\"}, {\"n\": \"2003\", \"v\": \"2003\"}, {\"n\": \"2002\", \"v\": \"2002\"}, {\"n\": \"2001\", \"v\": \"2001\"}, {\"n\": \"2000\", \"v\": \"2000\"}]}, {\"key\": \"lang\", \"name\": \"语言\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"国语\", \"v\": \"国语\"}, {\"n\": \"英语\", \"v\": \"英语\"}, {\"n\": \"粤语\", \"v\": \"粤语\"}, {\"n\": \"闽南语\", \"v\": \"闽南语\"}, {\"n\": \"韩语\", \"v\": \"韩语\"}, {\"n\": \"日语\", \"v\": \"日语\"}, {\"n\": \"法语\", \"v\": \"法语\"}, {\"n\": \"德语\", \"v\": \"德语\"}, {\"n\": \"其它\", \"v\": \"其它\"}]}, {\"key\": \"by\", \"name\": \"排序\", \"value\": [{\"n\": \"时间\", \"v\": \"time\"}, {\"n\": \"人气\", \"v\": \"hits\"}, {\"n\": \"评分\", \"v\": \"score\"}]}], \"2\": [{\"key\": \"class\", \"name\": \"剧情\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"古装\", \"v\": \"古装\"}, {\"n\": \"战争\", \"v\": \"战争\"}, {\"n\": \"青春偶像\", \"v\": \"青春偶像\"}, {\"n\": \"喜剧\", \"v\": \"喜剧\"}, {\"n\": \"家庭\", \"v\": \"家庭\"}, {\"n\": \"犯罪\", \"v\": \"犯罪\"}, {\"n\": \"动作\", \"v\": \"动作\"}, {\"n\": \"奇幻\", \"v\": \"奇幻\"}, {\"n\": \"剧情\", \"v\": \"剧情\"}, {\"n\": \"历史\", \"v\": \"历史\"}, {\"n\": \"经典\", \"v\": \"经典\"}, {\"n\": \"乡村\", \"v\": \"乡村\"}, {\"n\": \"情景\", \"v\": \"情景\"}, {\"n\": \"商战\", \"v\": \"商战\"}, {\"n\": \"网剧\", \"v\": \"网剧\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"area\", \"name\": \"地区\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"大陆\", \"v\": \"大陆\"}, {\"n\": \"韩国\", \"v\": \"韩国\"}, {\"n\": \"香港\", \"v\": \"香港\"}, {\"n\": \"台湾\", \"v\": \"台湾\"}, {\"n\": \"日本\", \"v\": \"日本\"}, {\"n\": \"美国\", \"v\": \"美国\"}, {\"n\": \"泰国\", \"v\": \"泰国\"}, {\"n\": \"英国\", \"v\": \"英国\"}, {\"n\": \"新加坡\", \"v\": \"新加坡\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"year\", \"name\": \"年份\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"2023\", \"v\": \"2023\"}, {\"n\": \"2022\", \"v\": \"2022\"}, {\"n\": \"2021\", \"v\": \"2021\"}, {\"n\": \"2020\", \"v\": \"2020\"}, {\"n\": \"2019\", \"v\": \"2019\"}, {\"n\": \"2018\", \"v\": \"2018\"}, {\"n\": \"2017\", \"v\": \"2017\"}, {\"n\": \"2016\", \"v\": \"2016\"}, {\"n\": \"2015\", \"v\": \"2015\"}, {\"n\": \"2014\", \"v\": \"2014\"}, {\"n\": \"2013\", \"v\": \"2013\"}, {\"n\": \"2012\", \"v\": \"2012\"}, {\"n\": \"2011\", \"v\": \"2011\"}, {\"n\": \"2010\", \"v\": \"2010\"}, {\"n\": \"2009\", \"v\": \"2009\"}, {\"n\": \"2008\", \"v\": \"2008\"}, {\"n\": \"2007\", \"v\": \"2007\"}, {\"n\": \"2006\", \"v\": \"2006\"}, {\"n\": \"2005\", \"v\": \"2005\"}, {\"n\": \"2004\", \"v\": \"2004\"}, {\"n\": \"2003\", \"v\": \"2003\"}, {\"n\": \"2002\", \"v\": \"2002\"}, {\"n\": \"2001\", \"v\": \"2001\"}, {\"n\": \"2000\", \"v\": \"2000\"}]}, {\"key\": \"lang\", \"name\": \"语言\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"国语\", \"v\": \"国语\"}, {\"n\": \"英语\", \"v\": \"英语\"}, {\"n\": \"粤语\", \"v\": \"粤语\"}, {\"n\": \"闽南语\", \"v\": \"闽南语\"}, {\"n\": \"韩语\", \"v\": \"韩语\"}, {\"n\": \"日语\", \"v\": \"日语\"}, {\"n\": \"其它\", \"v\": \"其它\"}]}, {\"key\": \"by\", \"name\": \"排序\", \"value\": [{\"n\": \"时间\", \"v\": \"time\"}, {\"n\": \"人气\", \"v\": \"hits\"}, {\"n\": \"评分\", \"v\": \"score\"}]}], \"3\": [{\"key\": \"class\", \"name\": \"剧情\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"选秀\", \"v\": \"选秀\"}, {\"n\": \"情感\", \"v\": \"情感\"}, {\"n\": \"访谈\", \"v\": \"访谈\"}, {\"n\": \"播报\", \"v\": \"播报\"}, {\"n\": \"旅游\", \"v\": \"旅游\"}, {\"n\": \"音乐\", \"v\": \"音乐\"}, {\"n\": \"美食\", \"v\": \"美食\"}, {\"n\": \"纪实\", \"v\": \"纪实\"}, {\"n\": \"曲艺\", \"v\": \"曲艺\"}, {\"n\": \"生活\", \"v\": \"生活\"}, {\"n\": \"游戏互动\", \"v\": \"游戏互动\"}, {\"n\": \"财经\", \"v\": \"财经\"}, {\"n\": \"求职\", \"v\": \"求职\"}]}, {\"key\": \"area\", \"name\": \"地区\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"大陆\", \"v\": \"大陆\"}, {\"n\": \"韩国\", \"v\": \"韩国\"}, {\"n\": \"香港\", \"v\": \"香港\"}, {\"n\": \"台湾\", \"v\": \"台湾\"}, {\"n\": \"日本\", \"v\": \"日本\"}, {\"n\": \"美国\", \"v\": \"美国\"}, {\"n\": \"泰国\", \"v\": \"泰国\"}, {\"n\": \"英国\", \"v\": \"英国\"}, {\"n\": \"新加坡\", \"v\": \"新加坡\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"year\", \"name\": \"年份\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"2023\", \"v\": \"2023\"}, {\"n\": \"2022\", \"v\": \"2022\"}, {\"n\": \"2021\", \"v\": \"2021\"}, {\"n\": \"2020\", \"v\": \"2020\"}, {\"n\": \"2019\", \"v\": \"2019\"}, {\"n\": \"2018\", \"v\": \"2018\"}, {\"n\": \"2017\", \"v\": \"2017\"}, {\"n\": \"2016\", \"v\": \"2016\"}, {\"n\": \"2015\", \"v\": \"2015\"}, {\"n\": \"2014\", \"v\": \"2014\"}, {\"n\": \"2013\", \"v\": \"2013\"}, {\"n\": \"2012\", \"v\": \"2012\"}, {\"n\": \"2011\", \"v\": \"2011\"}, {\"n\": \"2010\", \"v\": \"2010\"}, {\"n\": \"2009\", \"v\": \"2009\"}, {\"n\": \"2008\", \"v\": \"2008\"}, {\"n\": \"2007\", \"v\": \"2007\"}, {\"n\": \"2006\", \"v\": \"2006\"}, {\"n\": \"2005\", \"v\": \"2005\"}, {\"n\": \"2004\", \"v\": \"2004\"}, {\"n\": \"2003\", \"v\": \"2003\"}, {\"n\": \"2002\", \"v\": \"2002\"}, {\"n\": \"2001\", \"v\": \"2001\"}, {\"n\": \"2000\", \"v\": \"2000\"}]}, {\"key\": \"lang\", \"name\": \"语言\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"国语\", \"v\": \"国语\"}, {\"n\": \"英语\", \"v\": \"英语\"}, {\"n\": \"粤语\", \"v\": \"粤语\"}, {\"n\": \"闽南语\", \"v\": \"闽南语\"}, {\"n\": \"韩语\", \"v\": \"韩语\"}, {\"n\": \"日语\", \"v\": \"日语\"}, {\"n\": \"其它\", \"v\": \"其它\"}]}, {\"key\": \"by\", \"name\": \"排序\", \"value\": [{\"n\": \"时间\", \"v\": \"time\"}, {\"n\": \"人气\", \"v\": \"hits\"}, {\"n\": \"评分\", \"v\": \"score\"}]}], \"4\": [{\"key\": \"class\", \"name\": \"剧情\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"情感\", \"v\": \"情感\"}, {\"n\": \"科幻\", \"v\": \"科幻\"}, {\"n\": \"热血\", \"v\": \"热血\"}, {\"n\": \"推理\", \"v\": \"推理\"}, {\"n\": \"搞笑\", \"v\": \"搞笑\"}, {\"n\": \"冒险\", \"v\": \"冒险\"}, {\"n\": \"萝莉\", \"v\": \"萝莉\"}, {\"n\": \"校园\", \"v\": \"校园\"}, {\"n\": \"动作\", \"v\": \"动作\"}, {\"n\": \"机战\", \"v\": \"机战\"}, {\"n\": \"运动\", \"v\": \"运动\"}, {\"n\": \"战争\", \"v\": \"战争\"}, {\"n\": \"少年\", \"v\": \"少年\"}, {\"n\": \"少女\", \"v\": \"少女\"}, {\"n\": \"社会\", \"v\": \"社会\"}, {\"n\": \"原创\", \"v\": \"原创\"}, {\"n\": \"亲子\", \"v\": \"亲子\"}, {\"n\": \"益智\", \"v\": \"益智\"}, {\"n\": \"励志\", \"v\": \"励志\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"area\", \"name\": \"地区\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"大陆\", \"v\": \"大陆\"}, {\"n\": \"日本\", \"v\": \"日本\"}, {\"n\": \"美国\", \"v\": \"美国\"}, {\"n\": \"韩国\", \"v\": \"韩国\"}, {\"n\": \"香港\", \"v\": \"香港\"}, {\"n\": \"台湾\", \"v\": \"台湾\"}, {\"n\": \"泰国\", \"v\": \"泰国\"}, {\"n\": \"英国\", \"v\": \"英国\"}, {\"n\": \"新加坡\", \"v\": \"新加坡\"}, {\"n\": \"其他\", \"v\": \"其他\"}]}, {\"key\": \"year\", \"name\": \"年份\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"2023\", \"v\": \"2023\"}, {\"n\": \"2022\", \"v\": \"2022\"}, {\"n\": \"2021\", \"v\": \"2021\"}, {\"n\": \"2020\", \"v\": \"2020\"}, {\"n\": \"2019\", \"v\": \"2019\"}, {\"n\": \"2018\", \"v\": \"2018\"}, {\"n\": \"2017\", \"v\": \"2017\"}, {\"n\": \"2016\", \"v\": \"2016\"}, {\"n\": \"2015\", \"v\": \"2015\"}, {\"n\": \"2014\", \"v\": \"2014\"}, {\"n\": \"2013\", \"v\": \"2013\"}, {\"n\": \"2012\", \"v\": \"2012\"}, {\"n\": \"2011\", \"v\": \"2011\"}, {\"n\": \"2010\", \"v\": \"2010\"}, {\"n\": \"2009\", \"v\": \"2009\"}, {\"n\": \"2008\", \"v\": \"2008\"}, {\"n\": \"2007\", \"v\": \"2007\"}, {\"n\": \"2006\", \"v\": \"2006\"}, {\"n\": \"2005\", \"v\": \"2005\"}, {\"n\": \"2004\", \"v\": \"2004\"}, {\"n\": \"2003\", \"v\": \"2003\"}, {\"n\": \"2002\", \"v\": \"2002\"}, {\"n\": \"2001\", \"v\": \"2001\"}, {\"n\": \"2000\", \"v\": \"2000\"}]}, {\"key\": \"lang\", \"name\": \"语言\", \"value\": [{\"n\": \"全部\", \"v\": \"\"}, {\"n\": \"国语\", \"v\": \"国语\"}, {\"n\": \"日语\", \"v\": \"日语\"}, {\"n\": \"英语\", \"v\": \"英语\"}, {\"n\": \"粤语\", \"v\": \"粤语\"}, {\"n\": \"闽南语\", \"v\": \"闽南语\"}, {\"n\": \"韩语\", \"v\": \"韩语\"}, {\"n\": \"其它\", \"v\": \"其它\"}]}, {\"key\": \"by\", \"name\": \"排序\", \"value\": [{\"n\": \"时间\", \"v\": \"time\"}, {\"n\": \"人气\", \"v\": \"hits\"}, {\"n\": \"评分\", \"v\": \"score\"}]}]}";
        JSONObject filterConfig = new JSONObject(f);
        // filter 二级筛选 end
        JSONObject result = new JSONObject()
                .put("class", classes)
                .put("filters", filterConfig);
        return result.toString();
    }

    /**
     * 分类页面
     *
     * @param tid    影片分类id值
     * @param pg     第几页
     * @param extend 筛选
     * @return 返回 json 字符串
     */
    @Override
    public String categoryContent(String tid, String pg, boolean filter, HashMap<String, String> extend) throws Exception {
        // 二级筛选处理 start
        HashMap<String, String> ext = new HashMap<>();
        if (extend != null && extend.size() > 0) {
            ext.putAll(extend);
        }
        String area = checkExt("area", ext);
        String year = checkExt("year", ext);
        String by = checkExt("by", ext);
        String classType = checkExt("class", ext);
        String lang = checkExt("lang", ext);
        // 二级筛选处理 end

        String cateURL = siteURL + "/vodshow/" + tid + area + by + classType + lang + year;
        if (!pg.equals("1")) cateURL += "/page/" + pg;
        cateURL += ".html";
        String html = OkHttp.string(cateURL, getHeader());
        Elements items = Jsoup.parse(html).select("[class=module-items] .module-item");
        JSONArray videos = new JSONArray();
        for (Element item : items) {
            String vodId = item.select(".module-item-title").attr("href");
            String name = item.select(".module-item-title").text();
            String pic = item.select(".module-item-cover img").attr("data-src");
            String remark = item.select(".module-item-text").text();
            JSONObject vod = new JSONObject()
                    .put("vod_id", vodId)
                    .put("vod_name", name)
                    .put("vod_pic", pic)
                    .put("vod_remarks", remark);
            videos.put(vod);
        }
        JSONObject result = new JSONObject()
                .put("pagecount", 999)
                .put("list", videos);
        return result.toString();
    }

    private String checkExt(String key, HashMap<String, String> ext) {
//        String value = ext.getOrDefault(key, ""); // 这种写法可能不支持低版本的安卓系统
//        String value = ext.containsKey(key) ? ext.get(key) : ""; // 这种写法暂时未知
        String value = ext.get(key) == null ? "" : ext.get(key); // 推荐这种写法，HashMap 的 containsKey() 方法的源码就是这种写法
        if (value.equals("")) return value;
//        return "/" + key + "/" + value;
        return String.format("/%s/%s", key, value);
    }

    /**
     * 详情页
     */
    @Override
    public String detailContent(List<String> ids) throws Exception {
        String vodId = ids.get(0);
        String detailURL = siteURL + vodId;
        String html = OkHttp.string(detailURL, getHeader());
        Document doc = Jsoup.parse(html);

        Elements sourceList = doc.select("[class=module-blocklist scroll-box scroll-box-y] .scroll-content");
        Elements sourceHeader = doc.select("[class=module-tab-item tab-item]");
        Map<String, String> playMap = new LinkedHashMap<>();
        for (int i = 0; i < sourceList.size(); i++) {
            String playFrom = sourceHeader.get(i).text();
            Elements aList = sourceList.get(i).select("a");
            List<String> vodItems = new ArrayList<>();
            for (Element a : aList) {
                String episodeName = a.text();
                String episodeURL = a.attr("href");
                vodItems.add(episodeName + "$" + episodeURL);
            }
            if (vodItems.size() > 0) {
                playMap.put(playFrom, TextUtils.join("#", vodItems));
            }
        }

        String name = doc.select(".page-title").text();
        String pic = doc.select(".video-cover img").attr("data-src");
        String typeName = doc.select("div[class=tag-link]").text();
        Elements tags = doc.select("a[class=tag-link]");
        String year = "";
        String area = "";
        for (int i = 0; i < tags.size(); i++) {
            if (i == 1) year = tags.get(i).text();
            if (i == 2) area = tags.get(i).text();
        }
        String remark = "";
        String actor = getStrByRegex("主演：(.*?)</div>", html);
        String director = getStrByRegex("导演：(.*?)</div>", html);
        String description = doc.select(".vod_content").text();

        // 影片名称、图片等赋值
        JSONObject vod = new JSONObject()
                .put("vod_id", ids.get(0)) // 必须有
                .put("vod_name", name)
                .put("vod_pic", pic)
                .put("type_name", typeName) // 影片类型
                .put("vod_year", year) // 影片年份
                .put("vod_area", area) // 影片地区
                .put("vod_remarks", remark) // 备注
                .put("vod_actor", actor) // 主演
                .put("vod_director", director) // 导演
                .put("vod_content", description); // 简介

        if (playMap.size() > 0) {
            vod.put("vod_play_from", TextUtils.join("$$$", playMap.keySet()));
            vod.put("vod_play_url", TextUtils.join("$$$", playMap.values()));
        }

        JSONArray list = new JSONArray().put(vod);
        JSONObject result = new JSONObject();
        result.put("list", list);
        return result.toString();
    }

    private String getStrByRegex(String regexStr, String htmlStr) {
        if (regexStr == null) return "";
        try {
            Pattern pattern = Pattern.compile(regexStr, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(htmlStr);
            if (matcher.find()) {
                return matcher.group(1)
                        .replaceAll("</?[^>]+>", "")
                        .replaceAll("&nbsp;", " ")
                        .trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 搜索
     *
     * @param key 关键字/词
     */
    @Override
    public String searchContent(String key, boolean quick) throws Exception {
        return searchContent(key, quick, "1");
    }

    /**
     * 搜索带分页
     * 备注：最新的影视App支持搜索翻页功能
     *
     * @param key   关键字/词
     * @param quick 暂时不用
     * @param pg    页码
     */
    @Override
    public String searchContent(String key, boolean quick, String pg) throws Exception {
        // 第一页
        // https://www.bilituys.com/bilisch.html?wd=我

        // 第二页
        // https://www.bilituys.com/bilisch/page/2/wd/我.html
        String searchURL = siteURL + "/bilisch.html?wd=" + URLEncoder.encode(key);
        if (!pg.equals("1")) searchURL = siteURL + "/bilisch/page/" + pg + "/wd/" + URLEncoder.encode(key) + ".html";
        String html = OkHttp.string(searchURL, getHeader());
        Elements items = Jsoup.parse(html).select("[class=module-items] .module-search-item");
        JSONArray videos = new JSONArray();
        for (Element item : items) {
            String vodId = item.select(".video-info-header > h3 > a").attr("href");
            String name = item.select(".video-info-header > h3 > a").text();
            String pic = item.select(".video-cover img").attr("data-src");
            String remark = item.select(".video-info-header > a[class=video-serial]").text();
            JSONObject vod = new JSONObject()
                    .put("vod_id", vodId)
                    .put("vod_name", name)
                    .put("vod_pic", pic)
                    .put("vod_remarks", remark);
            videos.put(vod);
        }
        JSONObject result = new JSONObject()
                .put("list", videos);
        return result.toString();
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        String lastURL = siteURL + id;
        int parseFlag = 1;
        String content = OkHttp.string(lastURL, getHeader());
        Pattern pattern = Pattern.compile("player_aaaa=(.*?)</script>");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String url = new JSONObject(matcher.group(1).trim()).optString("url");
            if (url.contains(".m3u8") || url.contains(".mp4")) {
                lastURL = url;
                parseFlag = 0;
            }
        }
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", userAgent);
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        header.put("Accept-encoding", "gzip, deflate, br");
        header.put("Accept-language", "zh-CN,zh;q=0.9");
        header.put("Referer", siteURL + "/");
        header.put("Upgrade-insecure-requests", "1");

        JSONObject result = new JSONObject()
                .put("parse", parseFlag)
                .put("header", header.toString())
                .put("playUrl", "")
                .put("url", lastURL);
        return result.toString();
    }
}
