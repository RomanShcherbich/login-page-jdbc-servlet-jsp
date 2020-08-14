package model;

import constant.IModelConstant;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

    private String userName;
    private String gender;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "UserAccount{" +
               "userName='" + userName + '\'' +
               ", gender='" + gender + '\'' +
               ", password='" + password + '\'' +
               '}';
    }

}
