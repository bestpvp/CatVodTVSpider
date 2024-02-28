package com.github.catvod.spider;

import com.github.catvod.crawler.Spider;
import com.github.catvod.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PushAgent extends Spider {

    @Override
    public String detailContent(List<String> ids) throws Exception {
        String detailUrl = ids.get(0).trim();
        if (detailUrl.toLowerCase().startsWith("jianpian://pathtype=url&path=")) {
            ids.remove(0);
            String url = detailUrl.toLowerCase();
            ids.add(url.replaceAll("jianpian://pathtype=url&path=", "tvbox-xg:"));
            String name = getName(url);
            return getResultStr(ids, name, "荐片链接", "荐片边下边播");
        }
        if (isThunderSupport(detailUrl)) {
            String name = "";
            Matcher matcher = Pattern.compile("(^|&)dn=([^&]*)(&|$)").matcher(URLDecoder.decode(detailUrl));
            if (matcher.find()) name = matcher.group(2);
            if (name.equals("")) name = detailUrl;
            return getResultStr(ids, name, "磁力链接|迅雷链接|FTP", "magnet");
        }
        if (Utils.isVip(detailUrl)) return getResultStr(ids, detailUrl, "解析类链接", "解析");
        if (Utils.isVideoFormat(detailUrl)) return getResultStr(ids, detailUrl, "可以直接播放的直链", "直连");
        return getResultStr(ids, detailUrl, "嗅探类链接", "嗅探");
    }

    private String getName(String url) {
        try {
            String decodeUrl = URLDecoder.decode(url);
            int start = decodeUrl.lastIndexOf("/");
            int end = decodeUrl.lastIndexOf(".");
            return decodeUrl.substring(start + 1, end);
        } catch (Exception e) {
        }
        return url;
    }

    private boolean isThunderSupport(String url) {
        return url.startsWith("magnet:?xt=")
                || url.startsWith("thunder://")
                || url.startsWith("ftp://")
                || url.startsWith("ed2k://")
                || url.endsWith(".torrent");
    }

    private String getResultStr(List<String> ids, String name, String typeName, String vod_play_from) throws Exception {
        String url = ids.get(0).trim();
        JSONObject vod = new JSONObject()
                .put("vod_id", ids.get(0))
                .put("vod_name", name)
                .put("vod_pic", "https://pic.rmb.bdstatic.com/bjh/1d0b02d0f57f0a42201f92caba5107ed.jpeg")
                .put("type_name", typeName)
                .put("vod_content", url)
                .put("vod_play_from", vod_play_from)
                .put("vod_play_url", "立即播放$" + url);
        JSONArray list = new JSONArray().put(vod);
        JSONObject result = new JSONObject().put("list", list);
        return result.toString();
    }

    @Override
    public String playerContent(String flag, String id, List<String> vipFlags) throws Exception {
        JSONObject result = new JSONObject();
        switch (flag) {
            case "magnet":
            case "荐片边下边播":
            case "直连":
                result.put("parse", 0);
                break;
            case "解析":
                result.put("parse", 1).put("jx", "1");
                break;
            default: // 默认是嗅探
                result.put("parse", 1);
        }
        result.put("playUrl", "");
        result.put("url", id);
        return result.toString();
    }
}
