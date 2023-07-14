package com.bjut.iras.utils;
import com.alibaba.fastjson.JSONArray;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cal {
    public static int CalAge(String date){
        try{
            if(date.length() >= 4) return Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(date.substring(0, 4));
            if(date.length() == 0) return 20 + (int)Math.ceil(Math.random() * 10);
            return Integer.parseInt(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 20 + (int)Math.ceil(Math.random() * 10);
    }

    public static int CalWorkYear(String workYear){
        String str = "(19|20|21)\\d{2}";
        Pattern pattern = Pattern.compile(str);
        try{
            JSONArray jsonArray = JSONArray.parseArray(workYear);
            int ans = 0;
            for(int i = 0; i < jsonArray.size(); i++){
                String period = jsonArray.getString(i);
                Matcher matcher = pattern.matcher(period);
                int mn = 0, mx = 0;
                int cnt = 0;
                while(matcher.find()){
                    cnt++;
                    String group = matcher.group();
                    if(mn == 0){
                        mn = Integer.parseInt(group);
                    }else {
                        mx = Integer.parseInt(group);
                    }
                    if(cnt == 2) break;
                }
                if(cnt == 2 && period.contains("至今")){
                    mx = Calendar.getInstance().get(Calendar.YEAR);
                }
                else {
                    mx = mn;
                }
                ans += Math.max(mx, mn) - Math.min(mx, mn) + 1;
            }
            return ans;

        }catch (Exception e){
            e.printStackTrace();

        }
        return 1;
    }
}
