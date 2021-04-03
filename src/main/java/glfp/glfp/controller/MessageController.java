package glfp.glfp.controller;

import glfp.glfp.dto.MemberDto;
import glfp.glfp.model.MessageModel;
import glfp.glfp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class MessageController {

    private final MemberService memberService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public MessageController(MemberService memberService) {
        this.memberService = memberService;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable Long to, MessageModel message) {

        System.out.println("handling send msg: " + message + "to : "+ to);
        // to : memberId(Long)
        MemberDto memberSearchResult = memberService.getMember(to);

        if (memberSearchResult != null) {
            simpMessagingTemplate.convertAndSend("/topic/messages/"+to, message);
        }

    }
}
