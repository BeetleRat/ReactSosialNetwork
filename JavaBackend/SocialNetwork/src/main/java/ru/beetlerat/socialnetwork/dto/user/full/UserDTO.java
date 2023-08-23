package ru.beetlerat.socialnetwork.dto.user.full;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserDTO {
    @Min(value = 1, message = "ID should be bigger then 0")
    protected int userID;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    protected String email;
    @NotNull(message = "Image URL should not be empty")
    @URL(message = "Image URL should not be correct")
    protected String imgURL;
    @NotNull(message = "Nickname should not be empty")
    protected String nickname;
    @NotNull(message = "Status should not be empty")
    protected String status;
    @NotNull(message = "Country should not be empty")
    protected String country;
    @NotNull(message = "City should not be empty")
    protected String city;
    protected boolean lookingForAJob;
    @NotNull(message = "Search job description should not be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (userID != userDTO.userID) return false;
        if (lookingForAJob != userDTO.lookingForAJob) return false;
        if (isFollow != userDTO.isFollow) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        if (imgURL != null ? !imgURL.equals(userDTO.imgURL) : userDTO.imgURL != null) return false;
        if (nickname != null ? !nickname.equals(userDTO.nickname) : userDTO.nickname != null) return false;
        if (status != null ? !status.equals(userDTO.status) : userDTO.status != null) return false;
        if (country != null ? !country.equals(userDTO.country) : userDTO.country != null) return false;
        if (city != null ? !city.equals(userDTO.city) : userDTO.city != null) return false;
        if (searchJobDescription != null ? !searchJobDescription.equals(userDTO.searchJobDescription) : userDTO.searchJobDescription != null)
            return false;
        return contacts != null ? contacts.equals(userDTO.contacts) : userDTO.contacts == null;
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
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (isFollow ? 1 : 0);
        return result;
    }
}
