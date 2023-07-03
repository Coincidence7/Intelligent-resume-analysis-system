package com.bjut.iras.service.ticket;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjut.iras.mapper.ResultMapper;
import com.bjut.iras.mapper.TicketMapper;
import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.resume;
import com.bjut.iras.pojo.ticket;
import com.bjut.iras.service.ServiceConsts;
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

        IPage<ticket> ticketPage = new Page<>(page, ServiceConsts.ticketPageCapacity);
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
        retMap.put("size", Integer.toString(Math.max(ServiceConsts.resultPageCapacity, tickets.size())));

        return retMap;
    }

    @Override
    public Map<String, String> getResultByFilters(String resumename, Boolean isLike, Integer page) {
        QueryWrapper<ticket> qw = new QueryWrapper<>();
        if (isLike) {
            qw.like("resumename", resumename);
        } else {
            qw.eq("resumename", resumename);
        }

        return getTicketsFromQueryWrapper(qw, page);
    }

    @Override
    public int writeTickets(ArrayList<ticket> tickets) {
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
