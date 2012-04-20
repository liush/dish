package com.dish.util;

import com.dish.domain.Consume;
import com.dish.domain.ConsumeDetail;
import com.dish.domain.Information;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-11
 * Time: 下午9:06
 * To change this template use File | Settings | File Templates.
 */
public class TestPrintUtil {

    @BeforeMethod
    public void setUp() throws Exception {
        System.setProperty("dish.root", "f:/");
    }

    @Test
    public void testBuildHtml() throws Exception {
        Consume consume = new Consume();
        consume.setWaterPrice(10000);
        consume.setDiscount(10);
        consume.setDishPrice(10000);
        consume.setOtherPrice(10000);
        consume.setTotalPrice(30000);
        ArrayList<ConsumeDetail> details = new ArrayList<ConsumeDetail>();
        for (int i = 1; i < 10; i++) {
            ConsumeDetail detail = new ConsumeDetail();
            detail.setName(i + "测试");
            detail.setCount(i + 1);
            detail.setPrice(i * 100);
            detail.setType(Short.valueOf(String.valueOf(i % 3)));
            details.add(detail);
        }
        consume.setDetails(details);
        Map<String, Object> view = PrintUtil.createView(consume);
        String s = PrintUtil.buildHtml(view);
        PrintUtil.createImage(s);
    }

    @Test
    public void testPrint() throws Exception {
        Consume consume = new Consume();
        consume.setWaterPrice(10000);
        consume.setDiscount(10);
        consume.setDishPrice(10000);
        consume.setOtherPrice(10000);
        consume.setTotalPrice(30000);
        ArrayList<ConsumeDetail> details = new ArrayList<ConsumeDetail>();
        for (int i = 1; i < 10; i++) {
            ConsumeDetail detail = new ConsumeDetail();
            detail.setName(i + "测试");
            detail.setCount(i + 1);
            detail.setPrice(i * 100);
            detail.setType(Short.valueOf(String.valueOf(i % 3)));
            details.add(detail);
        }
        consume.setDetails(details);
        PrintUtil.print(consume);
    }

}
