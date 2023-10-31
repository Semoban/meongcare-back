package oauth2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuth2Attributes {

    GOOGLE("google", attributes -> new MemberProfile(
            "google",
            (String) attributes.get("name"),
            (String) attributes.get("email"),
            (String) attributes.get("picture")
    )),

    NAVER("naver", attributes -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new MemberProfile(
                "naver",
                (String) response.get("name"),
                (String) response.get("email"),
                (String) response.get("profile_image")
        );
    }),

    KAKAO("kakao", attributes -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return new MemberProfile(
                "kakao",
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                (String) profile.get("profile_image_url")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberProfile> userProfileFactory;

    OAuth2Attributes(String registrationId,
                     Function<Map<String, Object>, MemberProfile> userProfileFactory) {
        this.registrationId = registrationId;
        this.userProfileFactory = userProfileFactory;
    }

    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .userProfileFactory.apply(attributes);
    }
}