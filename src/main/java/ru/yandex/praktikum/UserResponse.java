package ru.yandex.praktikum;

public class UserResponse {
   private boolean status;
   private UserDataResponse user;

   private String accessToken;

   public UserResponse() {
   }

   public UserResponse(boolean status, UserDataResponse user, String accessToken, String refreshToken) {
      this.status = status;
      this.user = user;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }

   private String refreshToken;

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public UserDataResponse getUser() {
      return user;
   }

   public void setUser(UserDataResponse user) {
      this.user = user;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public String getRefreshToken() {
      return refreshToken;
   }

   public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
   }
}
