package com.greymat9er.shutup;

public class ChatList {
    private String friendName;
    private int profilePicture;

    public ChatList() {
        //blank constructor
    }

    public ChatList(String friendName, int profilePicture) {
        this.friendName = friendName;
        this.profilePicture = profilePicture;
    }

    public String getFriendName() {
        return friendName;
    }

    public int getProfilePicture() {
        return profilePicture;
    }
}
