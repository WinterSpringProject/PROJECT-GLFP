package glfp.glfp.controller;


import glfp.glfp.domain.repository.ChatRoomRepository;
import glfp.glfp.model.ChatMessage;
import glfp.glfp.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Controller
public class ChatController {

    //private final SimpMessageSendingOperations messagingTemplate;

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;


    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
       // messagingTemplate.convertAndSend("/sub/chat/room/"+ message.getRoomId(),message);
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

}
