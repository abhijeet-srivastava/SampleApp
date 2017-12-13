package com.cvent;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by a.srivastava on 6/1/16.
 */
public class MainClass {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static void main(String[] args) {
        MainClass main = new MainClass();
        main.tetsUpdateInIterator();
    }

    private void tetsUpdateInIterator() {
        List<DemoObject> list = createObjectList(10);
        DemoObject newDo = new DemoObject("Created", 100);
        ListIterator<DemoObject> itr = list.listIterator();
        while (itr.hasNext()) {
            DemoObject dObj = itr.next();
            if (dObj.getIndex() == 3) {
                itr.set(newDo);
            } else if (dObj.getIndex() == 5) {
                itr.remove();
            }
        }
        for (DemoObject dOb : list) {
            System.out.println(dOb.getName() + "<====>" + dOb.getIndex());
        }

    }

    private List<DemoObject> createObjectList(int i) {
        List<DemoObject> list = new ArrayList<>();
        for (int index = 0; index < i; index++) {
            list.add(new DemoObject(randomAlphaNumeric(5), index+1));
        }
        return list;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
