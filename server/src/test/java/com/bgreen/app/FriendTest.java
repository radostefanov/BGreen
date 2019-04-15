package com.bgreen.app;

import com.bgreen.app.enums.FriendshipStatus;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityMeal;
import com.bgreen.app.repositories.FriendRepository;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.reqmodels.AddFriendRequest;
import com.bgreen.app.reqmodels.UserFriend;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.HashSet;

import static org.junit.Assert.*;

@WithMockUser(username = "demoUser")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FriendTest extends AbstractTest {

    User demoUser;
    User demoUser2;
    User demoUser3;
    User demoUser4;
    Friend demoFriend;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Override
    @Before
    public void setUp() {

        demoUser = new User();
        demoUser.setId(Long.valueOf(1));
        demoUser.setUsername("user");
        demoUser.setPassword(passwordEncoder.encode("password"));
        demoUser.setPoints(50);

        demoUser2 = new User();
        demoUser2.setId(Long.valueOf(2));
        demoUser2.setUsername("user2");
        demoUser2.setPassword(passwordEncoder.encode("password2"));
        demoUser2.setPoints(20);

        demoUser3 = new User();
        demoUser3.setId(Long.valueOf(3));
        demoUser3.setUsername("user3");
        demoUser3.setPassword(passwordEncoder.encode("password3"));

        demoUser4 = new User();
        demoUser4.setId(Long.valueOf(4));
        demoUser4.setUsername("user4");
        demoUser4.setPassword(passwordEncoder.encode("password"));

        demoFriend = new Friend();
        demoFriend.setUser1(demoUser);
        demoFriend.setUser2(demoUser2);
        super.setUp();
    }

    @Test
    @Transactional
    public void _clearDatabase(){

        userRepository.truncate();
    }

    @Test
    public void a_getEmptySetOfFriends() throws Exception {

        Friend[] result = this.httpRequest("/api/friends/", HttpMethod.GET, null, Friend[].class, 200);

        assertEquals(0, result.length);
    }

    @Test
    public void ba1_createFriend() throws Exception {

        userRepository.save(demoUser);
        userRepository.save(demoUser2);
        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user2");

        Friend result = this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, Friend.class, 200);

        assertEquals(result.getUser1(), demoUser);
        assertEquals(result.getUser2(), demoUser2);

        assertEquals(demoUser.getPassword(), result.getUser1().getPassword());

    }

    @Test
    public void bb_createFriendFalseUsername() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user5");

        this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, null, 400);
    }

    @Test
    public void bc_AddYourselfAsFriendFalse() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user");

        this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, null, 400);
    }

    @Test
    public void c_getNonEmptySetOfFriends() throws Exception {

        Friend[] result = this.httpRequest("/api/friends/", HttpMethod.GET, null, Friend[].class, 200);

        assertTrue(result.length == 1);
        assertTrue(result[0].getUser1().getUsername().equals(demoUser.getUsername()));
    }

    @Test
    public void d_getFriendById() throws Exception {

        Friend result = this.httpRequest("/api/friends/1/", HttpMethod.GET, null, Friend.class, 200);

        assertTrue(result.getUser1().equals(demoUser));
        assertTrue(result.getUser2().equals(demoUser2));
    }

    @Test
    public void e_AddAnotherFriend() throws Exception {

        userRepository.save(demoUser3);
        try {
            AbstractTest.AuthToken = this.getLoginToken("user3", "password3");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user");

        Friend result = this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, Friend.class, 200);

        assertEquals(result.getUser1(), demoUser3);
        assertEquals(result.getUser2(), demoUser);
    }

    @Test
    public void e_AddAnotherFriend2() throws Exception {

        userRepository.save(demoUser4);
        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user4");

        this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, Friend.class, 200);

    }
    @Test

    public void ea_AddAnotherFriend() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user3", "password3");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user");

        this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, null, 400);

    }

    @Test

    public void eb_AddAnotherFriend() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user3");

        this.httpRequest("/api/friends/", HttpMethod.POST, friendRequest, null, 400);

    }

    @Test
    public void f_getAllFriendsOfUserSorted1() throws Exception {
        try {
            AbstractTest.AuthToken = this.getLoginToken("user2", "password2");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }
        this.httpRequest("/api/friends/user", HttpMethod.GET, null, UserFriend[].class, 200);

    }

    @Test
    public void fa_getAllFriendsOfUserSorted1() throws Exception {
        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }
        demoUser3.setPoints(2);
        demoUser2.setPoints(10);
        demoUser4.setPoints(1);
        userRepository.save(demoUser2);
        userRepository.save(demoUser3);
        userRepository.save(demoUser4);

        UserFriend[] result = this.httpRequest("/api/friends/user", HttpMethod.GET, null, UserFriend[].class, 200);

        assertEquals(3, result.length);

        UserFriend friend2 = new UserFriend(demoUser2);
        UserFriend friend3 = new UserFriend(demoUser3);
        UserFriend friend4 = new UserFriend(demoUser4);

        friend2.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        friend2.setPoints(0);
        friend3.setFriendshipStatus(FriendshipStatus.HAS_TO_RESPOND);
        friend3.setPoints(0);
        friend4.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        friend4.setPoints(0);

        assertEquals(friend2, result[0]);
        assertEquals(friend4, result[1]);
        assertEquals(friend3, result[2]);

    }




    @Test
    public void fb_acceptFriendshipRequest() throws Exception {

        this.httpRequest("/api/friends/user/accept", HttpMethod.POST, demoUser3, null, 200);
    }

    @Test
    public void fc_confirmFriendshipActivated() throws Exception {

        demoUser4.setPoints(1);

        UserFriend[] result = this.httpRequest("/api/friends/user", HttpMethod.GET, null, UserFriend[].class, 200);

        assertEquals(3, result.length);

        UserFriend friend2 = new UserFriend(demoUser2);
        UserFriend friend3 = new UserFriend(demoUser3);
        UserFriend friend4 = new UserFriend(demoUser4);

        friend2.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        friend2.setPoints(0);
        friend3.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend3.setPoints(2);
        friend4.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        friend4.setPoints(0);

        assertEquals(friend3, result[0]);
        assertEquals(friend2, result[1]);
        assertEquals(friend4, result[2]);

    }


    @Test
    public void g_testNotExistingFriendship() throws Exception{

        this.httpRequest("/api/friends/user/accept", HttpMethod.POST, demoUser, null, 404);

    }

    @Test
    public void h_testNotExistingUser() throws Exception{



        this.httpRequest("/api/friends/user/accept", HttpMethod.POST, new User(), null, 404);

    }

    @Test
    public void i_deleteNonExistingFriendship() throws Exception{

        this.httpRequest("/api/friends/user", HttpMethod.POST, demoUser, null, 404);

    }

    @Test
    public void j_getFriendsOfOtherUser() throws Exception {
        try {
            AbstractTest.AuthToken = this.getLoginToken("user3", "password3");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        UserFriend[] result = this.httpRequest("/api/friends/user", HttpMethod.GET, null, UserFriend[].class, 200);

        assertEquals(1, result.length);

        UserFriend friend = new UserFriend(demoUser);
        friend.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        assertEquals(friend, result[0]);

    }

    @Test
    public void k_testUserRepoEquals(){

        UserFriend friend1 = new UserFriend();
        friend1.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend1.setPoints(20);
        friend1.setUsername("u1");

        UserFriend friend2 = new UserFriend();
        friend2.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend2.setPoints(20);
        friend2.setUsername("u1");

        assertEquals(friend1, friend1);
        assertEquals(friend1, friend2);

        assertNotEquals(friend1, null);
        friend2.setPoints(11);
        assertNotEquals(friend1, friend2);
        friend2.setPoints(20);
        friend2.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        assertNotEquals(friend1, friend2);
        friend2.setPoints(20);
        friend2.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend2.setUsername("u2");
        assertNotEquals(friend1, friend2);
    }

    @Test
    public void l_testUserRepoEquals(){

        UserFriend friend1 = new UserFriend();
        friend1.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend1.setPoints(20);
        friend1.setUsername("u1");

        UserFriend friend2 = new UserFriend();
        friend2.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend2.setPoints(20);
        friend2.setUsername("u2");

        assertNotEquals(friend1, friend2);
        friend2.setUsername(null);
        assertNotEquals(friend1, friend2);
        friend2.setUsername("u2");
        friend1.setUsername(null);
        assertNotEquals(friend1, friend2);
    }

    @Test
    public void m_testUserRepoEquals(){

        UserFriend friend1 = new UserFriend();
        friend1.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend1.setPoints(20);
        friend1.setUsername("u1");

        UserFriend friend2 = new UserFriend();
        friend2.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
        friend2.setPoints(20);
        friend2.setUsername("u1");

        assertNotEquals(friend1, friend2);
    }

    @Test
    public void n_testUserRepoEquals(){

        UserFriend friend1 = new UserFriend();
        friend1.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend1.setPoints(20);
        friend1.setUsername("u1");
        friend1.setProfilePicture("pic");

        UserFriend friend2 = new UserFriend();
        friend2.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        friend2.setPoints(20);
        friend2.setUsername("u1");
        friend2.setProfilePicture("pic");

        assertEquals(friend1, friend2);

        friend1.setProfilePicture("pic2");
        assertNotEquals(friend1, friend2);

        friend2.setProfilePicture(null);
        assertNotEquals(friend1, friend2);

        friend1.setProfilePicture(null);
        friend2.setProfilePicture("pic2");
        assertNotEquals(friend1, friend2);

    }

//    @Test
//    public void f_getAllFriendsOfUserSorted2() throws Exception {
//
//        try {
//            AbstractTest.AuthToken = this.getLoginToken("user", "password");
//            System.out.println("Auth token1: " + AbstractTest.AuthToken);
//        } catch (Exception e) {
//
//            System.out.println(e);
//        }
//
//        User[] result = this.httpRequest("/api/friends/user", HttpMethod.GET, null, User[].class, 200);
//
//        assertEquals(2, result.length);
//        assertEquals(result[0], demoUser2);
//        assertEquals(result[1], demoUser3);
//    }

//    @Test
//    public void g_checkIfFriendTrue() throws Exception {
//        FriendController fc = new FriendController();
//
//        friendRepository.save(demoFriend);
//        assertTrue(fc.checkIfFriend(demoUser, demoUser2));
//    }

    @Test
    public void wa_deleteFriendByUsername() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        AddFriendRequest req = new AddFriendRequest();
        req.setUsername("user2");

        this.httpRequest("/api/friends/user", HttpMethod.POST, req, null, 200);
    }

    @Test
    public void wa3_deleteFriend() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {
            System.out.println(e);
        }

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername("user3");

        this.httpRequest("/api/friends/user", HttpMethod.POST, friendRequest, null, 200);


    }

