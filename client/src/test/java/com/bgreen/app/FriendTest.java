package com.bgreen.app;

import com.bgreen.app.models.AddFriendRequest;
import org.junit.Test;
import com.bgreen.app.models.User;
import com.bgreen.app.models.Friend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FriendTest {

    User user = new User("user", "pass");
    User user2 = new User("user2", "pass2");
    Friend friend = new Friend(user, user2);

    @Test
    public void getUsersTest() {
        assertEquals(friend.getUser1(), user);
        assertEquals(friend.getUser2(), user2);
    }

    @Test
    public void setUsersTest() {

        friend.setUser1(user2);
        friend.setUser2(user);

        assertEquals(friend.getUser1(), user2);
        assertEquals(friend.getUser2(), user);
    }

   @Test
    public void addFriendRequestTest() {

       AddFriendRequest request = new AddFriendRequest();
       request.setUsername("test");

       assertEquals(request.getUsername(), "test");
   }


}
