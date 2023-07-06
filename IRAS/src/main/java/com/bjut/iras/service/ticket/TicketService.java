package com.bjut.iras.service.ticket;

import com.bjut.iras.pojo.ticket;

import java.util.ArrayList;
import java.util.Map;

public interface TicketService {

    public Map<String, String> getTicketByName(String ticketname, Boolean isLike, Integer page);
    public Map<String, String> getTicketByKey(Integer ticketkey, Integer page);
    public Map<String, String> getTicketByFilterMap(Map<String, String> filters, Boolean isDesc, Integer page);
    public int writeTicket(ArrayList<ticket> tickets);
    public int deleteTicket(ArrayList<Integer> ticketkeys);

}
