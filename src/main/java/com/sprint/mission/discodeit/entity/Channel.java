package com.sprint.mission.discodeit.entity;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private final List<Message> messages;
    private final List<User> users;

    public Channel(String channel) {
        super();
        this.title = channel;

        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public String getChannel() {
        return title;
    }

    public void updateChannel(String updateChannel){
        this.title = updateChannel;
        updateTimeStamp();
    }

    public void addUser(User user){
        if(!users.contains(user)) {
            users.add(user);
            user.addChannel(this);
        }
    }
    public void addMessage(Message message){
        if(!messages.contains(message)) {
            messages.add(message);
            message.addChannel(this);
        }
    }
    public void deleteMessage(Message message){
        if(!messages.contains(message)){
            messages.remove(message);
            message.deleteChannel(this);
        }
    }

    public void deleteUser(User user){
        if(!users.contains(user)){
            users.remove(user);
            user.deleteChannel(this);
        }
    }

    public List<Message> getMessages(){
        return messages;
    }

    @Override
    public UUID getId(){
        return super.getId();
    }
}