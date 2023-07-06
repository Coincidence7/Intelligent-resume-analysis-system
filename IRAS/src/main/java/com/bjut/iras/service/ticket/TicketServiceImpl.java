package com.bjut.iras.service.ticket;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.TicketMapper;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.pojo.ticket;
import com.bjut.iras.service.QueryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    private HashMap<String, String> getTicketsFromQueryWrapper(QueryWrapper<ticket> qw, Integer page) {

        IPage<ticket> ticketPage = new Page<>(page, QueryConstants.ticketPageCapacity);
        List<ticket> tickets = ticketMapper.selectPage(ticketPage, qw).getRecords();

        if (tickets.size() == 0) {
            return null;
        }

        JSONObject jobj = new JSONObject();
        int i = 0;
        for (ticket t : tickets) {
            jobj.put("result" + i, t.toString());
            i++;
        }
        HashMap<String, String> retMap = new HashMap<>();
        retMap.put("results", jobj.toJSONString());
        retMap.put("size", Integer.toString(Math.min(QueryConstants.resultPageCapacity, tickets.size())));

        return retMap;
    }

    @Override
    public Map<String, String> getTicketByName(String ticketname, Boolean isLike, Integer page) {

        QueryWrapper<ticket> qw = new QueryWrapper<>();

        if (isLike) {
            qw.like("ticketname", ticketname);
        } else {
            qw.eq("ticketname", ticketname);
        }

        return getTicketsFromQueryWrapper(qw, page);
    }

    @Override
    public Map<String, String> getTicketByKey(Integer ticketkey, Integer page) {
        QueryWrapper<ticket> qw = new QueryWrapper<>();
        qw.eq("ticketkey", ticketkey);

        return getTicketsFromQueryWrapper(qw, page);
    }

    public Map<String, String> getTicketByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page) {

        QueryWrapper<ticket> qw = new QueryWrapper<>();

        for (Map.Entry<String, String> e : filters.entrySet()) {
            if (QueryConstants.supportedTicketFilters.get(e.getKey()) == null) {
                throw new RuntimeException("Unsupported filter type: " + e.getKey() + ".");

            }
            int filterType = QueryConstants.supportedTicketFilters.get(e.getKey());
            if (filterType == QueryConstants.byKey) {
                qw.eq("ticketkey", e.getValue());

                if (isDesc) qw.orderByDesc("ticketkey");

            } else if (filterType == QueryConstants.byName) {
                qw.like("ticketname", e.getValue());

                if (isDesc) qw.orderByDesc("ticketkey");

            } else if (filterType == QueryConstants.byBetweenDate) {
                String[] dateStrs = e.getValue().split("&");
                qw.apply("DATE(jobtime) >= STR_TO_DATE('"
                        + dateStrs[0] + "', '%Y-%m-%d')");
                qw.apply("DATE(jobtime) <= STR_TO_DATE('"
                        + dateStrs[1] + "', '%Y-%m-%d')");

                if (isDesc) qw.orderByDesc("jobtime");

            } else if (filterType == QueryConstants.byFromDate) {
                qw.ge("jobtime", e.getValue());

                if (isDesc) qw.orderByDesc("jobtime");

            } else if (filterType == QueryConstants.byTicketJobType) {
                qw.eq("jobtype", e.getValue());

                if (isDesc) qw.orderByDesc("ticketkey");

            } else if (filterType == QueryConstants.byTicketJobStatus) {
                qw.eq("jobstatus", e.getValue());

                if (isDesc) qw.orderByDesc("ticketkey");

            } else if (filterType == QueryConstants.byTicketInvokerKey) {
                qw.eq("invokerkey", e.getValue());

                if (isDesc) qw.orderByDesc("ticketkey");

            }
        }
        return getTicketsFromQueryWrapper(qw, page);
    }


    @Override
    public int writeTicket(ArrayList<ticket> tickets) {
        int successCnt = 0;
        for (ticket t : tickets) {
            ticketMapper.insert(t);
            successCnt++;
        }
        return successCnt;
    }

    @Override
    public int deleteTicket(ArrayList<Integer> ticketkeys) {
        QueryWrapper<ticket> qw = new QueryWrapper<>();
        qw.in("ticketkey", ticketkeys);
        return ticketMapper.delete(qw);
    }
}
