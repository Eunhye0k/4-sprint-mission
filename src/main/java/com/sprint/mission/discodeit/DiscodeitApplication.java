package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDTO;
import com.sprint.mission.discodeit.dto.message.MessageCreateDTO;
import com.sprint.mission.discodeit.dto.message.MessageUpdateDTO;
import com.sprint.mission.discodeit.dto.user.UserCreateDTO;
import com.sprint.mission.discodeit.dto.user.UserResponseDTO;
import com.sprint.mission.discodeit.dto.user.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class DiscodeitApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);
		//ReadStatusService readStatusService = context.getBean(ReadStatusService.class);


		System.out.println(context.getBean(UserService.class));


		//유저 생성
		UUID user1Id = userService.create(new UserCreateDTO("KEH", "KEH123@naver.com", "112233", null));
		UUID user2Id = userService.create(new UserCreateDTO("update1", "12312@abc.com", "113322", null));
		UUID user3Id = userService.create(new UserCreateDTO("Spring1", "Spring11@acfs.com", "321231", null));
		;
		//유저 업데이트
		userService.update(new UserUpdateDTO(user1Id, "update2", "12321@abc.com", null));
		//유저 상태 업데이트
		userService.updateUserOnline(user1Id, UserStatusType.OFFLINE);

		System.out.println("user 조회 - 수정");
		System.out.println(userService.find(user1Id));

		//전체 조회
		System.out.println("user 전체 조회 - 삭제");
		userService.delete(user1Id);
		for (UserResponseDTO dto : userService.findAll()) {
			System.out.println(dto);
		}
		//채널 생성
		UUID publicChannelId1 = channelService.createPublic("Discodeit", "디스코드잇 채널입니다.");
		UUID privateChannelID1 = channelService.createPrivate(user2Id);

		//채널 단건조회
		System.out.println("Channel 단건 조회 - public, private");
		System.out.println(channelService.find(publicChannelId1));
		System.out.println(channelService.find(privateChannelID1));

		//채널 삭제
		channelService.delete(privateChannelID1);

		//채널 다건조회
		System.out.println("채널 전체 조회 - 채널 삭제");
		for (ChannelResponseDTO dto : channelService.findAll()) {
			System.out.println(dto);
		}
		//channelService.delete(publicChannelId1);
		//NPE 터지는것 확인용(삭제되었는지 확인)
		//System.out.println(channelService.find(publicChannelId1));

		//메세지 생성
		UUID messageId1 = messageService.create(new MessageCreateDTO("안녕하세요. 너무 머리아파요.", user2Id, publicChannelId1, null));
		UUID messageId2 = messageService.create(new MessageCreateDTO("그치만 즐거워요.", user3Id, publicChannelId1, null));
		messageService.update(new MessageUpdateDTO(messageId1, "아니 아프지 않아"));

		System.out.println("Message 전체조회 - 등록, 수정 확인");
		for(Message message : messageService.findAll()) {
			System.out.println(message.getContent());
		}
	}
}
