package com.sprint.mission.discodeit.service.run;

//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.entity.Message;
//import com.sprint.mission.discodeit.entity.User;
//import com.sprint.mission.discodeit.service.ChannelService;
//import com.sprint.mission.discodeit.service.MessageService;
//import com.sprint.mission.discodeit.service.UserService;
//import com.sprint.mission.discodeit.service.file.FileChannelService;
//import com.sprint.mission.discodeit.service.file.FileMessageService;
//import com.sprint.mission.discodeit.service.file.FileUserService;
//import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
//import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
//import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.io.*;
import java.util.Optional;

public class JavaApplication {
    public static void main(String[] args) {
//        UserService userService = new FileUserService();
//        ChannelService channelService = new FileChannelService();
//        MessageService messageService = new FileMessageService();

        /*
        //아이디
        //아이디 등록
        User user1 = userService.createUser(new User("강은혁"));
        User user2 = userService.createUser(new User("자바"));

        //아이디 단건 조회
        System.out.println("아이디 단건 조회 : " + userService.getUser(user1.getId()).getUsername());

        //아이디 다건 조회
        for(User u : userService.getUsers()) {
            System.out.println("아이디 다건 조회 : " + u.getUsername());
        }

        //아이디 수정
        userService.updateUser(user1.getId(), "Hello");
        //수정된 아이디 조회
        System.out.println("수정 된 아이디 조회 : " + userService.getUser(user1.getId()).getUsername());

        //아이디 삭제
        userService.deleteUser(user1.getId());
        //아이디 삭제 후 조회
        System.out.println("삭제 후 조회 : " + userService.getUser(user1.getId())== null ? "존재함" : "존재하지않음");
        */
        /*
        //채널
        Channel channel1 = new Channel("Discodeit");
        Channel channel2 = new Channel("Hello");
        //채널 생성
        channelService.createChannel(channel1);
        channelService.createChannel(channel2);

        //채널 단건 조회
        System.out.println("채널 단건 조회 : " + channelService.getChannel(channel1.getId()).getChannel());

        //채널 전체조회
        for(Channel ch : channelService.getChannels()) {
            System.out.println("채널 다건 조회 : " + ch.getChannel());
        }

        //채널 수정
        channelService.updateChannel(channel1.getId(), "Discord");
        System.out.println("채널의 수정 된 이름 : " + channelService.getChannel(channel1.getId()).getChannel());

        //채널 삭제
        channelService.deleteChannel(channelService.getChannel(channel1.getId()).getId());

        //채널 삭제 후 조회
        System.out.println("삭제 된 채널 : " + channelService.getChannel(channel1.getId()) == null ? "존재함" : "존재하지않음");
        */

        /*
        //메세지
        User user = new User("EH");
        Channel ch = new Channel("Discodeit");
        Channel ch2 = new Channel("chtest");

        Message message1 = new Message("Hello Discord!", user, ch);
        Message message2 = new Message("test22", user, ch2);


        //메세지 전송
        System.out.println("메세지 : " + messageService.createMessage(message1, user, ch).getContent());
        messageService.createMessage(message2, user, ch);

        //단건 조회
        System.out.println("메세지 단건 조회 : " + messageService.getMessage(message1.getId()).getContent());


        //전체 조회
        for(Message m : messageService.getMessages()){
            System.out.println("메세지 다건 조회 : " + m.getContent());
        }

        //수정
        messageService.updateMessage(message1.getId(), "헬로 디스코드!");

        //수정 후 조회
        System.out.println("수정 후 조회 : " + messageService.getMessage(message1.getId()).getContent());

        //삭제
        messageService.deleteMessage(message1.getId());
        System.out.println("삭제 : " + messageService.getMessage(message1.getId()) == null ? "존재함" : "존재하지않음");
        */


 /*
            //직렬화
            try (FileOutputStream fos = new FileOutputStream("user.ser");
                 ObjectOutputStream oos = new ObjectOutputStream(fos);
            ) {
                oos.writeObject(user);
                System.out.println("User 객체가 user.ser 파일에 저장되었습니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        //역직렬화

        /* try (FileInputStream fis = new FileInputStream("user.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            User user1 = (User)ois.readObject();
            System.out.println("역직렬화 실행");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        */
        /*
         파일 테스트
        // 1. User 생성
        User user = new User("Alice");
        userService.createUser(user);

        // 2. Channel 생성
        Channel channel = new Channel("general");
        channelService.createChannel(channel);

        // 3. Message 생성
        Message message = new Message("Hello, world!",user, channel);
        messageService.createMessage(message, user, channel);

        // 4. 데이터 조회
        User fetchedUser = userService.getUser(user.getId());
        System.out.println("조회한 유저: " + (fetchedUser != null ? fetchedUser.getUsername() : "없음"));

        Channel fetchedChannel = channelService.getChannel(channel.getId());
        System.out.println("조회한 채널: " + (fetchedChannel != null ? fetchedChannel.getChannel() : "없음"));

        Message fetchedMessage = messageService.getMessage(message.getId());
        System.out.println("조회한 메시지: " + (fetchedMessage != null ? fetchedMessage.getContent() : "없음"));

        // 5. 메시지 수정
        messageService.updateMessage(message.getId(), "Hello, updated world!");

        // 6. 메시지 다시 조회
        Message updatedMessage = messageService.getMessage(message.getId());
        System.out.println("수정된 메시지: " + (updatedMessage != null ? updatedMessage.getContent() : "없음"));

        // 7. 삭제 테스트
        messageService.deleteMessage(message.getId());
        channelService.deleteChannel(channel.getId());
        userService.deleteUser(user.getId());

        // 8. 삭제 후 다시 조회해보기
        System.out.println("삭제 후 유저 조회: " + userService.getUser(user.getId()));
        System.out.println("삭제 후 채널 조회: " + channelService.getChannel(channel.getId()));
        System.out.println("삭제 후 메시지 조회: " + messageService.getMessage(message.getId()));
        */
    }
}
