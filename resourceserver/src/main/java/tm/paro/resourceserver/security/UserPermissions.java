package tm.paro.resourceserver.security;

public enum UserPermissions {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String permission;

    UserPermissions(String permission) {
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
