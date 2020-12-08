package com.store.demo.mail

import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class SendMail {
    public fun sendMail(to: String, subjects: String, messages: String) {
        Transport.send(plainMail(to, subjects, messages))
    }

    private fun plainMail(to: String, subjects: String, messages: String): MimeMessage {
        val tos = arrayListOf(to) //Multiple recipients
        val from = "rashwinnonda@outlook.com" //Sender email
        val password="rghu`ihROPQPXXV"
        val encryption:Encryption=Encryption()
        val properties = System.getProperties()

        with(properties) {
            put("mail.smtp.host", "smtp-mail.outlook.com") //Configure smtp host
            put("mail.smtp.port", "587") //Configure port
            put("mail.smtp.starttls.enable", "true") //Enable TLS
            put("mail.smtp.auth", "true") //Enable authentication
        }

        val auth = object : Authenticator() {
            override fun getPasswordAuthentication() =
                    PasswordAuthentication(from,encryption.decrypt(password)) //Credentials of the sender email
        }

        val session = Session.getDefaultInstance(properties, auth)

        val message = MimeMessage(session)

        with(message) {
            setFrom(InternetAddress(from))
            for (to in tos) {
                addRecipient(Message.RecipientType.TO, InternetAddress(to))
                subject = subjects //Email subject
                setContent(messages, "text/html; charset=utf-8") //Sending html message, you may change to send text here.
            }
        }

        return message
    }
}