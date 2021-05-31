package glfp.glfp.domain.repository;

import glfp.glfp.model.ChatRoom;
import glfp.glfp.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    //TODO : 나중에 dbRepository로 쉽게 변환할 수 있도록 리팩토링
    //private Map<String, ChatRoom> chatRoomMap;
    //채팅방(topic)에 발행되는 메시지를 처리할 Listener
    private final RedisMessageListenerContainer redisMessageListener;
    //구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    //채팅방의 대화 메시지를 발행하기 위한 redis topic 정보.
    // 서버별로 채팅방에 매치되는 topic 정보를 Map에 넣어 roomId로 찾을 수 있도록 한다
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
      //  chatRoomMap = new LinkedHashMap<>();
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        /*
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
         */
        return opsHashChatRoom.values(CHAT_ROOMS);

    }

    public ChatRoom findRoomById(String id) {
       // return chatRoomMap.get(id);
        return opsHashChatRoom.get(CHAT_ROOMS, id);
    }


    public ChatRoom createChatRoom(String name) {
        //서버 간 채팅방 공유를 위해 redis hash에 저장
        ChatRoom chatRoom = ChatRoom.create(name);
     //   chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public void enterChatRoom(String roomId) {
        //redis에 topic을 만들고 pub/sub 통신을 위해 리스너를 설정
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber,topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

}
