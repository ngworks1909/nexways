package com.nithin.auth.mailer;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    public String getBody(String otp) {
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <title>OTP Verification</title>
              <style>
                @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');
              </style>
            </head>
            <body style="font-family:'Poppins',Arial,Helvetica,sans-serif;background-color:#f4f4f4;margin:0;padding:0;">
              <div style="max-width:600px;margin:30px auto;background-color:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 0 10px rgba(0,0,0,0.1);font-family:'Poppins',Arial,Helvetica,sans-serif;">
                <div style="background:linear-gradient(90deg,#4CAF50,#2e7d32);color:white;padding:20px;text-align:center;">
                  <h2 style="margin:0;font-family:'Poppins',Arial,Helvetica,sans-serif;">OTP Verification</h2>
                </div>
                <div style="padding:20px;text-align:center;">
                  <p style="font-family:'Poppins',Arial,Helvetica,sans-serif;">Hello,</p>
                  <p style="font-family:'Poppins',Arial,Helvetica,sans-serif;">Your One-Time Password (OTP) for verification is:</p>
                  <div style="font-size:32px;font-weight:bold;color:#4CAF50;letter-spacing:4px;margin:20px 0;font-family:'Poppins',Arial,Helvetica,sans-serif;">%s</div>
                  <p style="font-family:'Poppins',Arial,Helvetica,sans-serif;">This OTP is valid for <b>10 minutes</b>. Do not share it with anyone.</p>
                </div>
                <div style="text-align:center;padding:15px;font-size:12px;color:#888;font-family:'Poppins',Arial,Helvetica,sans-serif;">
                  <p>&copy; 2025 Nexways. All rights reserved.</p>
                </div>
              </div>
            </body>
            </html>

            """;

        return String.format(htmlContent, otp);
    }
}
