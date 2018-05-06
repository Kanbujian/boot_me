package com.kanbujian.payment;

import com.kanbujian.entity.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ChargeDispatcher {

    public static Map dispatch(Transaction ts) throws Exception {
        String gateway = ts.getGateway();
        String classString =  String.format("com.kanbujian.payment.%s.Charge", gateway);
        try {
            Class clazz = Class.forName(classString);
            Action obj = (Action) clazz.getDeclaredConstructor(Transaction.class).newInstance(ts);
            Map response = obj.run();
            System.out.println(response.toString());
            // ts.getExtra().putAll(response)
            // ts.setExtra();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
