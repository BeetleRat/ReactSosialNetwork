package ru.beetlerat.socialnetwork.dto.user.full;

public class UserDTO {
    protected int userID;
    protected String email;
    protected String imgURL;
    protected String nickname;
    protected String status;
    protected String country;
    protected String city;
    protected boolean lookingForAJob;
    protected String searchJobDescription;
    protected Contacts contacts;
    protected boolean isFollow;

    public UserDTO() {
        this.contacts = new Contacts();
    }

    public int getUserID() {
        return userID;
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

    public Contacts getContacts() {
        return contacts;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public void setFacebook(String facebook) {
        this.contacts.setFacebook(facebook);
    }

    public void setWebsite(String website) {
        this.contacts.setWebsite(website);
    }

    public void setVk(String vk) {
        this.contacts.setVk(vk);
    }

    public void setTwitter(String twitter) {
        this.contacts.setTwitter(twitter);
    }

    public void setInstagram(String instagram) {
        this.contacts.setInstagram(instagram);
    }

    public void setYoutube(String youtube) {
        this.contacts.setYoutube(youtube);
    }

    public void setGithub(String github) {
        this.contacts.setGithub(github);
    }

    public void setMainlink(String mainlink) {
        this.contacts.setMainlink(mainlink);
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
