package security.token;


import org.pac4j.core.profile.UserProfile;

public interface ClientToken {
    public ClientType getClientType();
    public UserProfile getUserProfile();
}
