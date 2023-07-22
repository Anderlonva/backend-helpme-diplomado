package com.anderlonva.backendhelpmeiud.service.iface;

public interface IEmailService {

    boolean sendEmail(String mensaje, String email, String asunto);
}
