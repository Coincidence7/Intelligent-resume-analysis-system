package com.bjut.iras.service.ticket;

import com.bjut.iras.pojo.result;
import com.bjut.iras.pojo.ticket;

import java.util.ArrayList;
import java.util.Map;

public interface TicketService {

    public Map<String, String> getResultByFilters(String resumenamem, Boolean isLike, Integer page);
    public int writeTickets(ArrayList<ticket> tickets);
    public int deleteTicket(ArrayList<Integer> ticketkeys);

}
