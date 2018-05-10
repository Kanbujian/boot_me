package com.kanbujian.payment.wechatpay.lib;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class XmlUtil {

    public static Map<String,String> dom2Map(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if(doc == null)
            return map;
        Element root = doc.getRootElement();
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
            Element e = (Element) iterator.next();
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static String mapToXML(Map map) {
        Set<Map.Entry<String, String>> set= map.entrySet();
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        set.forEach((entry) -> {
            root.addElement(entry.getKey()).addText(entry.getValue());
        });
        return document.asXML();
    }

}
