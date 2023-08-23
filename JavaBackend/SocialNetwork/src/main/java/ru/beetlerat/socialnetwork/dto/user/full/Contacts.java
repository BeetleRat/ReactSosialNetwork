package ru.beetlerat.socialnetwork.dto.user.full;

public class Contacts {
    private String facebook;
    private String website;
    private String vk;
    private String twitter;
    private String instagram;
    private String youtube;
    private String github;
    private String mainlink;

    public Contacts() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts = (Contacts) o;

        if (facebook != null ? !facebook.equals(contacts.facebook) : contacts.facebook != null) return false;
        if (website != null ? !website.equals(contacts.website) : contacts.website != null) return false;
        if (vk != null ? !vk.equals(contacts.vk) : contacts.vk != null) return false;
        if (twitter != null ? !twitter.equals(contacts.twitter) : contacts.twitter != null) return false;
        if (instagram != null ? !instagram.equals(contacts.instagram) : contacts.instagram != null) return false;
        if (youtube != null ? !youtube.equals(contacts.youtube) : contacts.youtube != null) return false;
        if (github != null ? !github.equals(contacts.github) : contacts.github != null) return false;
        return mainlink != null ? mainlink.equals(contacts.mainlink) : contacts.mainlink == null;
    }

    @Override
    public int hashCode() {
        int result = facebook != null ? facebook.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (vk != null ? vk.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        result = 31 * result + (instagram != null ? instagram.hashCode() : 0);
        result = 31 * result + (youtube != null ? youtube.hashCode() : 0);
        result = 31 * result + (github != null ? github.hashCode() : 0);
        result = 31 * result + (mainlink != null ? mainlink.hashCode() : 0);
        return result;
    }
}
