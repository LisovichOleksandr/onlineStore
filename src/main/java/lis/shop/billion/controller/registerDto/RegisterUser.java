package lis.shop.billion.controller.registerDto;

public record RegisterUser(String username,  String email, String password, String confirmPassword) {

    public boolean isSimilarPassword(){
        return password().equals(confirmPassword());
    }
}
