package lis.shop.billion.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class UserRoleId implements Serializable{

    private Long userId;
    private Long roleId;

}
