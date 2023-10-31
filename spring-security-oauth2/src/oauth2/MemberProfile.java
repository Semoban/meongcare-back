package oauth2;

import lombok.Getter;

@Getter
public class MemberProfile {

    private final String provider;
    private final String email;
    private final String name;
    private final String profileImageUrl;

    public MemberProfile(String provider, String name, String email, String profileImageUrl) {
        this.provider = provider;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

}