//    @Test
//    public void wa4_deleteFriend() throws Exception {
//
//        try {
//            AbstractTest.AuthToken = this.getLoginToken("user5", "password5");
//            System.out.println("Auth token1: " + AbstractTest.AuthToken);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        AddFriendRequest friendRequest = new AddFriendRequest();
//        friendRequest.setUsername("user7");
//
//        this.httpRequest("/api/friends/user", HttpMethod.POST, friendRequest, null, 404);
//
//
//    }
//
    @Test
    public void w_deleteNonExistingUsername() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token1: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        AddFriendRequest req = new AddFriendRequest();
        req.setUsername("use");

        this.httpRequest("/api/friends/user", HttpMethod.POST, req, null, 404);
    }


    @Test
    public void y_deleteFriends() throws Exception {

        this.httpRequest("/api/friends/3", HttpMethod.DELETE, null, null, 200);

        userRepository.deleteAll();

    }




    @Test
    public void v_gettersTest(){

        assertEquals(demoUser, demoFriend.getUser1());
        assertEquals(demoUser2, demoFriend.getUser2());
    }

    @Test
    public void w_settersTest(){

        demoFriend.setUser1(demoUser3);
        demoFriend.setUser2(demoUser);
        assertEquals(demoFriend.getUser1(), demoUser3);
        assertEquals(demoFriend.getUser2(), demoUser);
    }

    @Test
    public void x_equalsTrue(){

        User demoUser4 = new User();
        demoUser4.setUsername("user");
        demoUser4.setPassword(passwordEncoder.encode("password"));

        assertTrue(demoUser.equals(demoUser4));
        assertFalse(demoUser.equals(demoUser2));

    }

    @Test
    public void xa_equalsNullFalse(){
        assertFalse(demoFriend.equals(null));

    }

    @Test
    public void xb_equalsOtherFalse(){
        User demoUser4 = new User();
        demoUser4.setUsername("user");
        demoUser4.setPassword(passwordEncoder.encode("password"));

        Friend demoFriend2 = new Friend();
        demoFriend2.setUser1(demoUser3);
        demoFriend2.setUser1(demoUser4);

        assertFalse(demoFriend.equals(demoFriend2));

    }

    @Test
    public void xc_notEqualsDifferentClass(){
        UserActivity temp = new UserActivityMeal();
        assertFalse(demoFriend.equals(temp));
    }

    @Test
    public void xd_notEqualsDifferentUsers(){
        Friend demoFriend2 = new Friend();
        demoFriend2.setUser1(demoUser);
        demoFriend2.setUser2(demoUser3);
        assertFalse(demoFriend.equals(demoFriend2));
        demoFriend2.setUser1(demoUser2);
        assertFalse(demoFriend.equals(demoFriend2));
        demoFriend2.setUser1(demoUser2);
        demoFriend2.setUser2(demoUser2);
        assertFalse(demoFriend.equals(demoFriend2));
        demoFriend2.setUser1(demoUser);
        assertTrue(demoFriend.equals(demoFriend2));

        assertTrue(demoUser.equals(demoUser));
    }

    @Test
    public void z_deleteNonExistingFriends() throws Exception {

        this.httpRequest("/api/friends/9", HttpMethod.DELETE, null, null, 404);
    }


}

