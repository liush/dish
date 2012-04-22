package com.dish.util;

import com.dish.domain.Consume;
import com.dish.domain.ConsumeDetail;
import com.dish.domain.Information;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import javax.print.*;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-11
 * Time: 下午9:02
 * To change this template use File | Settings | File Templates.
 */
public class PrintUtil {

    private final static Configuration CONFIGURATION;
    private final static String WEB_ROOT = System.getProperty("dish.root");
    public final static String WEB_ROOT_TEMP = WEB_ROOT + "/temp/";

    static {
        CONFIGURATION = new Configuration();
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setEncoding(Locale.CHINA,"UTF-8");
        File file = new File(WEB_ROOT + "/WEB-INF/freemarker");
        try {
            CONFIGURATION.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            throw new RuntimeException("获得项目路径失败。", e);
        }
        CONFIGURATION.setObjectWrapper(new DefaultObjectWrapper());
        File tempDir = new File(WEB_ROOT_TEMP);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    public static boolean print(Consume consume) {
        try {
            Map<String, Object> map = createView(consume);
            String fileFullName = buildHtml(map);
            createImage(fileFullName);
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob printJob = printService.createPrintJob();
            FileInputStream fileInputStream = new FileInputStream(fileFullName+".png");
            HashDocAttributeSet docAttributeSet = new HashDocAttributeSet();
            docAttributeSet.add(new MediaPrintableArea(0, 0, 47, 100, MediaPrintableArea.MM));
            SimpleDoc doc = new SimpleDoc(fileInputStream, DocFlavor.INPUT_STREAM.PNG, docAttributeSet);
            printJob.print(doc, new HashPrintRequestAttributeSet());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (PrintException e) {
            e.printStackTrace();
            return false;
        } catch (TemplateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 构造视图类
     *
     * @param consume 必须包含详细信息
     */
    public static Map<String, Object> createView(Consume consume) {
        List<ConsumeDetail> details = consume.getDetails();
        List<ConsumeDetail> dishes = new ArrayList<ConsumeDetail>();
        List<ConsumeDetail> waters = new ArrayList<ConsumeDetail>();
        List<ConsumeDetail> others = new ArrayList<ConsumeDetail>();
        int dishCount = 0;
        int waterCount = 0;
        int otherCount = 0;
        for (ConsumeDetail detail : details) {
            if (detail.getType() == Information.DISH) {
                dishes.add(detail);
                dishCount+=detail.getCount();
            }
            if (detail.getType() == Information.WATER) {
                waters.add(detail);
                waterCount+=detail.getCount();
            }
            if (detail.getType() == Information.OTHER) {
                others.add(detail);
                otherCount+=detail.getCount();
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("dishes", dishes);
        map.put("dishCount", dishCount);
        map.put("waters", waters);
        map.put("waterCount", waterCount);
        map.put("others", others);
        map.put("otherCount", otherCount);
        map.put("consume",consume);
        map.put("time",DateUtil.getCurrTimeString());
        return map;
    }

    public static String buildHtml(Map<String, Object> map) throws IOException, TemplateException {
        Template template = CONFIGURATION.getTemplate("receipt.ftl","UTF-8");
        StringWriter writer = new StringWriter();
        template.setEncoding("UTF-8");
        template.process(map, writer);
        return writer.toString();
    }

    public static void createImage(String fileFullName) throws IOException {
        //Generate an image from a file:
        File f = new File(fileFullName + ".html");

        int width = 200;
        // can specify width alone, or width + height
        // constructing does not render; not until getImage() is called

        Java2DRenderer renderer = new Java2DRenderer(f, width);

        // this renders and returns the image, which is stored in the J2R; will not
        // be re-rendered, calls to getImage() return the same instance
        BufferedImage img = renderer.getImage();

        // write it out, full size, PNG
        // FSImageWriter instance can be reused for different images,
        // defaults to PNG
        FSImageWriter imageWriter = new FSImageWriter();
        imageWriter.write(img, fileFullName + ".png");
    }

}
