package ru.beetlerat.socialnetwork.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.beetlerat.socialnetwork.security.models.SecurityUserModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userID;
    @OneToOne
    @JoinColumn(name = "security_id", referencedColumnName = "id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private SecurityUserModel securitySettings;
    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email is not in the correct format")
    private String email;
    @Column(name = "imgurl")
    private String imgURL;
    @Column(name = "nickname")
    @NotEmpty(message = "Nickname should not be empty")
    private String nickname;
    @Column(name = "status")
    private String status;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "looking_for_a_job")
    private boolean lookingForAJob;
    @Column(name = "search_job_description")
    private String searchJobDescription;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "website")
    private String website;
    @Column(name = "vk")
    private String vk;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "instagram")
    private String instagram;
    @Column(name = "youtube")
    private String youtube;
    @Column(name = "github")
    private String github;
    @Column(name = "mainlink")
    private String mainlink;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id", referencedColumnName = "id"))
    private Set<User> followedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "followedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Set<User> usersWhoFollowedMe = new HashSet();

    public User() {
    }

    public User(int userID, String email, String imgURL, String nickname, String status, String country, String city, boolean lookingForAJob, String searchJobDescription, String facebook, String website, String vk, String twitter, String instagram, String youtube, String github, String mainlink) {
        this.userID = userID;
        this.email = email;
        this.imgURL = imgURL;
        this.nickname = nickname;
        this.status = status;
        this.country = country;
        this.city = city;
        this.lookingForAJob = lookingForAJob;
        this.searchJobDescription = searchJobDescription;
        this.facebook = facebook;
        this.website = website;
        this.vk = vk;
        this.twitter = twitter;
        this.instagram = instagram;
        this.youtube = youtube;
        this.github = github;
        this.mainlink = mainlink;
    }

    public int getUserID() {
        return userID;
    }

    public SecurityUserModel getSecuritySettings() {
        return securitySettings;
    }

    public String getEmail() {
        return email;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getNickname() {
        return nickname;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public boolean isLookingForAJob() {
        return lookingForAJob;
    }

    public String getSearchJobDescription() {
        return searchJobDescription;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getWebsite() {
        return website;
    }

    public String getVk() {
        return vk;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getGithub() {
        return github;
    }

    public String getMainlink() {
        return mainlink;
    }

    public Set<User> getFollowedUsers() {
        return followedUsers;
    }

    public Set<User> getUsersWhoFollowedMe() {
        return usersWhoFollowedMe;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSecuritySettings(SecurityUserModel securitySettings) {
        this.securitySettings = securitySettings;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLookingForAJob(boolean lookingForAJob) {
        this.lookingForAJob = lookingForAJob;
    }

    public void setSearchJobDescription(String searchJobDescription) {
        this.searchJobDescription = searchJobDescription;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public void setMainlink(String mainlink) {
        this.mainlink = mainlink;
    }

    public void setFollowedUsers(Set<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public void setUsersWhoFollowedMe(Set<User> usersWhoFollowedMe) {
        this.usersWhoFollowedMe = usersWhoFollowedMe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userID != user.userID) return false;
        if (lookingForAJob != user.lookingForAJob) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (imgURL != null ? !imgURL.equals(user.imgURL) : user.imgURL != null) return false;
        if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (city != null ? !city.equals(user.city) : user.city != null) return false;
        if (searchJobDescription != null ? !searchJobDescription.equals(user.searchJobDescription) : user.searchJobDescription != null)
            return false;
        if (facebook != null ? !facebook.equals(user.facebook) : user.facebook != null) return false;
        if (website != null ? !website.equals(user.website) : user.website != null) return false;
        if (vk != null ? !vk.equals(user.vk) : user.vk != null) return false;
        if (twitter != null ? !twitter.equals(user.twitter) : user.twitter != null) return false;
        if (instagram != null ? !instagram.equals(user.instagram) : user.instagram != null) return false;
        if (youtube != null ? !youtube.equals(user.youtube) : user.youtube != null) return false;
        if (github != null ? !github.equals(user.github) : user.github != null) return false;
        return mainlink != null ? mainlink.equals(user.mainlink) : user.mainlink == null;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (imgURL != null ? imgURL.hashCode() : 0);
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (lookingForAJob ? 1 : 0);
        result = 31 * result + (searchJobDescription != null ? searchJobDescription.hashCode() : 0);
        result = 31 * result + (facebook != null ? facebook.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (vk != null ? vk.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        result = 31 * result + (instagram != null ? instagram.hashCode() : 0);
        result = 31 * result + (youtube != null ? youtube.hashCode() : 0);
        result = 31 * result + (github != null ? github.hashCode() : 0);
        result = 31 * result + (mainlink != null ? mainlink.hashCode() : 0);
        return result;
    }

    public void addFollowedUsers(User... users) {
        for (User user : users) {
            if (!this.followedUsers.contains(user)) {
                this.followedUsers.add(user);
                user.addUsersWhoFollowedMe(this);
            }
        }
    }

    public void removeFollowedUsers(User... users) {
        if (this.followedUsers.size() == 0) {
            return;
        }
        for (User user : users) {
            if (this.followedUsers.contains(user)) {
                this.followedUsers.remove(user);
                user.removeUsersWhoFollowedMe(this);
            }
        }
    }

    public void addUsersWhoFollowedMe(User... users) {
        for (User user : users) {
            if (!this.usersWhoFollowedMe.contains(user)) {
                this.usersWhoFollowedMe.add(user);
                user.addFollowedUsers(this);
            }
        }
    }

    public void removeUsersWhoFollowedMe(User... users) {
        if (this.usersWhoFollowedMe.size() == 0) {
            return;
        }
        for (User user : users) {
            if (this.usersWhoFollowedMe.contains(user)) {
                this.usersWhoFollowedMe.remove(user);
                user.removeFollowedUsers(this);
            }
        }
    }

    public void setAllFromAnotherUser(User copedUser) {
        setEmail(copedUser.getEmail());
        setImgURL(copedUser.getImgURL());
        setNickname(copedUser.getNickname());
        setStatus(copedUser.getStatus());
        setCountry(copedUser.getCountry());
        setCity(copedUser.getCity());
        setLookingForAJob(copedUser.isLookingForAJob());
        setSearchJobDescription(copedUser.getSearchJobDescription());
        setFacebook(copedUser.getFacebook());
        setWebsite(copedUser.getWebsite());
        setVk(copedUser.getVk());
        setTwitter(copedUser.getTwitter());
        setInstagram(copedUser.getInstagram());
        setYoutube(copedUser.getYoutube());
        setGithub(copedUser.getGithub());
        setMainlink(copedUser.getMainlink());
        setFollowedUsers(copedUser.getFollowedUsers());
        setUsersWhoFollowedMe(copedUser.getUsersWhoFollowedMe());
    }
}
