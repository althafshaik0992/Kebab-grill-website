package com.example.SpringRestaurantswebsite.Service;

public interface MailService {


    public void send(String formAddress, String toAddress, String subject, String content) throws Exception;

    }

