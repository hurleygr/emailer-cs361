package emailer

import org.springframework.boot.jackson.JsonComponent
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.*
import org.springframework.mail.SimpleMailMessage
import kotlinx.serialization.*

@Bean
fun javaMailSender(): JavaMailSender {
    val username : String = "Transistor.Employers@gmail.com"
    val password : String = "Wocaodijkstra1!"
    val mailSender = JavaMailSenderImpl()
    mailSender.host = "smtp.gmail.com"
    mailSender.port = 587
    mailSender.username = username
    mailSender.password = password
    mailSender.protocol = "smtp"
    mailSender.javaMailProperties.setProperty("mail.smtp.starttls.enable", "true")

    return mailSender
}

@RestController
class Controller {
    val sender = javaMailSender()
    @PostMapping
    fun sendEmail(@RequestBody body : kotlinx.serialization.json.JsonObject) : Unit {
        println(body)
        var message = SimpleMailMessage()
        val targetAddress = body["email"].toString()
        message.setTo(targetAddress)
        // Check if no address, handle error
        val subject = body["subject"].toString()
        if (subject != null) {
            message.setSubject(subject)
        }
        val messageBody = body["message"].toString()
        message.setText(messageBody)
        sender.send(message)

    }
}