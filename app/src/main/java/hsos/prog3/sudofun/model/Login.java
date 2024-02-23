package hsos.prog3.sudofun.model;

/**
 * Klasse repräsentiert die notwendigen Informationen für den Login.
 */
public class Login {

    private UserEntity userEntity;

    /**
     *  Getter
     */

    public UserEntity getUserEntity(){
        return userEntity;
    }

    /**
     *  Setter
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
